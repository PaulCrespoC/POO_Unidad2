package controller;

import java.util.List;

import interfaces.IContenidoService;
import model.ContenidoAudiovisual;
import view.MainView;

/**
 * Controlador principal para el sistema de contenido audiovisual
 * Implementa el patrón MVC - Controlador
 * Aplica Dependency Inversion Principle (DIP) - depende de abstracciones
 */
public class ContenidoController {
    private final IContenidoService contenidoService;
    private MainView mainView;

    public ContenidoController(IContenidoService contenidoService) {
        this.contenidoService = contenidoService;
    }

    public void setMainView(MainView mainView) {
        this.mainView = mainView;
        this.mainView.setController(this);
    }

    public void inicializar() {
        try {
            contenidoService.cargarDatos();
            if (mainView != null) {
                mainView.actualizarTabla();
            }
        } catch (Exception e) {
            System.err.println("Error al inicializar: " + e.getMessage());
        }
    }

    // Métodos de gestión de contenido
    public void agregarContenido(ContenidoAudiovisual contenido) {
        contenidoService.agregarContenido(contenido);
    }

    public void eliminarContenido(int id) {
        contenidoService.eliminarContenido(id);
    }

    public void actualizarContenido(ContenidoAudiovisual contenido) {
        contenidoService.actualizarContenido(contenido);
    }

    public ContenidoAudiovisual obtenerContenidoPorId(int id) {
        return contenidoService.obtenerContenidoPorId(id);
    }

    public List<ContenidoAudiovisual> obtenerTodosLosContenidos() {
        return contenidoService.obtenerTodosLosContenidos();
    }

    // Métodos de búsqueda y filtrado
    public List<ContenidoAudiovisual> buscarPorTitulo(String titulo) {
        return contenidoService.buscarPorTitulo(titulo);
    }

    public List<ContenidoAudiovisual> filtrarPorGenero(String genero) {
        return contenidoService.filtrarPorGenero(genero);
    }

    // Métodos de archivo
    public void cargarDatosDesdeArchivo(String rutaArchivo) {
        contenidoService.cargarDatosDesdeArchivo(rutaArchivo);
    }

    public void guardarDatosEnArchivo(String rutaArchivo) {
        contenidoService.guardarDatosEnArchivo(rutaArchivo);
    }

    public void guardarDatos() {
        contenidoService.guardarDatos();
    }

    // Métodos de estadísticas y utilidades
    public String obtenerEstadisticas() {
        return contenidoService.obtenerEstadisticas();
    }

    public int getCantidadTotal() {
        return contenidoService.getCantidadTotal();
    }

    // Métodos adicionales para funcionalidades avanzadas
    public List<ContenidoAudiovisual> obtenerContenidosOrdenadosPorTitulo() {
        return contenidoService.obtenerContenidosOrdenadosPorTitulo();
    }

    public List<ContenidoAudiovisual> obtenerContenidosOrdenadosPorDuracion() {
        return contenidoService.obtenerContenidosOrdenadosPorDuracion();
    }

    public List<ContenidoAudiovisual> filtrarPorDuracion(int duracionMinima, int duracionMaxima) {
        return contenidoService.filtrarPorDuracion(duracionMinima, duracionMaxima);
    }

    // Método para cerrar la aplicación de forma segura
    public void cerrarAplicacion() {
        try {
            contenidoService.guardarDatos();
            System.out.println("Datos guardados exitosamente al cerrar la aplicación");
        } catch (Exception e) {
            System.err.println("Error al guardar datos: " + e.getMessage());
        }
    }
}