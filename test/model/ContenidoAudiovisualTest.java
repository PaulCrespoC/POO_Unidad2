package model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para las clases del modelo
 */
public class ContenidoAudiovisualTest {

    private Pelicula pelicula;
    private SerieDeTV serie;
    private Documental documental;
    private VideoYouTube video;
    private Cortometraje cortometraje;

    @BeforeEach
    void setUp() {
        pelicula = new Pelicula("Avatar", 162, "Ciencia Ficción", "20th Century Studios");
        serie = new SerieDeTV("Game of Thrones", 60, "Fantasía", 8);
        documental = new Documental("Cosmos", 45, "Ciencia", "Astronomía");
        video = new VideoYouTube("Tutorial Java", 25, "Educativo", "CodeAcademy", 15000, 1200, "2024-01-15", "1080p");
        cortometraje = new Cortometraje("El Último Día", 15, "Drama", "María García", "Festival de Cannes", false, "Digital", 75000);
    }

    @Test
    @DisplayName("Test creación de Película")
    void testCreacionPelicula() {
        assertNotNull(pelicula);
        assertEquals("Avatar", pelicula.getTitulo());
        assertEquals(162, pelicula.getDuracionEnMinutos());
        assertEquals("Ciencia Ficción", pelicula.getGenero());
        assertEquals("20th Century Studios", pelicula.getEstudio());
        assertTrue(pelicula.getId() > 0);
    }

    @Test
    @DisplayName("Test creación de Serie de TV")
    void testCreacionSerie() {
        assertNotNull(serie);
        assertEquals("Game of Thrones", serie.getTitulo());
        assertEquals(60, serie.getDuracionEnMinutos());
        assertEquals("Fantasía", serie.getGenero());
        assertEquals(8, serie.getNumeroTemporadas());
        assertTrue(serie.getId() > 0);
    }

    @Test
    @DisplayName("Test creación de Documental")
    void testCreacionDocumental() {
        assertNotNull(documental);
        assertEquals("Cosmos", documental.getTitulo());
        assertEquals(45, documental.getDuracionEnMinutos());
        assertEquals("Ciencia", documental.getGenero());
        assertEquals("Astronomía", documental.getTema());
        assertTrue(documental.getId() > 0);
    }

    @Test
    @DisplayName("Test creación de Video YouTube")
    void testCreacionVideoYouTube() {
        assertNotNull(video);
        assertEquals("Tutorial Java", video.getTitulo());
        assertEquals(25, video.getDuracionEnMinutos());
        assertEquals("Educativo", video.getGenero());
        assertEquals("CodeAcademy", video.getCanal());
        assertEquals(15000, video.getVisualizaciones());
        assertEquals(1200, video.getLikes());
        assertEquals("2024-01-15", video.getFechaPublicacion());
        assertEquals("1080p", video.getCalidad());
    }

    @Test
    @DisplayName("Test creación de Cortometraje")
    void testCreacionCortometraje() {
        assertNotNull(cortometraje);
        assertEquals("El Último Día", cortometraje.getTitulo());
        assertEquals(15, cortometraje.getDuracionEnMinutos());
        assertEquals("Drama", cortometraje.getGenero());
        assertEquals("María García", cortometraje.getDirector());
        assertEquals("Festival de Cannes", cortometraje.getFestival());
        assertFalse(cortometraje.isEsEstudiantil());
        assertEquals("Digital", cortometraje.getTecnicaFilmacion());
        assertEquals(75000, cortometraje.getPresupuesto());
    }

    @Test
    @DisplayName("Test validación de datos inválidos")
    void testValidacionDatosInvalidos() {
        // Título vacío
        assertThrows(IllegalArgumentException.class, () -> {
            new Pelicula("", 120, "Acción", "Estudio");
        });

        // Duración negativa
        assertThrows(IllegalArgumentException.class, () -> {
            new Pelicula("Título", -10, "Acción", "Estudio");
        });

        // Género vacío
        assertThrows(IllegalArgumentException.class, () -> {
            new Pelicula("Título", 120, "", "Estudio");
        });

        // Estudio vacío
        assertThrows(IllegalArgumentException.class, () -> {
            new Pelicula("Título", 120, "Acción", "");
        });
    }

    @Test
    @DisplayName("Test agregación de actores a película")
    void testAgregarActoresPelicula() {
        Actor actor1 = new Actor("Leonardo DiCaprio", 49, "Estadounidense", "Principal");
        Actor actor2 = new Actor("Kate Winslet", 48, "Británica", "Principal");

        pelicula.agregarActor(actor1);
        pelicula.agregarActor(actor2);

        assertEquals(2, pelicula.getCantidadActores());
        assertTrue(pelicula.tieneActor(actor1));
        assertTrue(pelicula.tieneActor(actor2));

        // No debe agregar actor duplicado
        pelicula.agregarActor(actor1);
        assertEquals(2, pelicula.getCantidadActores());
    }

    @Test
    @DisplayName("Test composición de temporadas en serie")
    void testTemporadasSerie() {
        Temporada temp1 = new Temporada(1, 10, "2011-04-17", "2011-06-19");
        Temporada temp2 = new Temporada(2, 10, "2012-04-01", "2012-06-03");

        serie.agregarTemporada(temp1);
        serie.agregarTemporada(temp2);

        assertEquals(2, serie.getTemporadas().size());
        assertEquals(temp1, serie.obtenerTemporada(1));
        assertEquals(temp2, serie.obtenerTemporada(2));
    }

    @Test
    @DisplayName("Test asociación de investigadores en documental")
    void testInvestigadoresDocumental() {
        Investigador inv1 = new Investigador("Neil deGrasse Tyson", "Astrofísica", "Planetario Hayden", 25, "Dr.");
        Investigador inv2 = new Investigador("Carl Sagan", "Astronomía", "Universidad Cornell", 30, "Dr.");

        documental.agregarInvestigador(inv1);
        documental.agregarInvestigador(inv2);

        assertEquals(2, documental.getCantidadInvestigadores());
        assertTrue(documental.tieneInvestigador(inv1));
        assertTrue(documental.tieneInvestigador(inv2));
    }

    @Test
    @DisplayName("Test cálculo de ratio de likes en YouTube")
    void testRatioLikesYouTube() {
        double ratioEsperado = (double) 1200 / 15000 * 100;
        assertEquals(ratioEsperado, video.calcularRatioLikes(), 0.01);

        // Test con visualizaciones = 0
        VideoYouTube videoSinVisualizaciones = new VideoYouTube("Test", 10, "Test", "Canal", 0, 0, "2024-01-01", "720p");
        assertEquals(0.0, videoSinVisualizaciones.calcularRatioLikes());
    }

    @Test
    @DisplayName("Test categorización automática de cortometraje")
    void testCategorizacionCortometraje() {
        // Cortometraje profesional (presupuesto >= 50000 y no estudiantil)
        assertEquals("Cortometraje Profesional", cortometraje.getCategoria());

        // Cortometraje estudiantil
        Cortometraje estudiantil = new Cortometraje("Tesis", 10, "Drama", "Estudiante", "Festival Universitario", true, "Digital", 5000);
        assertEquals("Cortometraje Estudiantil", estudiantil.getCategoria());

        // Cortometraje independiente
        Cortometraje independiente = new Cortometraje("Indie", 20, "Drama", "Director", "Festival Indie", false, "Digital", 30000);
        assertEquals("Cortometraje Independiente", independiente.getCategoria());
    }

    @Test
    @DisplayName("Test gestión de premios en cortometraje")
    void testPremiosCortometraje() {
        assertFalse(cortometraje.tienePremios());

        cortometraje.agregarPremio("Mejor Cortometraje");
        cortometraje.agregarPremio("Premio del Público");

        assertTrue(cortometraje.tienePremios());
        assertEquals(2, cortometraje.getPremios().size());

        // No debe agregar premio duplicado
        cortometraje.agregarPremio("Mejor Cortometraje");
        assertEquals(2, cortometraje.getPremios().size());
    }

    @Test
    @DisplayName("Test incremento de métricas en YouTube")
    void testIncrementoMetricasYouTube() {
        int visualizacionesIniciales = video.getVisualizaciones();
        int likesIniciales = video.getLikes();

        video.incrementarVisualizaciones();
        video.incrementarLikes();

        assertEquals(visualizacionesIniciales + 1, video.getVisualizaciones());
        assertEquals(likesIniciales + 1, video.getLikes());
    }

    @Test
    @DisplayName("Test equals y hashCode")
    void testEqualsYHashCode() {
        // Los objetos deben ser iguales si tienen el mismo ID
        Pelicula otraPelicula = new Pelicula("Otro Título", 100, "Otro Género", "Otro Estudio");
        
        // Como tienen IDs diferentes, no deben ser iguales
        assertNotEquals(pelicula, otraPelicula);
        assertNotEquals(pelicula.hashCode(), otraPelicula.hashCode());

        // Un objeto debe ser igual a sí mismo
        assertEquals(pelicula, pelicula);
        assertEquals(pelicula.hashCode(), pelicula.hashCode());
    }

    @Test
    @DisplayName("Test toString")
    void testToString() {
        String toString = pelicula.toString();
        assertTrue(toString.contains("Avatar"));
        assertTrue(toString.contains("Ciencia Ficción"));
        assertTrue(toString.contains("162"));
    }

    @Test
    @DisplayName("Test CSV serialization")
    void testCSVSerialization() {
        String csv = pelicula.toCSV();
        assertNotNull(csv);
        assertTrue(csv.contains("PELICULA"));
        assertTrue(csv.contains("Avatar"));
        assertTrue(csv.contains("162"));
        assertTrue(csv.contains("Ciencia Ficción"));
        assertTrue(csv.contains("20th Century Studios"));
    }
}