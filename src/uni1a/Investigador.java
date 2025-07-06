package uni1a;

/**
 * Clase Investigador que mantiene una relación de asociación con Documental
 * Un investigador puede existir independientemente y puede participar en múltiples documentales
 */
public class Investigador {
    private String nombre;
    private String especialidad;
    private String institucion;
    private int anosExperiencia;
    private String gradoAcademico;
    
    public Investigador(String nombre, String especialidad, String institucion, int anosExperiencia, String gradoAcademico) {
        this.nombre = nombre;
        this.especialidad = especialidad;
        this.institucion = institucion;
        this.anosExperiencia = anosExperiencia;
        this.gradoAcademico = gradoAcademico;
    }
    
    // Getters y Setters
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String getEspecialidad() {
        return especialidad;
    }
    
    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }
    
    public String getInstitucion() {
        return institucion;
    }
    
    public void setInstitucion(String institucion) {
        this.institucion = institucion;
    }
    
    public int getAnosExperiencia() {
        return anosExperiencia;
    }
    
    public void setAnosExperiencia(int anosExperiencia) {
        this.anosExperiencia = anosExperiencia;
    }
    
    public String getGradoAcademico() {
        return gradoAcademico;
    }
    
    public void setGradoAcademico(String gradoAcademico) {
        this.gradoAcademico = gradoAcademico;
    }
    
    public void mostrarInformacion() {
        System.out.println("Investigador: " + nombre);
        System.out.println("Grado Académico: " + gradoAcademico);
        System.out.println("Especialidad: " + especialidad);
        System.out.println("Institución: " + institucion);
        System.out.println("Años de experiencia: " + anosExperiencia);
    }
    
    public void contribuirDocumental(String temaDocumental) {
        System.out.println(nombre + " está contribuyendo su expertise en " + especialidad + 
                          " para el documental sobre " + temaDocumental);
    }
    
    @Override
    public String toString() {
        return gradoAcademico + " " + nombre + " - " + especialidad;
    }
} 