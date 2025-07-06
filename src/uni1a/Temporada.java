package uni1a;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase Temporada que mantiene una relación de composición con SerieDeTV
 * Una temporada no puede existir sin una serie
 */
public class Temporada {
    private int numeroTemporada;
    private int numeroEpisodios;
    private String fechaEstreno;
    private String fechaFinalizacion;
    private List<String> episodios;
    
    public Temporada(int numeroTemporada, int numeroEpisodios, String fechaEstreno, String fechaFinalizacion) {
        this.numeroTemporada = numeroTemporada;
        this.numeroEpisodios = numeroEpisodios;
        this.fechaEstreno = fechaEstreno;
        this.fechaFinalizacion = fechaFinalizacion;
        this.episodios = new ArrayList<>();
    }
    
    // Getters y Setters
    public int getNumeroTemporada() {
        return numeroTemporada;
    }
    
    public void setNumeroTemporada(int numeroTemporada) {
        this.numeroTemporada = numeroTemporada;
    }
    
    public int getNumeroEpisodios() {
        return numeroEpisodios;
    }
    
    public void setNumeroEpisodios(int numeroEpisodios) {
        this.numeroEpisodios = numeroEpisodios;
    }
    
    public String getFechaEstreno() {
        return fechaEstreno;
    }
    
    public void setFechaEstreno(String fechaEstreno) {
        this.fechaEstreno = fechaEstreno;
    }
    
    public String getFechaFinalizacion() {
        return fechaFinalizacion;
    }
    
    public void setFechaFinalizacion(String fechaFinalizacion) {
        this.fechaFinalizacion = fechaFinalizacion;
    }
    
    public List<String> getEpisodios() {
        return episodios;
    }
    
    public void agregarEpisodio(String nombreEpisodio) {
        this.episodios.add(nombreEpisodio);
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
    
    @Override
    public String toString() {
        return "Temporada " + numeroTemporada + " (" + numeroEpisodios + " episodios)";
    }
} 