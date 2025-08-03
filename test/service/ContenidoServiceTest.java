package service;

import interfaces.IContenidoRepository;
import interfaces.IFileHandler;
import model.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.ArrayList;

/**
 * Pruebas unitarias para ContenidoService
 */
public class ContenidoServiceTest {

    @Mock
    private IContenidoRepository mockRepository;

    private ContenidoService contenidoService;
    private List<ContenidoAudiovisual> contenidosTest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        contenidoService = new ContenidoService(mockRepository, "test.csv");
        
        // Crear contenidos de prueba
        contenidosTest = new ArrayList<>();
        contenidosTest.add(new Pelicula("Avatar", 162, "Ciencia Ficción", "20th Century Studios"));
        contenidosTest.add(new SerieDeTV("Game of Thrones", 60, "Fantasía", 8));
        contenidosTest.add(new Documental("Cosmos", 45, "Ciencia", "Astronomía"));
    }

    @Test
    @DisplayName("Test agregar contenido válido")
    void testAgregarContenidoValido() {
        Pelicula pelicula = new Pelicula("Nueva Película", 120, "Acción", "Estudio");
        
        // Configurar mock para que no encuentre contenido duplicado
        when(mockRepository.buscarPorTitulo("Nueva Película")).thenReturn(new ArrayList<>());

        assertDoesNotThrow(() -> {
            contenidoService.agregarContenido(pelicula);
        });

        verify(mockRepository).buscarPorTitulo("Nueva Película");
        verify(mockRepository).guardar(pelicula);
    }

    @Test
    @DisplayName("Test agregar contenido duplicado")
    void testAgregarContenidoDuplicado() {
        Pelicula pelicula1 = new Pelicula("Avatar", 162, "Ciencia Ficción", "20th Century Studios");
        Pelicula pelicula2 = new Pelicula("Avatar", 120, "Acción", "Otro Estudio");
        
        // Configurar mock para que encuentre contenido duplicado
        when(mockRepository.buscarPorTitulo("Avatar")).thenReturn(List.of(pelicula1));

        assertThrows(IllegalArgumentException.class, () -> {
            contenidoService.agregarContenido(pelicula2);
        });

        verify(mockRepository).buscarPorTitulo("Avatar");
        verify(mockRepository, never()).guardar(pelicula2);
    }

    @Test
    @DisplayName("Test agregar contenido null")
    void testAgregarContenidoNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            contenidoService.agregarContenido(null);
        });

        verify(mockRepository, never()).guardar(any());
    }

    @Test
    @DisplayName("Test agregar contenido con datos inválidos")
    void testAgregarContenidoInvalido() {
        // Crear película con título vacío
        assertThrows(IllegalArgumentException.class, () -> {
            Pelicula peliculaInvalida = new Pelicula("", 120, "Acción", "Estudio");
            contenidoService.agregarContenido(peliculaInvalida);
        });
    }

    @Test
    @DisplayName("Test eliminar contenido existente")
    void testEliminarContenidoExistente() {
        int id = 1;
        Pelicula pelicula = new Pelicula("Avatar", 162, "Ciencia Ficción", "20th Century Studios");
        
        when(mockRepository.buscarPorId(id)).thenReturn(pelicula);

        assertDoesNotThrow(() -> {
            contenidoService.eliminarContenido(id);
        });

        verify(mockRepository).buscarPorId(id);
        verify(mockRepository).eliminar(id);
    }

    @Test
    @DisplayName("Test eliminar contenido inexistente")
    void testEliminarContenidoInexistente() {
        int id = 999;
        
        when(mockRepository.buscarPorId(id)).thenReturn(null);

        assertThrows(IllegalArgumentException.class, () -> {
            contenidoService.eliminarContenido(id);
        });

        verify(mockRepository).buscarPorId(id);
        verify(mockRepository, never()).eliminar(id);
    }

    @Test
    @DisplayName("Test actualizar contenido existente")
    void testActualizarContenidoExistente() {
        Pelicula pelicula = new Pelicula("Avatar Actualizado", 180, "Ciencia Ficción", "20th Century Studios");
        
        when(mockRepository.existe(pelicula.getId())).thenReturn(true);

        assertDoesNotThrow(() -> {
            contenidoService.actualizarContenido(pelicula);
        });

        verify(mockRepository).existe(pelicula.getId());
        verify(mockRepository).guardar(pelicula);
    }

    @Test
    @DisplayName("Test actualizar contenido inexistente")
    void testActualizarContenidoInexistente() {
        Pelicula pelicula = new Pelicula("Avatar", 162, "Ciencia Ficción", "20th Century Studios");
        
        when(mockRepository.existe(pelicula.getId())).thenReturn(false);

        assertThrows(IllegalArgumentException.class, () -> {
            contenidoService.actualizarContenido(pelicula);
        });

        verify(mockRepository).existe(pelicula.getId());
        verify(mockRepository, never()).guardar(pelicula);
    }

    @Test
    @DisplayName("Test obtener contenido por ID")
    void testObtenerContenidoPorId() {
        int id = 1;
        Pelicula pelicula = new Pelicula("Avatar", 162, "Ciencia Ficción", "20th Century Studios");
        
        when(mockRepository.buscarPorId(id)).thenReturn(pelicula);

        ContenidoAudiovisual resultado = contenidoService.obtenerContenidoPorId(id);

        assertEquals(pelicula, resultado);
        verify(mockRepository).buscarPorId(id);
    }

    @Test
    @DisplayName("Test obtener todos los contenidos")
    void testObtenerTodosLosContenidos() {
        when(mockRepository.obtenerTodos()).thenReturn(contenidosTest);

        List<ContenidoAudiovisual> resultado = contenidoService.obtenerTodosLosContenidos();

        assertEquals(contenidosTest, resultado);
        verify(mockRepository).obtenerTodos();
    }

    @Test
    @DisplayName("Test buscar por título")
    void testBuscarPorTitulo() {
        String titulo = "Avatar";
        List<ContenidoAudiovisual> resultadoEsperado = List.of(contenidosTest.get(0));
        
        when(mockRepository.buscarPorTitulo(titulo)).thenReturn(resultadoEsperado);

        List<ContenidoAudiovisual> resultado = contenidoService.buscarPorTitulo(titulo);

        assertEquals(resultadoEsperado, resultado);
        verify(mockRepository).buscarPorTitulo(titulo);
    }

    @Test
    @DisplayName("Test buscar por título vacío")
    void testBuscarPorTituloVacio() {
        assertThrows(IllegalArgumentException.class, () -> {
            contenidoService.buscarPorTitulo("");
        });

        assertThrows(IllegalArgumentException.class, () -> {
            contenidoService.buscarPorTitulo(null);
        });

        verify(mockRepository, never()).buscarPorTitulo(any());
    }

    @Test
    @DisplayName("Test filtrar por género")
    void testFiltrarPorGenero() {
        String genero = "Ciencia Ficción";
        List<ContenidoAudiovisual> resultadoEsperado = List.of(contenidosTest.get(0));
        
        when(mockRepository.buscarPorGenero(genero)).thenReturn(resultadoEsperado);

        List<ContenidoAudiovisual> resultado = contenidoService.filtrarPorGenero(genero);

        assertEquals(resultadoEsperado, resultado);
        verify(mockRepository).buscarPorGenero(genero);
    }

    @Test
    @DisplayName("Test filtrar por género vacío")
    void testFiltrarPorGeneroVacio() {
        assertThrows(IllegalArgumentException.class, () -> {
            contenidoService.filtrarPorGenero("");
        });

        assertThrows(IllegalArgumentException.class, () -> {
            contenidoService.filtrarPorGenero(null);
        });

        verify(mockRepository, never()).buscarPorGenero(any());
    }

    @Test
    @DisplayName("Test cargar datos")
    void testCargarDatos() {
        // No debe lanzar excepción incluso si falla la carga
        assertDoesNotThrow(() -> {
            contenidoService.cargarDatos();
        });

        verify(mockRepository).cargarDesdeArchivo("test.csv");
    }

    @Test
    @DisplayName("Test guardar datos")
    void testGuardarDatos() {
        assertDoesNotThrow(() -> {
            contenidoService.guardarDatos();
        });

        verify(mockRepository).guardarEnArchivo("test.csv");
    }

    @Test
    @DisplayName("Test cargar datos desde archivo específico")
    void testCargarDatosDesdeArchivoEspecifico() {
        String rutaArchivo = "archivo_especifico.csv";

        assertDoesNotThrow(() -> {
            contenidoService.cargarDatosDesdeArchivo(rutaArchivo);
        });

        verify(mockRepository).cargarDesdeArchivo(rutaArchivo);
    }

    @Test
    @DisplayName("Test guardar datos en archivo específico")
    void testGuardarDatosEnArchivoEspecifico() {
        String rutaArchivo = "archivo_especifico.csv";

        assertDoesNotThrow(() -> {
            contenidoService.guardarDatosEnArchivo(rutaArchivo);
        });

        verify(mockRepository).guardarEnArchivo(rutaArchivo);
    }

    @Test
    @DisplayName("Test obtener cantidad total")
    void testObtenerCantidadTotal() {
        when(mockRepository.obtenerTodos()).thenReturn(contenidosTest);

        int cantidad = contenidoService.getCantidadTotal();

        assertEquals(contenidosTest.size(), cantidad);
        verify(mockRepository).obtenerTodos();
    }

    @Test
    @DisplayName("Test filtrar por duración válida")
    void testFiltrarPorDuracionValida() {
        int duracionMinima = 30;
        int duracionMaxima = 120;
        
        // Crear mock del repositorio que implemente los métodos adicionales
        ContenidoRepository realRepository = mock(ContenidoRepository.class);
        ContenidoService serviceConRepositorio = new ContenidoService(realRepository, "test.csv");

        List<ContenidoAudiovisual> resultadoEsperado = List.of(contenidosTest.get(1));
        when(realRepository.buscarPorDuracion(duracionMinima, duracionMaxima)).thenReturn(resultadoEsperado);

        List<ContenidoAudiovisual> resultado = serviceConRepositorio.filtrarPorDuracion(duracionMinima, duracionMaxima);

        assertEquals(resultadoEsperado, resultado);
        verify(realRepository).buscarPorDuracion(duracionMinima, duracionMaxima);
    }

    @Test
    @DisplayName("Test filtrar por duración inválida")
    void testFiltrarPorDuracionInvalida() {
        // Duración mínima negativa
        assertThrows(IllegalArgumentException.class, () -> {
            contenidoService.filtrarPorDuracion(-10, 120);
        });

        // Duración máxima negativa
        assertThrows(IllegalArgumentException.class, () -> {
            contenidoService.filtrarPorDuracion(30, -10);
        });

        // Duración mínima mayor que máxima
        assertThrows(IllegalArgumentException.class, () -> {
            contenidoService.filtrarPorDuracion(120, 60);
        });
    }

    @Test
    @DisplayName("Test obtener estadísticas")
    void testObtenerEstadisticas() {
        when(mockRepository.obtenerTodos()).thenReturn(contenidosTest);
        
        // Crear mock del repositorio que implemente los métodos adicionales
        ContenidoRepository realRepository = mock(ContenidoRepository.class);
        ContenidoService serviceConRepositorio = new ContenidoService(realRepository, "test.csv");

        when(realRepository.obtenerTodos()).thenReturn(contenidosTest);
        when(realRepository.obtenerEstadisticasPorTipo()).thenReturn(
            java.util.Map.of("Pelicula", 1L, "SerieDeTV", 1L, "Documental", 1L)
        );

        String estadisticas = serviceConRepositorio.obtenerEstadisticas();

        assertNotNull(estadisticas);
        assertTrue(estadisticas.contains("ESTADÍSTICAS"));
        assertTrue(estadisticas.contains("Total de contenidos"));
        verify(realRepository).obtenerTodos();
        verify(realRepository).obtenerEstadisticasPorTipo();
    }
}