package model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para la clase Actor
 */
public class ActorTest {

    private Actor actor;

    @BeforeEach
    void setUp() {
        actor = new Actor("Leonardo DiCaprio", 49, "Estadounidense", "Principal");
    }

    @Test
    @DisplayName("Test creación de actor válido")
    void testCreacionActorValido() {
        assertNotNull(actor);
        assertEquals("Leonardo DiCaprio", actor.getNombre());
        assertEquals(49, actor.getEdad());
        assertEquals("Estadounidense", actor.getNacionalidad());
        assertEquals("Principal", actor.getTipoActor());
    }

    @Test
    @DisplayName("Test validación de nombre")
    void testValidacionNombre() {
        // Nombre vacío
        assertThrows(IllegalArgumentException.class, () -> {
            new Actor("", 25, "Español", "Principal");
        });

        // Nombre null
        assertThrows(IllegalArgumentException.class, () -> {
            new Actor(null, 25, "Español", "Principal");
        });

        // Nombre solo espacios
        assertThrows(IllegalArgumentException.class, () -> {
            new Actor("   ", 25, "Español", "Principal");
        });
    }

    @Test
    @DisplayName("Test validación de edad")
    void testValidacionEdad() {
        // Edad negativa
        assertThrows(IllegalArgumentException.class, () -> {
            new Actor("Nombre", -1, "Español", "Principal");
        });

        // Edad mayor a 120
        assertThrows(IllegalArgumentException.class, () -> {
            new Actor("Nombre", 121, "Español", "Principal");
        });

        // Edades límite válidas
        assertDoesNotThrow(() -> {
            new Actor("Bebé", 0, "Español", "Extra");
        });

        assertDoesNotThrow(() -> {
            new Actor("Anciano", 120, "Español", "Principal");
        });
    }

    @Test
    @DisplayName("Test validación de tipo de actor")
    void testValidacionTipoActor() {
        // Tipos válidos
        assertDoesNotThrow(() -> {
            new Actor("Actor1", 30, "Español", "Principal");
        });

        assertDoesNotThrow(() -> {
            new Actor("Actor2", 30, "Español", "Secundario");
        });

        assertDoesNotThrow(() -> {
            new Actor("Actor3", 30, "Español", "Extra");
        });

        // Tipo inválido
        assertThrows(IllegalArgumentException.class, () -> {
            new Actor("Actor", 30, "Español", "Protagonista");
        });

        // Tipo vacío
        assertThrows(IllegalArgumentException.class, () -> {
            new Actor("Actor", 30, "Español", "");
        });
    }

    @Test
    @DisplayName("Test setters con validación")
    void testSettersConValidacion() {
        // Cambiar nombre válido
        actor.setNombre("Brad Pitt");
        assertEquals("Brad Pitt", actor.getNombre());

        // Cambiar edad válida
        actor.setEdad(60);
        assertEquals(60, actor.getEdad());

        // Cambiar nacionalidad válida
        actor.setNacionalidad("Británico");
        assertEquals("Británico", actor.getNacionalidad());

        // Cambiar tipo válido
        actor.setTipoActor("Secundario");
        assertEquals("Secundario", actor.getTipoActor());

        // Intentar cambiar a valores inválidos
        assertThrows(IllegalArgumentException.class, () -> {
            actor.setNombre("");
        });

        assertThrows(IllegalArgumentException.class, () -> {
            actor.setEdad(-5);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            actor.setTipoActor("TipoInválido");
        });
    }

    @Test
    @DisplayName("Test equals y hashCode")
    void testEqualsYHashCode() {
        Actor actor2 = new Actor("Leonardo DiCaprio", 49, "Italiano", "Secundario");
        Actor actor3 = new Actor("Brad Pitt", 49, "Estadounidense", "Principal");
        Actor actor4 = new Actor("Leonardo DiCaprio", 50, "Estadounidense", "Principal");

        // Actores con mismo nombre y edad deben ser iguales
        assertEquals(actor, actor2);
        assertEquals(actor.hashCode(), actor2.hashCode());

        // Actores con diferente nombre no deben ser iguales
        assertNotEquals(actor, actor3);

        // Actores con diferente edad no deben ser iguales
        assertNotEquals(actor, actor4);

        // Un objeto debe ser igual a sí mismo
        assertEquals(actor, actor);
    }

    @Test
    @DisplayName("Test toString")
    void testToString() {
        String toString = actor.toString();
        assertTrue(toString.contains("Leonardo DiCaprio"));
        assertTrue(toString.contains("Estadounidense"));
        assertTrue(toString.contains("49"));
        assertTrue(toString.contains("Principal"));
    }

    @Test
    @DisplayName("Test CSV serialization")
    void testCSVSerialization() {
        String csv = actor.toCSV();
        assertEquals("Leonardo DiCaprio,49,Estadounidense,Principal", csv);

        // Test fromCSV
        Actor actorDesdeCSV = Actor.fromCSV(csv);
        assertEquals(actor.getNombre(), actorDesdeCSV.getNombre());
        assertEquals(actor.getEdad(), actorDesdeCSV.getEdad());
        assertEquals(actor.getNacionalidad(), actorDesdeCSV.getNacionalidad());
        assertEquals(actor.getTipoActor(), actorDesdeCSV.getTipoActor());
    }

    @Test
    @DisplayName("Test fromCSV con formato inválido")
    void testFromCSVFormatoInvalido() {
        assertThrows(IllegalArgumentException.class, () -> {
            Actor.fromCSV("datos,insuficientes");
        });

        assertThrows(NumberFormatException.class, () -> {
            Actor.fromCSV("Nombre,EdadNoNumerica,Nacionalidad,Tipo");
        });
    }

    @Test
    @DisplayName("Test caso insensitive en tipo de actor")
    void testTipoActorCaseInsensitive() {
        // Debe aceptar tipos en diferentes casos
        Actor actor1 = new Actor("Actor1", 30, "Español", "principal");
        assertEquals("Principal", actor1.getTipoActor());

        Actor actor2 = new Actor("Actor2", 30, "Español", "SECUNDARIO");
        assertEquals("Secundario", actor2.getTipoActor());

        Actor actor3 = new Actor("Actor3", 30, "Español", "extra");
        assertEquals("Extra", actor3.getTipoActor());
    }

    @Test
    @DisplayName("Test trimming de espacios")
    void testTrimmingEspacios() {
        Actor actorConEspacios = new Actor("  Leonardo DiCaprio  ", 49, "  Estadounidense  ", "  Principal  ");
        assertEquals("Leonardo DiCaprio", actorConEspacios.getNombre());
        assertEquals("Estadounidense", actorConEspacios.getNacionalidad());
        assertEquals("Principal", actorConEspacios.getTipoActor());
    }
}