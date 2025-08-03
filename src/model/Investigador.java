package model;

/**
 * Clase Investigador - Responsabilidad única de representar un investigador
 * Aplicación de Single Responsibility Principle (SRP)
 */
public class Investigador {
    private String nombre;
    private String especialidad;
    private String institucion;
    private int anosExperiencia;
    private String gradoAcademico;

    public Investigador(String nombre, String especialidad, String institucion, 
                       int anosExperiencia, String gradoAcademico) {
        this.nombre = validarTexto(nombre, "Nombre");
        this.especialidad = validarTexto(especialidad, "Especialidad");
        this.institucion = validarTexto(institucion, "Institución");
        this.anosExperiencia = validarAnosExperiencia(anosExperiencia);
        this.gradoAcademico = validarGradoAcademico(gradoAcademico);
    }

    private String validarTexto(String texto, String campo) {
        if (texto == null || texto.trim().isEmpty()) {
            throw new IllegalArgumentException(campo + " no puede estar vacío");
        }
        return texto.trim();
    }

    private int validarAnosExperiencia(int anos) {
        if (anos < 0 || anos > 60) {
            throw new IllegalArgumentException("Los años de experiencia deben estar entre 0 y 60");
        }
        return anos;
    }

    private String validarGradoAcademico(String grado) {
        String gradoValidado = validarTexto(grado, "Grado académico");
        String[] gradosValidos = {"Lic.", "Msc.", "Dr.", "Ph.D.", "Ing."};
        
        for (String gradoValido : gradosValidos) {
            if (gradoValido.equalsIgnoreCase(gradoValidado)) {
                return gradoValido;
            }
        }
        
        throw new IllegalArgumentException("Grado académico debe ser: Lic., Msc., Dr., Ph.D. o Ing.");
    }

    // Getters y Setters
    public String getNombre() { return nombre; }
    public String getEspecialidad() { return especialidad; }
    public String getInstitucion() { return institucion; }
    public int getAnosExperiencia() { return anosExperiencia; }
    public String getGradoAcademico() { return gradoAcademico; }

    public void setNombre(String nombre) {
        this.nombre = validarTexto(nombre, "Nombre");
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = validarTexto(especialidad, "Especialidad");
    }

    public void setInstitucion(String institucion) {
        this.institucion = validarTexto(institucion, "Institución");
    }

    public void setAnosExperiencia(int anosExperiencia) {
        this.anosExperiencia = validarAnosExperiencia(anosExperiencia);
    }

    public void setGradoAcademico(String gradoAcademico) {
        this.gradoAcademico = validarGradoAcademico(gradoAcademico);
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

    public String toCSV() {
        return String.format("%s,%s,%s,%d,%s", 
                nombre, especialidad, institucion, anosExperiencia, gradoAcademico);
    }

    public static Investigador fromCSV(String csvLine) {
        String[] parts = csvLine.split(",");
        if (parts.length < 5) {
            throw new IllegalArgumentException("Formato CSV inválido para Investigador");
        }
        
        String nombre = parts[0];
        String especialidad = parts[1];
        String institucion = parts[2];
        int anosExperiencia = Integer.parseInt(parts[3]);
        String gradoAcademico = parts[4];
        
        return new Investigador(nombre, especialidad, institucion, anosExperiencia, gradoAcademico);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Investigador that = (Investigador) obj;
        return nombre.equals(that.nombre) && especialidad.equals(that.especialidad);
    }

    @Override
    public int hashCode() {
        return nombre.hashCode() + especialidad.hashCode();
    }

    @Override
    public String toString() {
        return String.format("%s %s - %s (%s)", gradoAcademico, nombre, especialidad, institucion);
    }
}