package uni1a;

import java.util.ArrayList;
import java.util.List;

/**
 * Subclase Cortometraje que extiende de ContenidoAudiovisual
 */
public class Cortometraje extends ContenidoAudiovisual {
    private String director;
    private String festival; // Festival donde se presentó
    private boolean esEstudiantil;
    private String tecnicaFilmacion; // Digital, Analógico, Mixto
    private List<String> premios;
    private int presupuesto;
    
    public Cortometraje(String titulo, int duracionEnMinutos, String genero, String director, 
                       String festival, boolean esEstudiantil, String tecnicaFilmacion, int presupuesto) {
        super(titulo, duracionEnMinutos, genero);
        this.director = director;
        this.festival = festival;
        this.esEstudiantil = esEstudiantil;
        this.tecnicaFilmacion = tecnicaFilmacion;
        this.presupuesto = presupuesto;
        this.premios = new ArrayList<>();
    }
    
    // Getters y Setters
    public String getDirector() {
        return director;
    }
    
    public void setDirector(String director) {
        this.director = director;
    }
    
    public String getFestival() {
        return festival;
    }
    
    public void setFestival(String festival) {
        this.festival = festival;
    }
    
    public boolean isEsEstudiantil() {
        return esEstudiantil;
    }
    
    public void setEsEstudiantil(boolean esEstudiantil) {
        this.esEstudiantil = esEstudiantil;
    }
    
    public String getTecnicaFilmacion() {
        return tecnicaFilmacion;
    }
    
    public void setTecnicaFilmacion(String tecnicaFilmacion) {
        this.tecnicaFilmacion = tecnicaFilmacion;
    }
    
    public List<String> getPremios() {
        return premios;
    }
    
    public int getPresupuesto() {
        return presupuesto;
    }
    
    public void setPresupuesto(int presupuesto) {
        this.presupuesto = presupuesto;
    }
    
    // Métodos específicos
    public void agregarPremio(String premio) {
        this.premios.add(premio);
    }
    
    public boolean tienePremios() {
        return !premios.isEmpty();
    }
    
    public String getCategoria() {
        if (esEstudiantil) {
            return "Cortometraje Estudiantil";
        } else if (presupuesto < 50000) {
            return "Cortometraje Independiente";
        } else {
            return "Cortometraje Profesional";
        }
    }
    
    @Override
    public void mostrarDetalles() {
        System.out.println("Detalles del Cortometraje:");
        System.out.println("ID: " + getId());
        System.out.println("Título: " + getTitulo());
        System.out.println("Duración en minutos: " + getDuracionEnMinutos());
        System.out.println("Género: " + getGenero());
        System.out.println("Director: " + director);
        System.out.println("Festival: " + festival);
        System.out.println("Categoría: " + getCategoria());
        System.out.println("Técnica de filmación: " + tecnicaFilmacion);
        System.out.println("Presupuesto: $" + presupuesto);
        if (tienePremios()) {
            System.out.println("Premios obtenidos:");
            for (String premio : premios) {
                System.out.println("  - " + premio);
            }
        } else {
            System.out.println("Sin premios hasta el momento");
        }
        System.out.println();
    }
} 