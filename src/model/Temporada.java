package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase Temporada - Responsabilidad única de representar una temporada
 * Aplicación de Single Responsibility Principle (SRP)
 */
public class Temporada {
    private int numeroTemporada;
    private int numeroEpisodios;
    private String fechaEstreno;
    private String fechaFinalizacion;
    private List<String> episodios;

    public Temporada(int numeroTemporada, int numeroEpisodios, String fechaEstreno, String fechaFinalizacion) {
        this.numeroTemporada = validarNumero(numeroTemporada, "Número de temporada");
        this.numeroEpisodios = validarNumero(numeroEpisodios, "Número de episodios");
        this.fechaEstreno = validarTexto(fechaEstreno, "Fecha de estreno");
        this.fechaFinalizacion = validarTexto(fechaFinalizacion, "Fecha de finalización");
        this.episodios = new ArrayList<>();
    }

    private int validarNumero(int numero, String campo) {
        if (numero <= 0) {
            throw new IllegalArgumentException(campo + " debe ser mayor a 0");
        }
        return numero;
    }

    private String validarTexto(String texto, String campo) {
        if (texto == null || texto.trim().isEmpty()) {
            throw new IllegalArgumentException(campo + " no puede estar vacío");
        }
        return texto.trim();
    }

    // Getters y Setters
    public int getNumeroTemporada() { return numeroTemporada; }
    public int getNumeroEpisodios() { return numeroEpisodios; }
    public String getFechaEstreno() { return fechaEstreno; }
    public String getFechaFinalizacion() { return fechaFinalizacion; }

    public List<String> getEpisodios() {
        return new ArrayList<>(episodios); // Retorna copia para encapsulación
    }

    public void setNumeroTemporada(int numeroTemporada) {
        this.numeroTemporada = validarNumero(numeroTemporada, "Número de temporada");
    }

    public void setNumeroEpisodios(int numeroEpisodios) {
        this.numeroEpisodios = validarNumero(numeroEpisodios, "Número de episodios");
    }

    public void setFechaEstreno(String fechaEstreno) {
        this.fechaEstreno = validarTexto(fechaEstreno, "Fecha de estreno");
    }

    public void setFechaFinalizacion(String fechaFinalizacion) {
        this.fechaFinalizacion = validarTexto(fechaFinalizacion, "Fecha de finalización");
    }

    // Métodos para gestionar episodios
    public void agregarEpisodio(String nombreEpisodio) {
        if (nombreEpisodio != null && !nombreEpisodio.trim().isEmpty()) {
            episodios.add(nombreEpisodio.trim());
        }
    }

    public void removerEpisodio(String nombreEpisodio) {
        episodios.remove(nombreEpisodio);
    }

    public boolean tieneEpisodio(String nombreEpisodio) {
        return episodios.contains(nombreEpisodio);
    }

    public void mostrarInformacion() {
        System.out.println("Temporada " + numeroTemporada);
        System.out.println("Episodios: " + numeroEpisodios);
        System.out.println("Estreno: " + fechaEstreno);
        System.out.println("Finalización: " + fechaFinalizacion);
        
        if (!episodios.isEmpty()) {
            System.out.println("Lista de episodios:");
            for (int i = 0; i < episodios.size(); i++) {
                System.out.println("  " + (i + 1) + ". " + episodios.get(i));
            }
        }
    }

    public String toCSV() {
        StringBuilder sb = new StringBuilder();
        sb.append(numeroTemporada).append(",")
          .append(numeroEpisodios).append(",")
          .append(fechaEstreno).append(",")
          .append(fechaFinalizacion);
        
        if (!episodios.isEmpty()) {
            sb.append(",");
            for (int i = 0; i < episodios.size(); i++) {
                if (i > 0) sb.append(";");
                sb.append(episodios.get(i));
            }
        }
        
        return sb.toString();
    }

    public static Temporada fromCSV(String csvLine) {
        String[] parts = csvLine.split(",");
        if (parts.length < 4) {
            throw new IllegalArgumentException("Formato CSV inválido para Temporada");
        }
        
        int numero = Integer.parseInt(parts[0]);
        int episodios = Integer.parseInt(parts[1]);
        String estreno = parts[2];
        String finalizacion = parts[3];
        
        return new Temporada(numero, episodios, estreno, finalizacion);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Temporada temporada = (Temporada) obj;
        return numeroTemporada == temporada.numeroTemporada;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(numeroTemporada);
    }

    @Override
    public String toString() {
        return String.format("Temporada %d (%d episodios) - %s a %s", 
                numeroTemporada, numeroEpisodios, fechaEstreno, fechaFinalizacion);
    }
}