package model;

/**
 * Clase VideoYouTube - Responsabilidad única de representar un video de YouTube
 * Extiende ContenidoAudiovisual (Liskov Substitution Principle)
 */
public class VideoYouTube extends ContenidoAudiovisual {
    private String canal;
    private int visualizaciones;
    private int likes;
    private String fechaPublicacion;
    private String calidad;

    public VideoYouTube(String titulo, int duracionEnMinutos, String genero, String canal,
                       int visualizaciones, int likes, String fechaPublicacion, String calidad) {
        super(titulo, duracionEnMinutos, genero);
        this.canal = validarTexto(canal, "Canal");
        this.visualizaciones = validarNumero(visualizaciones, "Visualizaciones");
        this.likes = validarNumero(likes, "Likes");
        this.fechaPublicacion = validarTexto(fechaPublicacion, "Fecha de publicación");
        this.calidad = validarCalidad(calidad);
    }

    private String validarTexto(String texto, String campo) {
        if (texto == null || texto.trim().isEmpty()) {
            throw new IllegalArgumentException(campo + " no puede estar vacío");
        }
        return texto.trim();
    }

    private int validarNumero(int numero, String campo) {
        if (numero < 0) {
            throw new IllegalArgumentException(campo + " no puede ser negativo");
        }
        return numero;
    }

    private String validarCalidad(String calidad) {
        String calidadValidada = validarTexto(calidad, "Calidad");
        String[] calidadesValidas = {"480p", "720p", "1080p", "1440p", "4K"};
        
        for (String calidadValida : calidadesValidas) {
            if (calidadValida.equalsIgnoreCase(calidadValidada)) {
                return calidadValida;
            }
        }
        
        throw new IllegalArgumentException("Calidad debe ser: 480p, 720p, 1080p, 1440p o 4K");
    }

    // Getters y Setters
    public String getCanal() { return canal; }
    public int getVisualizaciones() { return visualizaciones; }
    public int getLikes() { return likes; }
    public String getFechaPublicacion() { return fechaPublicacion; }
    public String getCalidad() { return calidad; }

    public void setCanal(String canal) {
        this.canal = validarTexto(canal, "Canal");
    }

    public void setVisualizaciones(int visualizaciones) {
        this.visualizaciones = validarNumero(visualizaciones, "Visualizaciones");
    }

    public void setLikes(int likes) {
        this.likes = validarNumero(likes, "Likes");
    }

    public void setFechaPublicacion(String fechaPublicacion) {
        this.fechaPublicacion = validarTexto(fechaPublicacion, "Fecha de publicación");
    }

    public void setCalidad(String calidad) {
        this.calidad = validarCalidad(calidad);
    }

    // Métodos específicos
    public void incrementarVisualizaciones() {
        this.visualizaciones++;
    }

    public void incrementarLikes() {
        this.likes++;
    }

    public double calcularRatioLikes() {
        if (visualizaciones == 0) return 0.0;
        return (double) likes / visualizaciones * 100;
    }

    @Override
    public void mostrarDetalles() {
        System.out.println("=== VIDEO DE YOUTUBE ===");
        System.out.println("ID: " + getId());
        System.out.println("Título: " + getTitulo());
        System.out.println("Duración: " + getDuracionEnMinutos() + " minutos");
        System.out.println("Género: " + getGenero());
        System.out.println("Canal: " + canal);
        System.out.println("Visualizaciones: " + visualizaciones);
        System.out.println("Likes: " + likes);
        System.out.println("Fecha de publicación: " + fechaPublicacion);
        System.out.println("Calidad: " + calidad);
        System.out.println("Ratio de likes: " + String.format("%.2f", calcularRatioLikes()) + "%");
        System.out.println();
    }

    @Override
    public String toCSV() {
        return String.format("YOUTUBE,%d,%s,%d,%s,%s,%d,%d,%s,%s",
                getId(), getTitulo(), getDuracionEnMinutos(), getGenero(),
                canal, visualizaciones, likes, fechaPublicacion, calidad);
    }

    public static VideoYouTube fromCSV(String csvLine) {
        String[] parts = csvLine.split(",");
        if (parts.length < 9) {
            throw new IllegalArgumentException("Formato CSV inválido para Video de YouTube");
        }
        
        String titulo = parts[2];
        int duracion = Integer.parseInt(parts[3]);
        String genero = parts[4];
        String canal = parts[5];
        int visualizaciones = Integer.parseInt(parts[6]);
        int likes = Integer.parseInt(parts[7]);
        String fechaPublicacion = parts[8];
        String calidad = parts.length > 9 ? parts[9] : "720p";
        
        return new VideoYouTube(titulo, duracion, genero, canal, 
                               visualizaciones, likes, fechaPublicacion, calidad);
    }
}