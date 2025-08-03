package poo;
import uni1a.Actor;
import uni1a.ContenidoAudiovisual;
import uni1a.Cortometraje;
import uni1a.Documental;
import uni1a.Investigador;
import uni1a.Pelicula;
import uni1a.SerieDeTV;
import uni1a.Temporada;
import uni1a.VideoYouTube;

public class PruebaAudioVisual {
    public static void main(String[] args) {
        System.out.println("=== SISTEMA DE GESTIÓN DE CONTENIDO AUDIOVISUAL ===");
        System.out.println();

        // Crear actores
        Actor actor1 = new Actor("Leonardo DiCaprio", 49, "Estadounidense", "Principal");
        Actor actor2 = new Actor("Kate Winslet", 48, "Británica", "Principal");
        Actor actor3 = new Actor("Sam Worthington", 47, "Australiano", "Principal");

        // Crear investigadores
        Investigador investigador1 = new Investigador("Neil deGrasse Tyson", "Astrofísica", 
                                                     "Planetario Hayden", 25, "Dr.");
        Investigador investigador2 = new Investigador("Carl Sagan", "Astronomía", 
                                                     "Universidad Cornell", 30, "Dr.");

        // Crear películas con actores
        Pelicula pelicula1 = new Pelicula("Avatar", 162, "Ciencia Ficción", "20th Century Studios");
        pelicula1.agregarActor(actor3);
        pelicula1.agregarActor(new Actor("Zoe Saldana", 45, "Estadounidense", "Principal"));

        Pelicula pelicula2 = new Pelicula("Titanic", 195, "Drama", "Paramount Pictures");
        pelicula2.agregarActor(actor1);
        pelicula2.agregarActor(actor2);

        // Crear serie de TV con temporadas
        SerieDeTV serie1 = new SerieDeTV("Game of Thrones", 60, "Fantasía", 8);
        Temporada temp1 = new Temporada(1, 10, "2011-04-17", "2011-06-19");
        temp1.agregarEpisodio("Winter Is Coming");
        temp1.agregarEpisodio("The Kingsroad");
        temp1.agregarEpisodio("Lord Snow");
        serie1.agregarTemporada(temp1);

        Temporada temp2 = new Temporada(2, 10, "2012-04-01", "2012-06-03");
        temp2.agregarEpisodio("The North Remembers");
        temp2.agregarEpisodio("The Night Lands");
        serie1.agregarTemporada(temp2);

        // Crear documental con investigadores
        Documental documental1 = new Documental("Cosmos", 45, "Ciencia", "Astronomía");
        documental1.agregarInvestigador(investigador1);
        documental1.agregarInvestigador(investigador2);

        // Crear nuevas subclases
        VideoYouTube video1 = new VideoYouTube("Tutorial Java POO", 25, "Educativo", 
                                              "CodeAcademy", 15000, 1200, "2024-01-15", "1080p");
        video1.incrementarVisualizaciones();
        video1.incrementarLikes();

        Cortometraje corto1 = new Cortometraje("El Último Día", 15, "Drama", "María García", 
                                              "Festival de Cannes", false, "Digital", 75000);
        corto1.agregarPremio("Mejor Cortometraje - Festival de Cannes 2023");
        corto1.agregarPremio("Premio del Público - Festival Internacional de Cine");

        // Crear array con todos los contenidos
        ContenidoAudiovisual[] contenidos = {pelicula1, pelicula2, serie1, documental1, video1, corto1};

        // Mostrar los detalles de cada contenido audiovisual
        for (ContenidoAudiovisual contenido : contenidos) {
            contenido.mostrarDetalles();
        }

        // Demostrar funcionalidades específicas
        System.out.println("=== DEMOSTRACIONES ESPECÍFICAS ===");
        System.out.println();

        // Demostrar información de actores
        System.out.println("Información detallada de un actor:");
        actor1.mostrarInformacion();
        System.out.println();

        // Demostrar información de temporadas
        System.out.println("Información detallada de una temporada:");
        temp1.mostrarInformacion();
        System.out.println();

        // Demostrar información de investigadores
        System.out.println("Información detallada de un investigador:");
        investigador1.mostrarInformacion();
        investigador1.contribuirDocumental("Astronomía");
        System.out.println();

        // Demostrar funcionalidades específicas de VideoYouTube
        System.out.println("Estadísticas del video de YouTube:");
        System.out.println("Ratio de likes: " + String.format("%.2f", video1.calcularRatioLikes()) + "%");
        System.out.println();

        // Demostrar funcionalidades específicas de Cortometraje
        System.out.println("Información del cortometraje:");
        System.out.println("Categoría: " + corto1.getCategoria());
        System.out.println("¿Tiene premios?: " + (corto1.tienePremios() ? "Sí" : "No"));
        System.out.println();

        System.out.println("=== FIN DE LA DEMOSTRACIÓN ===");
    }
}