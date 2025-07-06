package uni1a;

/**
 * Clase Actor que mantiene una relación de agregación con Pelicula
 * Un actor puede existir independientemente de una película
 */
public class Actor {
    private String nombre;
    private int edad;
    private String nacionalidad;
    private String tipoActor; // Principal, Secundario, Extra
    
    public Actor(String nombre, int edad, String nacionalidad, String tipoActor) {
        this.nombre = nombre;
        this.edad = edad;
        this.nacionalidad = nacionalidad;
        this.tipoActor = tipoActor;
    }
    
    // Getters y Setters
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public int getEdad() {
        return edad;
    }
    
    public void setEdad(int edad) {
        this.edad = edad;
    }
    
    public String getNacionalidad() {
        return nacionalidad;
    }
    
    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }
    
    public String getTipoActor() {
        return tipoActor;
    }
    
    public void setTipoActor(String tipoActor) {
        this.tipoActor = tipoActor;
    }
    
    public void mostrarInformacion() {
        System.out.println("Actor: " + nombre);
        System.out.println("Edad: " + edad);
        System.out.println("Nacionalidad: " + nacionalidad);
        System.out.println("Tipo: " + tipoActor);
    }
    
    @Override
    public String toString() {
        return nombre + " (" + tipoActor + ")";
    }
} 