package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase Pelicula - Responsabilidad única de representar una película
 * Extiende ContenidoAudiovisual (Liskov Substitution Principle)
 */
public class Pelicula extends ContenidoAudiovisual {
    private String estudio;
    private List<Actor> actores;

    public Pelicula(String titulo, int duracionEnMinutos, String genero, String estudio) {
        super(titulo, duracionEnMinutos, genero);
        this.estudio = validarEstudio(estudio);
        this.actores = new ArrayList<>();
    }

    private String validarEstudio(String estudio) {
        if (estudio == null || estudio.trim().isEmpty()) {
            throw new IllegalArgumentException("El estudio no puede estar vacío");
        }
        return estudio.trim();
    }

    // Getters y Setters
    public String getEstudio() { return estudio; }
    
    public void setEstudio(String estudio) {
        this.estudio = validarEstudio(estudio);
    }

    public List<Actor> getActores() { 
        return new ArrayList<>(actores); // Retorna copia para encapsulación
    }

    // Métodos para gestión de actores (Agregación)
    public void agregarActor(Actor actor) {
        if (actor != null && !actores.contains(actor)) {
            actores.add(actor);
        }
    }

    public void removerActor(Actor actor) {
        actores.remove(actor);
    }

    public boolean tieneActor(Actor actor) {
        return actores.contains(actor);
    }

    public int getCantidadActores() {
        return actores.size();
    }

    @Override
    public void mostrarDetalles() {
        System.out.println("=== PELÍCULA ===");
        System.out.println("ID: " + getId());
        System.out.println("Título: " + getTitulo());
        System.out.println("Duración: " + getDuracionEnMinutos() + " minutos");
        System.out.println("Género: " + getGenero());
        System.out.println("Estudio: " + estudio);
        
        if (!actores.isEmpty()) {
            System.out.println("Actores:");
            actores.forEach(actor -> System.out.println("  - " + actor));
        }
        System.out.println();
    }

    @Override
    public String toCSV() {
        StringBuilder sb = new StringBuilder();
        sb.append("PELICULA,")
          .append(getId()).append(",")
          .append(getTitulo()).append(",")
          .append(getDuracionEnMinutos()).append(",")
          .append(getGenero()).append(",")
          .append(estudio);
        
        if (!actores.isEmpty()) {
            sb.append(",");
            for (int i = 0; i < actores.size(); i++) {
                if (i > 0) sb.append(";");
                sb.append(actores.get(i).getNombre());
            }
        }
        
        return sb.toString();
    }

    public static Pelicula fromCSV(String csvLine) {
        String[] parts = csvLine.split(",");
        if (parts.length < 6) {
            throw new IllegalArgumentException("Formato CSV inválido para Película");
        }
        
        String titulo = parts[2];
        int duracion = Integer.parseInt(parts[3]);
        String genero = parts[4];
        String estudio = parts[5];
        
        return new Pelicula(titulo, duracion, genero, estudio);
    }
}