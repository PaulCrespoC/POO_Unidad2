package service;

import model.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.io.TempDir;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.ArrayList;

/**
 * Pruebas unitarias para FileHandlerService
 */
public class FileHandlerServiceTest {

    private FileHandlerService fileHandler;
    private List<ContenidoAudiovisual> contenidosTest;

    @TempDir
    Path tempDir;

    @BeforeEach
    void setUp() {
        fileHandler = new FileHandlerService();
        contenidosTest = new ArrayList<>();
        
        // Crear contenidos de prueba
        contenidosTest.add(new Pelicula("Avatar", 162, "Ciencia Ficción", "20th Century Studios"));
        contenidosTest.add(new SerieDeTV("Game of Thrones", 60, "Fantasía", 8));
        contenidosTest.add(new Documental("Cosmos", 45, "Ciencia", "Astronomía"));
        contenidosTest.add(new VideoYouTube("Tutorial Java", 25, "Educativo", "CodeAcademy", 15000, 1200, "2024-01-15", "1080p"));
        contenidosTest.add(new Cortometraje("El Último Día", 15, "Drama", "María García", "Festival de Cannes", false, "Digital", 75000));
    }

    @Test
    @DisplayName("Test validación de formato de archivo")
    void testValidarFormato() {
        assertTrue(fileHandler.validarFormato("archivo.csv"));
        assertTrue(fileHandler.validarFormato("ARCHIVO.CSV"));
        assertTrue(fileHandler.validarFormato("/ruta/completa/archivo.csv"));
        
        assertFalse(fileHandler.validarFormato("archivo.txt"));
        assertFalse(fileHandler.validarFormato("archivo"));
        assertFalse(fileHandler.validarFormato("archivo.xml"));
        assertFalse(fileHandler.validarFormato(null));
    }

    @Test
    @DisplayName("Test escritura y lectura de archivo CSV")
    void testEscrituraYLecturaCSV() throws IOException {
        Path archivoTest = tempDir.resolve("test.csv");
        String rutaArchivo = archivoTest.toString();

        // Escribir archivo
        assertDoesNotThrow(() -> {
            fileHandler.escribirArchivo(rutaArchivo, contenidosTest);
        });

        // Verificar que el archivo existe
        assertTrue(archivoTest.toFile().exists());

        // Leer archivo
        List<ContenidoAudiovisual> contenidosLeidos = fileHandler.leerArchivo(rutaArchivo);

        // Verificar contenido
        assertEquals(contenidosTest.size(), contenidosLeidos.size());
        
        // Verificar primer elemento (Película)
        ContenidoAudiovisual primerContenido = contenidosLeidos.get(0);
        assertTrue(primerContenido instanceof Pelicula);
        assertEquals("Avatar", primerContenido.getTitulo());
        assertEquals(162, primerContenido.getDuracionEnMinutos());
        assertEquals("Ciencia Ficción", primerContenido.getGenero());
    }

    @Test
    @DisplayName("Test lectura de archivo inexistente")
    void testLecturaArchivoInexistente() {
        String archivoInexistente = tempDir.resolve("inexistente.csv").toString();
        
        assertThrows(RuntimeException.class, () -> {
            fileHandler.leerArchivo(archivoInexistente);
        });
    }

    @Test
    @DisplayName("Test escritura con formato inválido")
    void testEscrituraFormatoInvalido() {
        String archivoInvalido = tempDir.resolve("archivo.txt").toString();
        
        assertThrows(IllegalArgumentException.class, () -> {
            fileHandler.escribirArchivo(archivoInvalido, contenidosTest);
        });
    }

    @Test
    @DisplayName("Test lectura con formato inválido")
    void testLecturaFormatoInvalido() {
        String archivoInvalido = tempDir.resolve("archivo.txt").toString();
        
        assertThrows(IllegalArgumentException.class, () -> {
            fileHandler.leerArchivo(archivoInvalido);
        });
    }

    @Test
    @DisplayName("Test creación de archivo de ejemplo")
    void testCrearArchivoEjemplo() {
        Path archivoEjemplo = tempDir.resolve("ejemplo.csv");
        String rutaArchivo = archivoEjemplo.toString();

        assertDoesNotThrow(() -> {
            fileHandler.crearArchivoEjemplo(rutaArchivo);
        });

        // Verificar que el archivo existe
        assertTrue(archivoEjemplo.toFile().exists());

        // Leer y verificar contenido
        List<ContenidoAudiovisual> contenidos = fileHandler.leerArchivo(rutaArchivo);
        assertFalse(contenidos.isEmpty());
        assertTrue(contenidos.size() >= 5); // Debe tener al menos 5 ejemplos
    }

    @Test
    @DisplayName("Test manejo de líneas vacías y comentarios")
    void testManejoLineasVaciasYComentarios() throws IOException {
        Path archivoTest = tempDir.resolve("test_comentarios.csv");
        
        // Crear archivo con comentarios y líneas vacías
        String contenido = """
                # Este es un comentario
                
                PELICULA,1,Avatar,162,Ciencia Ficción,20th Century Studios
                # Otro comentario
                
                SERIE,2,Game of Thrones,60,Fantasía,8
                
                """;
        
        java.nio.file.Files.write(archivoTest, contenido.getBytes());

        // Leer archivo
        List<ContenidoAudiovisual> contenidos = fileHandler.leerArchivo(archivoTest.toString());
        
        // Debe leer solo las líneas válidas, ignorando comentarios y líneas vacías
        assertEquals(2, contenidos.size());
    }

    @Test
    @DisplayName("Test parseo de diferentes tipos de contenido")
    void testParseoTiposContenido() throws IOException {
        Path archivoTest = tempDir.resolve("test_tipos.csv");
        
        String contenido = """
                PELICULA,1,Avatar,162,Ciencia Ficción,20th Century Studios
                SERIE,2,Game of Thrones,60,Fantasía,8
                DOCUMENTAL,3,Cosmos,45,Ciencia,Astronomía
                YOUTUBE,4,Tutorial Java,25,Educativo,CodeAcademy,15000,1200,2024-01-15,1080p
                CORTOMETRAJE,5,El Último Día,15,Drama,María García,Festival de Cannes,false,Digital,75000
                """;
        
        java.nio.file.Files.write(archivoTest, contenido.getBytes());

        List<ContenidoAudiovisual> contenidos = fileHandler.leerArchivo(archivoTest.toString());
        
        assertEquals(5, contenidos.size());
        assertTrue(contenidos.get(0) instanceof Pelicula);
        assertTrue(contenidos.get(1) instanceof SerieDeTV);
        assertTrue(contenidos.get(2) instanceof Documental);
        assertTrue(contenidos.get(3) instanceof VideoYouTube);
        assertTrue(contenidos.get(4) instanceof Cortometraje);
    }

    @Test
    @DisplayName("Test manejo de errores en líneas mal formateadas")
    void testManejoErroresLineasMalFormateadas() throws IOException {
        Path archivoTest = tempDir.resolve("test_errores.csv");
        
        String contenido = """
                PELICULA,1,Avatar,162,Ciencia Ficción,20th Century Studios
                LINEA_MALFORMATEADA,datos,insuficientes
                SERIE,2,Game of Thrones,60,Fantasía,8
                TIPO_DESCONOCIDO,3,Titulo,60,Genero,Campo
                """;
        
        java.nio.file.Files.write(archivoTest, contenido.getBytes());

        // Debe leer solo las líneas válidas y mostrar errores para las inválidas
        List<ContenidoAudiovisual> contenidos = fileHandler.leerArchivo(archivoTest.toString());
        
        assertEquals(2, contenidos.size()); // Solo película y serie válidas
    }

    @Test
    @DisplayName("Test persistencia de datos complejos")
    void testPersistenciaDatosComplejos() throws IOException {
        Path archivoTest = tempDir.resolve("test_complejo.csv");
        String rutaArchivo = archivoTest.toString();

        // Crear contenidos con relaciones
        Pelicula pelicula = new Pelicula("Titanic", 195, "Drama", "Paramount Pictures");
        pelicula.agregarActor(new Actor("Leonardo DiCaprio", 49, "Estadounidense", "Principal"));
        pelicula.agregarActor(new Actor("Kate Winslet", 48, "Británica", "Principal"));

        SerieDeTV serie = new SerieDeTV("Breaking Bad", 47, "Drama", 5);
        Temporada temp1 = new Temporada(1, 7, "2008-01-20", "2008-03-09");
        temp1.agregarEpisodio("Pilot");
        temp1.agregarEpisodio("Cat's in the Bag...");
        serie.agregarTemporada(temp1);

        List<ContenidoAudiovisual> contenidosComplejos = List.of(pelicula, serie);

        // Escribir y leer
        fileHandler.escribirArchivo(rutaArchivo, contenidosComplejos);
        List<ContenidoAudiovisual> contenidosLeidos = fileHandler.leerArchivo(rutaArchivo);

        assertEquals(2, contenidosLeidos.size());
        
        // Verificar que se mantienen los datos básicos
        ContenidoAudiovisual peliculaLeida = contenidosLeidos.get(0);
        assertEquals("Titanic", peliculaLeida.getTitulo());
        assertEquals(195, peliculaLeida.getDuracionEnMinutos());
    }

    @Test
    @DisplayName("Test charset UTF-8")
    void testCharsetUTF8() throws IOException {
        Path archivoTest = tempDir.resolve("test_utf8.csv");
        String rutaArchivo = archivoTest.toString();

        // Crear contenido con caracteres especiales
        List<ContenidoAudiovisual> contenidosUTF8 = List.of(
            new Pelicula("El Niño", 120, "Acción", "Estudio Español"),
            new Documental("La Niña", 90, "Ciencia", "Fenómeno climático")
        );

        // Escribir y leer
        fileHandler.escribirArchivo(rutaArchivo, contenidosUTF8);
        List<ContenidoAudiovisual> contenidosLeidos = fileHandler.leerArchivo(rutaArchivo);

        assertEquals(2, contenidosLeidos.size());
        assertEquals("El Niño", contenidosLeidos.get(0).getTitulo());
        assertEquals("La Niña", contenidosLeidos.get(1).getTitulo());
    }
}