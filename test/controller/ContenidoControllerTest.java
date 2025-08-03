package controller;

import interfaces.IContenidoService;
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
 * Pruebas unitarias para ContenidoController
 */
public class ContenidoControllerTest {

    @Mock
    private IContenidoService mockService;

    private ContenidoController controller;
    private List<ContenidoAudiovisual> contenidosTest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        controller = new ContenidoController(mockService);
        
        // Crear contenidos de prueba
        contenidosTest = new ArrayList<>();
        contenidosTest.add(new Pelicula("Avatar", 162, "Ciencia Ficción", "20th Century Studios"));
        contenidosTest.add(new SerieDeTV("Game of Thrones", 60, "Fantasía", 8));
        contenidosTest.add(new Documental("Cosmos", 45, "Ciencia", "Astronomía"));
    }

    @Test
    @DisplayName("Test inicialización del controlador")
    void testInicializacion() {
        assertDoesNotThrow(() -> {
            controller.inicializar();
        });

        verify(mockService).cargarDatos();
    }

    @Test
    @DisplayName("Test agregar contenido")
    void testAgregarContenido() {
        Pelicula pelicula = new Pelicula("Nueva Película", 120, "Acción", "Estudio");

        assertDoesNotThrow(() -> {
            controller.agregarContenido(pelicula);
        });

        verify(mockService).agregarContenido(pelicula);
    }

    @Test
    @DisplayName("Test eliminar contenido")
    void testEliminarContenido() {
        int id = 1;

        assertDoesNotThrow(() -> {
            controller.eliminarContenido(id);
        });

        verify(mockService).eliminarContenido(id);
    }

    @Test
    @DisplayName("Test actualizar contenido")
    void testActualizarContenido() {
        Pelicula pelicula = new Pelicula("Avatar Actualizado", 180, "Ciencia Ficción", "20th Century Studios");

        assertDoesNotThrow(() -> {
            controller.actualizarContenido(pelicula);
        });

        verify(mockService).actualizarContenido(pelicula);
    }

    @Test
    @DisplayName("Test obtener contenido por ID")
    void testObtenerContenidoPorId() {
        int id = 1;
        Pelicula peliculaEsperada = new Pelicula("Avatar", 162, "Ciencia Ficción", "20th Century Studios");
        
        when(mockService.obtenerContenidoPorId(id)).thenReturn(peliculaEsperada);

        ContenidoAudiovisual resultado = controller.obtenerContenidoPorId(id);

        assertEquals(peliculaEsperada, resultado);
        verify(mockService).obtenerContenidoPorId(id);
    }

    @Test
    @DisplayName("Test obtener todos los contenidos")
    void testObtenerTodosLosContenidos() {
        when(mockService.obtenerTodosLosContenidos()).thenReturn(contenidosTest);

        List<ContenidoAudiovisual> resultado = controller.obtenerTodosLosContenidos();

        assertEquals(contenidosTest, resultado);
        verify(mockService).obtenerTodosLosContenidos();
    }

    @Test
    @DisplayName("Test buscar por título")
    void testBuscarPorTitulo() {
        String titulo = "Avatar";
        List<ContenidoAudiovisual> resultadoEsperado = List.of(contenidosTest.get(0));
        
        when(mockService.buscarPorTitulo(titulo)).thenReturn(resultadoEsperado);

        List<ContenidoAudiovisual> resultado = controller.buscarPorTitulo(titulo);

        assertEquals(resultadoEsperado, resultado);
        verify(mockService).buscarPorTitulo(titulo);
    }

    @Test
    @DisplayName("Test filtrar por género")
    void testFiltrarPorGenero() {
        String genero = "Ciencia Ficción";
        List<ContenidoAudiovisual> resultadoEsperado = List.of(contenidosTest.get(0));
        
        when(mockService.filtrarPorGenero(genero)).thenReturn(resultadoEsperado);

        List<ContenidoAudiovisual> resultado = controller.filtrarPorGenero(genero);

        assertEquals(resultadoEsperado, resultado);
        verify(mockService).filtrarPorGenero(genero);
    }

    @Test
    @DisplayName("Test cargar datos desde archivo")
    void testCargarDatosDesdeArchivo() {
        String rutaArchivo = "test.csv";

        assertDoesNotThrow(() -> {
            controller.cargarDatosDesdeArchivo(rutaArchivo);
        });

        verify(mockService).cargarDatosDesdeArchivo(rutaArchivo);
    }

    @Test
    @DisplayName("Test guardar datos en archivo")
    void testGuardarDatosEnArchivo() {
        String rutaArchivo = "test.csv";

        assertDoesNotThrow(() -> {
            controller.guardarDatosEnArchivo(rutaArchivo);
        });

        verify(mockService).guardarDatosEnArchivo(rutaArchivo);
    }

    @Test
    @DisplayName("Test guardar datos")
    void testGuardarDatos() {
        assertDoesNotThrow(() -> {
            controller.guardarDatos();
        });

        verify(mockService).guardarDatos();
    }

    @Test
    @DisplayName("Test obtener estadísticas")
    void testObtenerEstadisticas() {
        String estadisticasEsperadas = "=== ESTADÍSTICAS DEL SISTEMA ===\nTotal de contenidos: 3\n";
        
        when(mockService.obtenerEstadisticas()).thenReturn(estadisticasEsperadas);

        String resultado = controller.obtenerEstadisticas();

        assertEquals(estadisticasEsperadas, resultado);
        verify(mockService).obtenerEstadisticas();
    }

    @Test
    @DisplayName("Test obtener cantidad total")
    void testObtenerCantidadTotal() {
        int cantidadEsperada = 3;
        
        when(mockService.getCantidadTotal()).thenReturn(cantidadEsperada);

        int resultado = controller.getCantidadTotal();

        assertEquals(cantidadEsperada, resultado);
        verify(mockService).getCantidadTotal();
    }

    @Test
    @DisplayName("Test obtener contenidos ordenados por título")
    void testObtenerContenidosOrdenadosPorTitulo() {
        List<ContenidoAudiovisual> resultadoEsperado = new ArrayList<>(contenidosTest);
        
        when(mockService.obtenerContenidosOrdenadosPorTitulo()).thenReturn(resultadoEsperado);

        List<ContenidoAudiovisual> resultado = controller.obtenerContenidosOrdenadosPorTitulo();

        assertEquals(resultadoEsperado, resultado);
        verify(mockService).obtenerContenidosOrdenadosPorTitulo();
    }

    @Test
    @DisplayName("Test obtener contenidos ordenados por duración")
    void testObtenerContenidosOrdenadosPorDuracion() {
        List<ContenidoAudiovisual> resultadoEsperado = new ArrayList<>(contenidosTest);
        
        when(mockService.obtenerContenidosOrdenadosPorDuracion()).thenReturn(resultadoEsperado);

        List<ContenidoAudiovisual> resultado = controller.obtenerContenidosOrdenadosPorDuracion();

        assertEquals(resultadoEsperado, resultado);
        verify(mockService).obtenerContenidosOrdenadosPorDuracion();
    }

    @Test
    @DisplayName("Test filtrar por duración")
    void testFiltrarPorDuracion() {
        int duracionMinima = 30;
        int duracionMaxima = 120;
        List<ContenidoAudiovisual> resultadoEsperado = List.of(contenidosTest.get(1), contenidosTest.get(2));
        
        when(mockService.filtrarPorDuracion(duracionMinima, duracionMaxima)).thenReturn(resultadoEsperado);

        List<ContenidoAudiovisual> resultado = controller.filtrarPorDuracion(duracionMinima, duracionMaxima);

        assertEquals(resultadoEsperado, resultado);
        verify(mockService).filtrarPorDuracion(duracionMinima, duracionMaxima);
    }

    @Test
    @DisplayName("Test cerrar aplicación")
    void testCerrarAplicacion() {
        // No debe lanzar excepción
        assertDoesNotThrow(() -> {
            controller.cerrarAplicacion();
        });

        verify(mockService).guardarDatos();
    }

    @Test
    @DisplayName("Test cerrar aplicación con error")
    void testCerrarAplicacionConError() {
        // Configurar mock para que lance excepción
        doThrow(new RuntimeException("Error al guardar")).when(mockService).guardarDatos();

        // No debe lanzar excepción, debe manejar el error internamente
        assertDoesNotThrow(() -> {
            controller.cerrarAplicacion();
        });

        verify(mockService).guardarDatos();
    }

    @Test
    @DisplayName("Test manejo de excepciones en inicialización")
    void testManejoExcepcionesInicializacion() {
        // Configurar mock para que lance excepción
        doThrow(new RuntimeException("Error al cargar datos")).when(mockService).cargarDatos();

        // No debe lanzar excepción, debe manejar el error internamente
        assertDoesNotThrow(() -> {
            controller.inicializar();
        });

        verify(mockService).cargarDatos();
    }

    @Test
    @DisplayName("Test delegación correcta al servicio")
    void testDelegacionCorrectaAlServicio() {
        // Verificar que todas las llamadas se delegan correctamente al servicio
        Pelicula pelicula = new Pelicula("Test", 120, "Test", "Test");
        
        controller.agregarContenido(pelicula);
        controller.eliminarContenido(1);
        controller.actualizarContenido(pelicula);
        controller.obtenerContenidoPorId(1);
        controller.obtenerTodosLosContenidos();
        controller.buscarPorTitulo("Test");
        controller.filtrarPorGenero("Test");
        controller.cargarDatosDesdeArchivo("test.csv");
        controller.guardarDatosEnArchivo("test.csv");
        controller.guardarDatos();
        controller.obtenerEstadisticas();
        controller.getCantidadTotal();

        // Verificar que todas las llamadas llegaron al servicio
        verify(mockService).agregarContenido(pelicula);
        verify(mockService).eliminarContenido(1);
        verify(mockService).actualizarContenido(pelicula);
        verify(mockService).obtenerContenidoPorId(1);
        verify(mockService).obtenerTodosLosContenidos();
        verify(mockService).buscarPorTitulo("Test");
        verify(mockService).filtrarPorGenero("Test");
        verify(mockService).cargarDatosDesdeArchivo("test.csv");
        verify(mockService).guardarDatosEnArchivo("test.csv");
        verify(mockService).guardarDatos();
        verify(mockService).obtenerEstadisticas();
        verify(mockService).getCantidadTotal();
    }
}