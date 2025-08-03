package service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import interfaces.IFileHandler;
import model.ContenidoAudiovisual;
import model.Cortometraje;
import model.Documental;
import model.Pelicula;
import model.SerieDeTV;
import model.VideoYouTube;

/**
 * Servicio para manejo de archivos CSV
 * Implementa Single Responsibility Principle (SRP) - responsabilidad única de manejar archivos
 */
public class FileHandlerService implements IFileHandler<ContenidoAudiovisual> {
    
    private static final String SEPARADOR = ",";
    private static final String CHARSET = "UTF-8";

    @Override
    public List<ContenidoAudiovisual> leerArchivo(String rutaArchivo) {
        List<ContenidoAudiovisual> contenidos = new ArrayList<>();
        
        if (!validarFormato(rutaArchivo)) {
            throw new IllegalArgumentException("El archivo debe tener extensión .csv");
        }

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(rutaArchivo), CHARSET))) {
            
            String linea;
            int numeroLinea = 0;
            
            while ((linea = reader.readLine()) != null) {
                numeroLinea++;
                
                // Saltar líneas vacías y comentarios
                if (linea.trim().isEmpty() || linea.startsWith("#")) {
                    continue;
                }
                
                try {
                    ContenidoAudiovisual contenido = parsearLinea(linea);
                    if (contenido != null) {
                        contenidos.add(contenido);
                    }
                } catch (Exception e) {
                    System.err.println("Error en línea " + numeroLinea + ": " + e.getMessage());
                }
            }
            
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Archivo no encontrado: " + rutaArchivo, e);
        } catch (IOException e) {
            throw new RuntimeException("Error al leer el archivo: " + rutaArchivo, e);
        }
        
        return contenidos;
    }

    @Override
    public void escribirArchivo(String rutaArchivo, List<ContenidoAudiovisual> datos) {
        if (!validarFormato(rutaArchivo)) {
            throw new IllegalArgumentException("El archivo debe tener extensión .csv");
        }

        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(rutaArchivo), CHARSET))) {
            
            // Escribir encabezado
            writer.write("# Archivo de Contenido Audiovisual");
            writer.newLine();
            writer.write("# Formato: TIPO,ID,TITULO,DURACION,GENERO,CAMPO_ESPECIFICO");
            writer.newLine();
            writer.newLine();
            
            // Escribir datos
            for (ContenidoAudiovisual contenido : datos) {
                writer.write(contenido.toCSV());
                writer.newLine();
            }
            
        } catch (IOException e) {
            throw new RuntimeException("Error al escribir el archivo: " + rutaArchivo, e);
        }
    }

    @Override
    public boolean validarFormato(String rutaArchivo) {
        return rutaArchivo != null && rutaArchivo.toLowerCase().endsWith(".csv");
    }

    /**
     * Parsea una línea CSV y crea el objeto correspondiente
     */
    private ContenidoAudiovisual parsearLinea(String linea) {
        String[] partes = linea.split(SEPARADOR);
        
        if (partes.length < 2) {
            throw new IllegalArgumentException("Formato de línea inválido");
        }
        
        String tipo = partes[0].trim().toUpperCase();
        
        switch (tipo) {
            case "PELICULA":
                return parsearPelicula(partes);
            case "SERIE":
                return parsearSerie(partes);
            case "DOCUMENTAL":
                return parsearDocumental(partes);
            case "YOUTUBE":
                return parsearVideoYouTube(partes);
            case "CORTOMETRAJE":
                return parsearCortometraje(partes);
            default:
                throw new IllegalArgumentException("Tipo de contenido desconocido: " + tipo);
        }
    }

    private Pelicula parsearPelicula(String[] partes) {
        if (partes.length < 6) {
            throw new IllegalArgumentException("Formato inválido para Película");
        }
        
        String titulo = partes[2];
        int duracion = Integer.parseInt(partes[3]);
        String genero = partes[4];
        String estudio = partes[5];
        
        return new Pelicula(titulo, duracion, genero, estudio);
    }

    private SerieDeTV parsearSerie(String[] partes) {
        if (partes.length < 6) {
            throw new IllegalArgumentException("Formato inválido para Serie de TV");
        }
        
        String titulo = partes[2];
        int duracion = Integer.parseInt(partes[3]);
        String genero = partes[4];
        int temporadas = Integer.parseInt(partes[5]);
        
        return new SerieDeTV(titulo, duracion, genero, temporadas);
    }

    private Documental parsearDocumental(String[] partes) {
        if (partes.length < 6) {
            throw new IllegalArgumentException("Formato inválido para Documental");
        }
        
        String titulo = partes[2];
        int duracion = Integer.parseInt(partes[3]);
        String genero = partes[4];
        String tema = partes[5];
        
        return new Documental(titulo, duracion, genero, tema);
    }

    private VideoYouTube parsearVideoYouTube(String[] partes) {
        if (partes.length < 9) {
            throw new IllegalArgumentException("Formato inválido para Video de YouTube");
        }
        
        String titulo = partes[2];
        int duracion = Integer.parseInt(partes[3]);
        String genero = partes[4];
        String canal = partes[5];
        int visualizaciones = Integer.parseInt(partes[6]);
        int likes = Integer.parseInt(partes[7]);
        String fechaPublicacion = partes[8];
        String calidad = partes.length > 9 ? partes[9] : "720p";
        
        return new VideoYouTube(titulo, duracion, genero, canal, 
                               visualizaciones, likes, fechaPublicacion, calidad);
    }

    private Cortometraje parsearCortometraje(String[] partes) {
        if (partes.length < 9) {
            throw new IllegalArgumentException("Formato inválido para Cortometraje");
        }
        
        String titulo = partes[2];
        int duracion = Integer.parseInt(partes[3]);
        String genero = partes[4];
        String director = partes[5];
        String festival = partes[6];
        boolean esEstudiantil = Boolean.parseBoolean(partes[7]);
        String tecnicaFilmacion = partes[8];
        int presupuesto = partes.length > 9 ? Integer.parseInt(partes[9]) : 0;
        
        return new Cortometraje(titulo, duracion, genero, director, festival, 
                               esEstudiantil, tecnicaFilmacion, presupuesto);
    }

    /**
     * Crear archivo de ejemplo con datos de prueba
     */
    public void crearArchivoEjemplo(String rutaArchivo) {
        List<ContenidoAudiovisual> ejemplos = new ArrayList<>();
        
        ejemplos.add(new Pelicula("Avatar", 162, "Ciencia Ficción", "20th Century Studios"));
        ejemplos.add(new SerieDeTV("Game of Thrones", 60, "Fantasía", 8));
        ejemplos.add(new Documental("Cosmos", 45, "Ciencia", "Astronomía"));
        ejemplos.add(new VideoYouTube("Tutorial Java POO", 25, "Educativo", 
                                    "CodeAcademy", 15000, 1200, "2024-01-15", "1080p"));
        ejemplos.add(new Cortometraje("El Último Día", 15, "Drama", "María García", 
                                    "Festival de Cannes", false, "Digital", 75000));
        
        escribirArchivo(rutaArchivo, ejemplos);
    }
}