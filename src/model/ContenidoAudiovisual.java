package model;

/**
 * Clase abstracta base para todo contenido audiovisual
 * Aplica Single Responsibility Principle (SRP) - responsabilidad única de definir estructura base
 * Aplica Open/Closed Principle (OCP) - abierta para extensión, cerrada para modificación
 */
public abstract class ContenidoAudiovisual {
    private static int contadorId = 0;
    private final int id;
    private String titulo;
    private int duracionEnMinutos;
    private String genero;

    public ContenidoAudiovisual(String titulo, int duracionEnMinutos, String genero) {
        this.id = ++contadorId;
        this.titulo = validarTexto(titulo, "Título");
        this.duracionEnMinutos = validarDuracion(duracionEnMinutos);
        this.genero = validarTexto(genero, "Género");
    }

    // Validaciones para asegurar datos correctos
    private String validarTexto(String texto, String campo) {
        if (texto == null || texto.trim().isEmpty()) {
            throw new IllegalArgumentException(campo + " no puede estar vacío");
        }
        return texto.trim();
    }

    private int validarDuracion(int duracion) {
        if (duracion <= 0) {
            throw new IllegalArgumentException("La duración debe ser mayor a 0");
        }
        return duracion;
    }

    // Getters
    public int getId() { return id; }
    public String getTitulo() { return titulo; }
    public int getDuracionEnMinutos() { return duracionEnMinutos; }
    public String getGenero() { return genero; }

    // Setters con validación
    public void setTitulo(String titulo) {
        this.titulo = validarTexto(titulo, "Título");
    }

    public void setDuracionEnMinutos(int duracionEnMinutos) {
        this.duracionEnMinutos = validarDuracion(duracionEnMinutos);
    }

    public void setGenero(String genero) {
        this.genero = validarTexto(genero, "Género");
    }

    // Método abstracto para polimorfismo (Template Method Pattern)
    public abstract void mostrarDetalles();
    
    // Método para obtener información básica como CSV
    public abstract String toCSV();
    
    // Método para crear objeto desde CSV
    public static ContenidoAudiovisual fromCSV(String csvLine) {
        // Este método será implementado en las subclases específicas
        throw new UnsupportedOperationException("Debe ser implementado en las subclases");
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        ContenidoAudiovisual that = (ContenidoAudiovisual) obj;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }

    @Override
    public String toString() {
        return String.format("%s - %s (%d min)", titulo, genero, duracionEnMinutos);
    }
}