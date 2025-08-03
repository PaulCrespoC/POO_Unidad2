package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase SerieDeTV - Responsabilidad única de representar una serie de TV
 * Extiende ContenidoAudiovisual (Liskov Substitution Principle)
 */
public class SerieDeTV extends ContenidoAudiovisual {
    private int numeroTemporadas;
    private List<Temporada> temporadas;

    public SerieDeTV(String titulo, int duracionEnMinutos, String genero, int numeroTemporadas) {
        super(titulo, duracionEnMinutos, genero);
        this.numeroTemporadas = validarNumeroTemporadas(numeroTemporadas);
        this.temporadas = new ArrayList<>();
    }

    private int validarNumeroTemporadas(int numero) {
        if (numero <= 0) {
            throw new IllegalArgumentException("El número de temporadas debe ser mayor a 0");
        }
        return numero;
    }

    // Getters y Setters
    public int getNumeroTemporadas() { return numeroTemporadas; }
    
    public void setNumeroTemporadas(int numeroTemporadas) {
        this.numeroTemporadas = validarNumeroTemporadas(numeroTemporadas);
    }

    public List<Temporada> getTemporadas() {
        return new ArrayList<>(temporadas); // Retorna copia para encapsulación
    }

    // Métodos para gestión de temporadas (Composición)
    public void agregarTemporada(Temporada temporada) {
        if (temporada != null && !temporadas.contains(temporada)) {
            temporadas.add(temporada);
        }
    }

    public void removerTemporada(Temporada temporada) {
        temporadas.remove(temporada);
    }

    public Temporada obtenerTemporada(int numero) {
        return temporadas.stream()
                .filter(t -> t.getNumeroTemporada() == numero)
                .findFirst()
                .orElse(null);
    }

    public int getTotalEpisodios() {
        return temporadas.stream()
                .mapToInt(Temporada::getNumeroEpisodios)
                .sum();
    }

    @Override
    public void mostrarDetalles() {
        System.out.println("=== SERIE DE TV ===");
        System.out.println("ID: " + getId());
        System.out.println("Título: " + getTitulo());
        System.out.println("Duración por episodio: " + getDuracionEnMinutos() + " minutos");
        System.out.println("Género: " + getGenero());
        System.out.println("Temporadas: " + numeroTemporadas);
        System.out.println("Total de episodios: " + getTotalEpisodios());
        
        if (!temporadas.isEmpty()) {
            System.out.println("Detalles de temporadas:");
            temporadas.forEach(temporada -> 
                System.out.println("  - " + temporada));
        }
        System.out.println();
    }

    @Override
    public String toCSV() {
        StringBuilder sb = new StringBuilder();
        sb.append("SERIE,")
          .append(getId()).append(",")
          .append(getTitulo()).append(",")
          .append(getDuracionEnMinutos()).append(",")
          .append(getGenero()).append(",")
          .append(numeroTemporadas);
        
        return sb.toString();
    }

    public static SerieDeTV fromCSV(String csvLine) {
        String[] parts = csvLine.split(",");
        if (parts.length < 6) {
            throw new IllegalArgumentException("Formato CSV inválido para Serie de TV");
        }
        
        String titulo = parts[2];
        int duracion = Integer.parseInt(parts[3]);
        String genero = parts[4];
        int temporadas = Integer.parseInt(parts[5]);
        
        return new SerieDeTV(titulo, duracion, genero, temporadas);
    }
}