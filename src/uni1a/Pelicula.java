/**
 * Class Pelicula
 */
package uni1a;

import java.util.ArrayList;
import java.util.List;

// Subclase Pelicula que extiende de ContenidoAudiovisual
public class Pelicula extends ContenidoAudiovisual {
    private String estudio;
    private List<Actor> actores; // Relación de agregación con Actor

    public Pelicula(String titulo, int duracionEnMinutos, String genero, String estudio) {
        super(titulo, duracionEnMinutos, genero);
        this.estudio = estudio;
        this.actores = new ArrayList<>();
    }

    public String getEstudio() {
        return estudio;
    }

    public void setEstudio(String estudio) {
        this.estudio = estudio;
    }
    
    public List<Actor> getActores() {
        return actores;
    }
    
    public void agregarActor(Actor actor) {
        this.actores.add(actor);
    }
    
    public void removerActor(Actor actor) {
        this.actores.remove(actor);
    }
    
    @Override
    public void mostrarDetalles() {
        System.out.println("Detalles de la película:");
        System.out.println("ID: " + getId());
        System.out.println("Título: " + getTitulo());
        System.out.println("Duración en minutos: " + getDuracionEnMinutos());
        System.out.println("Género: " + getGenero());
        System.out.println("Estudio: " + estudio);
        if (!actores.isEmpty()) {
            System.out.println("Actores:");
            for (Actor actor : actores) {
                System.out.println("  - " + actor);
            }
        }
        System.out.println();
    }
}