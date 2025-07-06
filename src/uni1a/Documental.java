/**
 * Class Documental
 */
package uni1a;

import java.util.ArrayList;
import java.util.List;

// Subclase Documental que extiende de ContenidoAudiovisual
public class Documental extends ContenidoAudiovisual {
    private String tema;
    private List<Investigador> investigadores; // Relación de asociación con Investigador

    public Documental(String titulo, int duracionEnMinutos, String genero, String tema) {
        super(titulo, duracionEnMinutos, genero);
        this.tema = tema;
        this.investigadores = new ArrayList<>();
    }

    public String getTema() {
        return tema;
    }

    public void setTema(String tema) {
        this.tema = tema;
    }
    
    public List<Investigador> getInvestigadores() {
        return investigadores;
    }
    
    public void agregarInvestigador(Investigador investigador) {
        this.investigadores.add(investigador);
    }
    
    public void removerInvestigador(Investigador investigador) {
        this.investigadores.remove(investigador);
    }
    
    @Override
    public void mostrarDetalles() {
        System.out.println("Detalles del documental:");
        System.out.println("ID: " + getId());
        System.out.println("Título: " + getTitulo());
        System.out.println("Duración en minutos: " + getDuracionEnMinutos());
        System.out.println("Género: " + getGenero());
        System.out.println("Tema: " + this.tema);
        if (!investigadores.isEmpty()) {
            System.out.println("Investigadores participantes:");
            for (Investigador investigador : investigadores) {
                System.out.println("  - " + investigador);
            }
        }
        System.out.println();
    }
}