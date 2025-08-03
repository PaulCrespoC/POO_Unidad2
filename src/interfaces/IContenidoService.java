package interfaces;

import java.util.List;

import model.ContenidoAudiovisual;

/**
 * Interfaz para servicios de contenido
 * Segregación de interfaces (ISP)
 */
public interface IContenidoService {
    void agregarContenido(ContenidoAudiovisual contenido);
    void eliminarContenido(int id);
    void actualizarContenido(ContenidoAudiovisual contenido);
    ContenidoAudiovisual obtenerContenidoPorId(int id);
    List<ContenidoAudiovisual> obtenerTodosLosContenidos();
    List<ContenidoAudiovisual> filtrarPorGenero(String genero);
    List<ContenidoAudiovisual> buscarPorTitulo(String titulo);
    void cargarDatos();
    void guardarDatos();
    void cargarDatosDesdeArchivo(String rutaArchivo);
    void guardarDatosEnArchivo(String rutaArchivo);
    String obtenerEstadisticas();
    int getCantidadTotal();
    List<ContenidoAudiovisual> obtenerContenidosOrdenadosPorTitulo();
    List<ContenidoAudiovisual> obtenerContenidosOrdenadosPorDuracion();
    List<ContenidoAudiovisual> filtrarPorDuracion(int duracionMinima, int duracionMaxima);
}