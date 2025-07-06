package uni1a;

/**
 * Subclase VideoYouTube que extiende de ContenidoAudiovisual
 */
public class VideoYouTube extends ContenidoAudiovisual {
    private String canal;
    private int visualizaciones;
    private int likes;
    private String fechaPublicacion;
    private String calidad; // 720p, 1080p, 4K
    
    public VideoYouTube(String titulo, int duracionEnMinutos, String genero, String canal, 
                       int visualizaciones, int likes, String fechaPublicacion, String calidad) {
        super(titulo, duracionEnMinutos, genero);
        this.canal = canal;
        this.visualizaciones = visualizaciones;
        this.likes = likes;
        this.fechaPublicacion = fechaPublicacion;
        this.calidad = calidad;
    }
    
    // Getters y Setters
    public String getCanal() {
        return canal;
    }
    
    public void setCanal(String canal) {
        this.canal = canal;
    }
    
    public int getVisualizaciones() {
        return visualizaciones;
    }
    
    public void setVisualizaciones(int visualizaciones) {
        this.visualizaciones = visualizaciones;
    }
    
    public int getLikes() {
        return likes;
    }
    
    public void setLikes(int likes) {
        this.likes = likes;
    }
    
    public String getFechaPublicacion() {
        return fechaPublicacion;
    }
    
    public void setFechaPublicacion(String fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }
    
    public String getCalidad() {
        return calidad;
    }
    
    public void setCalidad(String calidad) {
        this.calidad = calidad;
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
        System.out.println("Detalles del Video de YouTube:");
        System.out.println("ID: " + getId());
        System.out.println("Título: " + getTitulo());
        System.out.println("Duración en minutos: " + getDuracionEnMinutos());
        System.out.println("Género: " + getGenero());
        System.out.println("Canal: " + canal);
        System.out.println("Visualizaciones: " + visualizaciones);
        System.out.println("Likes: " + likes);
        System.out.println("Fecha de publicación: " + fechaPublicacion);
        System.out.println("Calidad: " + calidad);
        System.out.println("Ratio de likes: " + String.format("%.2f", calcularRatioLikes()) + "%");
        System.out.println();
    }
} 