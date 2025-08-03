package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase Documental - Responsabilidad única de representar un documental
 * Extiende ContenidoAudiovisual (Liskov Substitution Principle)
 */
public class Documental extends ContenidoAudiovisual {
    private String tema;
    private List<Investigador> investigadores;

    public Documental(String titulo, int duracionEnMinutos, String genero, String tema) {
        super(titulo, duracionEnMinutos, genero);
        this.tema = validarTema(tema);
        this.investigadores = new ArrayList<>();
    }

    private String validarTema(String tema) {
        if (tema == null || tema.trim().isEmpty()) {
            throw new IllegalArgumentException("El tema no puede estar vacío");
        }
        return tema.trim();
    }

    // Getters y Setters
    public String getTema() { return tema; }
    
    public void setTema(String tema) {
        this.tema = validarTema(tema);
    }

    public List<Investigador> getInvestigadores() {
        return new ArrayList<>(investigadores); // Retorna copia para encapsulación
    }

    // Métodos para gestión de investigadores (Asociación)
    public void agregarInvestigador(Investigador investigador) {
        if (investigador != null && !investigadores.contains(investigador)) {
            investigadores.add(investigador);
        }
    }

    public void removerInvestigador(Investigador investigador) {
        investigadores.remove(investigador);
    }

    public boolean tieneInvestigador(Investigador investigador) {
        return investigadores.contains(investigador);
    }

    public int getCantidadInvestigadores() {
        return investigadores.size();
    }

    @Override
    public void mostrarDetalles() {
        System.out.println("=== DOCUMENTAL ===");
        System.out.println("ID: " + getId());
        System.out.println("Título: " + getTitulo());
        System.out.println("Duración: " + getDuracionEnMinutos() + " minutos");
        System.out.println("Género: " + getGenero());
        System.out.println("Tema: " + tema);
        
        if (!investigadores.isEmpty()) {
            System.out.println("Investigadores participantes:");
            investigadores.forEach(investigador -> 
                System.out.println("  - " + investigador));
        }
        System.out.println();
    }

    @Override
    public String toCSV() {
        StringBuilder sb = new StringBuilder();
        sb.append("DOCUMENTAL,")
          .append(getId()).append(",")
          .append(getTitulo()).append(",")
          .append(getDuracionEnMinutos()).append(",")
          .append(getGenero()).append(",")
          .append(tema);
        
        return sb.toString();
    }

    public static Documental fromCSV(String csvLine) {
        String[] parts = csvLine.split(",");
        if (parts.length < 6) {
            throw new IllegalArgumentException("Formato CSV inválido para Documental");
        }
        
        String titulo = parts[2];
        int duracion = Integer.parseInt(parts[3]);
        String genero = parts[4];
        String tema = parts[5];
        
        return new Documental(titulo, duracion, genero, tema);
    }
}