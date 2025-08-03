package interfaces;

import java.util.List;

import model.ContenidoAudiovisual;

/**
 * Interfaz para el repositorio de contenido audiovisual
 * Aplica el principio de Inversión de Dependencias (DIP)
 */
public interface IContenidoRepository {
    void guardar(ContenidoAudiovisual contenido);
    void eliminar(int id);
    ContenidoAudiovisual buscarPorId(int id);
    List<ContenidoAudiovisual> obtenerTodos();
    List<ContenidoAudiovisual> buscarPorTitulo(String titulo);
    List<ContenidoAudiovisual> buscarPorGenero(String genero);
    void cargarDesdeArchivo(String rutaArchivo);
    void guardarEnArchivo(String rutaArchivo);
    boolean existe(int id);
}