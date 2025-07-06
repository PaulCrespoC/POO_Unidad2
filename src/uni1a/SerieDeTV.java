/**
 * Class SerieDeTV
 */
package uni1a;

import java.util.ArrayList;
import java.util.List;

// Subclase SerieDeTV que extiende de ContenidoAudiovisual
public class SerieDeTV extends ContenidoAudiovisual {
    private int temporadas;
    private List<Temporada> listaTemporadas; // Relación de composición con Temporada

    public SerieDeTV(String titulo, int duracionEnMinutos, String genero, int temporadas) {
        super(titulo, duracionEnMinutos, genero);
        this.temporadas = temporadas;
        this.listaTemporadas = new ArrayList<>();
    }

    public int getTemporadas() {
        return temporadas;
    }

    public void setTemporadas(int temporadas) {
        this.temporadas = temporadas;
    }
    
    public List<Temporada> getListaTemporadas() {
        return listaTemporadas;
    }
    
    public void agregarTemporada(Temporada temporada) {
        this.listaTemporadas.add(temporada);
    }
    
    public void removerTemporada(Temporada temporada) {
        this.listaTemporadas.remove(temporada);
    }
    
    @Override
    public void mostrarDetalles() {
        System.out.println("Detalles de la serie de TV:");
        System.out.println("ID: " + getId());
        System.out.println("Título: " + getTitulo());
        System.out.println("Duración en minutos: " + getDuracionEnMinutos());
        System.out.println("Género: " + getGenero());
        System.out.println("Temporadas: " + this.temporadas);
        if (!listaTemporadas.isEmpty()) {
            System.out.println("Detalles de temporadas:");
            for (Temporada temporada : listaTemporadas) {
                System.out.println("  - " + temporada);
            }
        }
        System.out.println();
    }
}