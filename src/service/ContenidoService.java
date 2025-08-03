package service;

import java.util.List;

import interfaces.IContenidoRepository;
import interfaces.IContenidoService;
import model.ContenidoAudiovisual;

/**
 * Servicio de negocio para contenido audiovisual
 * Implementa Single Responsibility Principle (SRP) - lógica de negocio
 * Implementa Dependency Inversion Principle (DIP) - depende de abstracciones
 */
public class ContenidoService implements IContenidoService {
    private final IContenidoRepository repository;
    private final String rutaArchivoPorDefecto;

    public ContenidoService(IContenidoRepository repository, String rutaArchivoPorDefecto) {
        this.repository = repository;
        this.rutaArchivoPorDefecto = rutaArchivoPorDefecto;
    }

    @Override
    public void agregarContenido(ContenidoAudiovisual contenido) {
        validarContenido(contenido);
        
        // Verificar que no exista un contenido con el mismo título y tipo
        List<ContenidoAudiovisual> existentes = repository.buscarPorTitulo(contenido.getTitulo());
        boolean yaExiste = existentes.stream()
                .anyMatch(c -> c.getClass().equals(contenido.getClass()) && 
                              c.getTitulo().equalsIgnoreCase(contenido.getTitulo()));
        
        if (yaExiste) {
            throw new IllegalArgumentException("Ya existe un " + contenido.getClass().getSimpleName() + 
                                             " con el título: " + contenido.getTitulo());
        }
        
        repository.guardar(contenido);
    }

    @Override
    public void eliminarContenido(int id) {
        ContenidoAudiovisual contenido = repository.buscarPorId(id);
        if (contenido == null) {
            throw new IllegalArgumentException("No existe contenido con ID: " + id);
        }
        repository.eliminar(id);
    }

    @Override
    public void actualizarContenido(ContenidoAudiovisual contenido) {
        validarContenido(contenido);
        
        if (!repository.existe(contenido.getId())) {
            throw new IllegalArgumentException("No existe contenido con ID: " + contenido.getId());
        }
        
        repository.guardar(contenido);
    }

    @Override
    public ContenidoAudiovisual obtenerContenidoPorId(int id) {
        return repository.buscarPorId(id);
    }

    @Override
    public List<ContenidoAudiovisual> obtenerTodosLosContenidos() {
        return repository.obtenerTodos();
    }

    @Override
    public List<ContenidoAudiovisual> filtrarPorGenero(String genero) {
        if (genero == null || genero.trim().isEmpty()) {
            throw new IllegalArgumentException("El género no puede estar vacío");
        }
        return repository.buscarPorGenero(genero);
    }

    @Override
    public List<ContenidoAudiovisual> buscarPorTitulo(String titulo) {
        if (titulo == null || titulo.trim().isEmpty()) {
            throw new IllegalArgumentException("El título no puede estar vacío");
        }
        return repository.buscarPorTitulo(titulo);
    }

    @Override
    public void cargarDatos() {
        try {
            repository.cargarDesdeArchivo(rutaArchivoPorDefecto);
        } catch (Exception e) {
            System.err.println("Advertencia: No se pudieron cargar los datos: " + e.getMessage());
            // No lanzar excepción para permitir que la aplicación continúe
        }
    }

    @Override
    public void guardarDatos() {
        try {
            repository.guardarEnArchivo(rutaArchivoPorDefecto);
        } catch (Exception e) {
            throw new RuntimeException("Error al guardar los datos: " + e.getMessage(), e);
        }
    }

    // Métodos adicionales para funcionalidades específicas
    public int getCantidadTotal() {
        return repository.obtenerTodos().size();
    }

    public List<ContenidoAudiovisual> obtenerContenidosOrdenadosPorTitulo() {
        return ((ContenidoRepository) repository).ordenarPorTitulo();
    }

    public List<ContenidoAudiovisual> obtenerContenidosOrdenadosPorDuracion() {
        return ((ContenidoRepository) repository).ordenarPorDuracion();
    }

    public List<ContenidoAudiovisual> filtrarPorDuracion(int duracionMinima, int duracionMaxima) {
        if (duracionMinima < 0 || duracionMaxima < 0 || duracionMinima > duracionMaxima) {
            throw new IllegalArgumentException("Rangos de duración inválidos");
        }
        return ((ContenidoRepository) repository).buscarPorDuracion(duracionMinima, duracionMaxima);
    }

    public String obtenerEstadisticas() {
        StringBuilder stats = new StringBuilder();
        stats.append("=== ESTADÍSTICAS DEL SISTEMA ===\n");
        stats.append("Total de contenidos: ").append(getCantidadTotal()).append("\n");
        
        var estadisticasPorTipo = ((ContenidoRepository) repository).obtenerEstadisticasPorTipo();
        for (var entrada : estadisticasPorTipo.entrySet()) {
            stats.append(entrada.getKey()).append(": ").append(entrada.getValue()).append("\n");
        }
        
        return stats.toString();
    }

    public void cargarDatosDesdeArchivo(String rutaArchivo) {
        repository.cargarDesdeArchivo(rutaArchivo);
    }

    public void guardarDatosEnArchivo(String rutaArchivo) {
        repository.guardarEnArchivo(rutaArchivo);
    }

    private void validarContenido(ContenidoAudiovisual contenido) {
        if (contenido == null) {
            throw new IllegalArgumentException("El contenido no puede ser null");
        }
        
        if (contenido.getTitulo() == null || contenido.getTitulo().trim().isEmpty()) {
            throw new IllegalArgumentException("El título no puede estar vacío");
        }
        
        if (contenido.getDuracionEnMinutos() <= 0) {
            throw new IllegalArgumentException("La duración debe ser mayor a 0");
        }
        
        if (contenido.getGenero() == null || contenido.getGenero().trim().isEmpty()) {
            throw new IllegalArgumentException("El género no puede estar vacío");
        }
    }
}