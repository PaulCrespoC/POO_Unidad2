package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase Cortometraje - Responsabilidad única de representar un cortometraje
 * Extiende ContenidoAudiovisual (Liskov Substitution Principle)
 */
public class Cortometraje extends ContenidoAudiovisual {
    private String director;
    private String festival;
    private boolean esEstudiantil;
    private String tecnicaFilmacion;
    private List<String> premios;
    private int presupuesto;

    public Cortometraje(String titulo, int duracionEnMinutos, String genero, String director,
                       String festival, boolean esEstudiantil, String tecnicaFilmacion, int presupuesto) {
        super(titulo, duracionEnMinutos, genero);
        this.director = validarTexto(director, "Director");
        this.festival = validarTexto(festival, "Festival");
        this.esEstudiantil = esEstudiantil;
        this.tecnicaFilmacion = validarTecnicaFilmacion(tecnicaFilmacion);
        this.presupuesto = validarPresupuesto(presupuesto);
        this.premios = new ArrayList<>();
    }

    private String validarTexto(String texto, String campo) {
        if (texto == null || texto.trim().isEmpty()) {
            throw new IllegalArgumentException(campo + " no puede estar vacío");
        }
        return texto.trim();
    }

    private String validarTecnicaFilmacion(String tecnica) {
        String tecnicaValidada = validarTexto(tecnica, "Técnica de filmación");
        String[] tecnicasValidas = {"Digital", "Analógico", "Mixto"};
        
        for (String tecnicaValida : tecnicasValidas) {
            if (tecnicaValida.equalsIgnoreCase(tecnicaValidada)) {
                return tecnicaValida;
            }
        }
        
        throw new IllegalArgumentException("Técnica debe ser: Digital, Analógico o Mixto");
    }

    private int validarPresupuesto(int presupuesto) {
        if (presupuesto < 0) {
            throw new IllegalArgumentException("El presupuesto no puede ser negativo");
        }
        return presupuesto;
    }

    // Getters y Setters
    public String getDirector() { return director; }
    public String getFestival() { return festival; }
    public boolean isEsEstudiantil() { return esEstudiantil; }
    public String getTecnicaFilmacion() { return tecnicaFilmacion; }
    public int getPresupuesto() { return presupuesto; }

    public List<String> getPremios() {
        return new ArrayList<>(premios); // Retorna copia para encapsulación
    }

    public void setDirector(String director) {
        this.director = validarTexto(director, "Director");
    }

    public void setFestival(String festival) {
        this.festival = validarTexto(festival, "Festival");
    }

    public void setEsEstudiantil(boolean esEstudiantil) {
        this.esEstudiantil = esEstudiantil;
    }

    public void setTecnicaFilmacion(String tecnicaFilmacion) {
        this.tecnicaFilmacion = validarTecnicaFilmacion(tecnicaFilmacion);
    }

    public void setPresupuesto(int presupuesto) {
        this.presupuesto = validarPresupuesto(presupuesto);
    }

    // Métodos para gestión de premios
    public void agregarPremio(String premio) {
        if (premio != null && !premio.trim().isEmpty() && !premios.contains(premio.trim())) {
            premios.add(premio.trim());
        }
    }

    public void removerPremio(String premio) {
        premios.remove(premio);
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
        System.out.println("=== CORTOMETRAJE ===");
        System.out.println("ID: " + getId());
        System.out.println("Título: " + getTitulo());
        System.out.println("Duración: " + getDuracionEnMinutos() + " minutos");
        System.out.println("Género: " + getGenero());
        System.out.println("Director: " + director);
        System.out.println("Festival: " + festival);
        System.out.println("Categoría: " + getCategoria());
        System.out.println("Técnica de filmación: " + tecnicaFilmacion);
        System.out.println("Presupuesto: $" + presupuesto);
        
        if (tienePremios()) {
            System.out.println("Premios obtenidos:");
            premios.forEach(premio -> System.out.println("  - " + premio));
        } else {
            System.out.println("Sin premios hasta el momento");
        }
        System.out.println();
    }

    @Override
    public String toCSV() {
        return String.format("CORTOMETRAJE,%d,%s,%d,%s,%s,%s,%b,%s,%d",
                getId(), getTitulo(), getDuracionEnMinutos(), getGenero(),
                director, festival, esEstudiantil, tecnicaFilmacion, presupuesto);
    }

    public static Cortometraje fromCSV(String csvLine) {
        String[] parts = csvLine.split(",");
        if (parts.length < 9) {
            throw new IllegalArgumentException("Formato CSV inválido para Cortometraje");
        }
        
        String titulo = parts[2];
        int duracion = Integer.parseInt(parts[3]);
        String genero = parts[4];
        String director = parts[5];
        String festival = parts[6];
        boolean esEstudiantil = Boolean.parseBoolean(parts[7]);
        String tecnicaFilmacion = parts[8];
        int presupuesto = parts.length > 9 ? Integer.parseInt(parts[9]) : 0;
        
        return new Cortometraje(titulo, duracion, genero, director, festival,
                               esEstudiantil, tecnicaFilmacion, presupuesto);
    }
}