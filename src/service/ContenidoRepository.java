package service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import interfaces.IContenidoRepository;
import interfaces.IFileHandler;
import model.ContenidoAudiovisual;

/**
 * Repositorio para gestión de contenido audiovisual
 * Implementa Single Responsibility Principle (SRP) - responsabilidad única de gestionar datos
 * Implementa Dependency Inversion Principle (DIP) - depende de abstracciones
 */
public class ContenidoRepository implements IContenidoRepository {
    private final Map<Integer, ContenidoAudiovisual> contenidos;
    private final IFileHandler<ContenidoAudiovisual> fileHandler;

    public ContenidoRepository(IFileHandler<ContenidoAudiovisual> fileHandler) {
        this.contenidos = new HashMap<>();
        this.fileHandler = fileHandler;
    }

    @Override
    public void guardar(ContenidoAudiovisual contenido) {
        if (contenido == null) {
            throw new IllegalArgumentException("El contenido no puede ser null");
        }
        contenidos.put(contenido.getId(), contenido);
    }

    @Override
    public void eliminar(int id) {
        if (!contenidos.containsKey(id)) {
            throw new IllegalArgumentException("No existe contenido con ID: " + id);
        }
        contenidos.remove(id);
    }

    @Override
    public ContenidoAudiovisual buscarPorId(int id) {
        return contenidos.get(id);
    }

    @Override
    public List<ContenidoAudiovisual> obtenerTodos() {
        return new ArrayList<>(contenidos.values());
    }

    @Override
    public List<ContenidoAudiovisual> buscarPorTitulo(String titulo) {
        if (titulo == null || titulo.trim().isEmpty()) {
            return new ArrayList<>();
        }
        
        String tituloBusqueda = titulo.toLowerCase().trim();
        return contenidos.values().stream()
                .filter(c -> c.getTitulo().toLowerCase().contains(tituloBusqueda))
                .collect(Collectors.toList());
    }

    @Override
    public List<ContenidoAudiovisual> buscarPorGenero(String genero) {
        if (genero == null || genero.trim().isEmpty()) {
            return new ArrayList<>();
        }
        
        String generoBusqueda = genero.toLowerCase().trim();
        return contenidos.values().stream()
                .filter(c -> c.getGenero().toLowerCase().contains(generoBusqueda))
                .collect(Collectors.toList());
    }

    @Override
    public void cargarDesdeArchivo(String rutaArchivo) {
        try {
            List<ContenidoAudiovisual> contenidosCargados = fileHandler.leerArchivo(rutaArchivo);
            contenidos.clear(); // Limpiar datos existentes
            
            for (ContenidoAudiovisual contenido : contenidosCargados) {
                contenidos.put(contenido.getId(), contenido);
            }
            
            System.out.println("Cargados " + contenidosCargados.size() + " elementos desde " + rutaArchivo);
            
        } catch (Exception e) {
            throw new RuntimeException("Error al cargar datos desde archivo: " + e.getMessage(), e);
        }
    }

    @Override
    public void guardarEnArchivo(String rutaArchivo) {
        try {
            List<ContenidoAudiovisual> listaContenidos = new ArrayList<>(contenidos.values());
            fileHandler.escribirArchivo(rutaArchivo, listaContenidos);
            
            System.out.println("Guardados " + listaContenidos.size() + " elementos en " + rutaArchivo);
            
        } catch (Exception e) {
            throw new RuntimeException("Error al guardar datos en archivo: " + e.getMessage(), e);
        }
    }

    // Métodos adicionales útiles
    public int getCantidadTotal() {
        return contenidos.size();
    }

    public boolean existe(int id) {
        return contenidos.containsKey(id);
    }

    public void limpiar() {
        contenidos.clear();
    }

    public Map<String, Long> obtenerEstadisticasPorTipo() {
        return contenidos.values().stream()
                .collect(Collectors.groupingBy(
                    c -> c.getClass().getSimpleName(),
                    Collectors.counting()
                ));
    }

    public List<ContenidoAudiovisual> buscarPorDuracion(int duracionMinima, int duracionMaxima) {
        return contenidos.values().stream()
                .filter(c -> c.getDuracionEnMinutos() >= duracionMinima && 
                            c.getDuracionEnMinutos() <= duracionMaxima)
                .collect(Collectors.toList());
    }

    public List<ContenidoAudiovisual> ordenarPorTitulo() {
        return contenidos.values().stream()
                .sorted(Comparator.comparing(ContenidoAudiovisual::getTitulo))
                .collect(Collectors.toList());
    }

    public List<ContenidoAudiovisual> ordenarPorDuracion() {
        return contenidos.values().stream()
                .sorted(Comparator.comparing(ContenidoAudiovisual::getDuracionEnMinutos))
                .collect(Collectors.toList());
    }
}