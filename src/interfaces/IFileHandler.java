package interfaces;

import java.util.List;

/**
 * Interfaz para manejo de archivos
 * Segregación de interfaces (ISP)
 */
public interface IFileHandler<T> {
    List<T> leerArchivo(String rutaArchivo);
    void escribirArchivo(String rutaArchivo, List<T> datos);
    boolean validarFormato(String rutaArchivo);
}