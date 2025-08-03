package model;

/**
 * Clase Actor - Responsabilidad única de representar un actor
 * Aplicación de Single Responsibility Principle (SRP)
 */
public class Actor {
    private String nombre;
    private int edad;
    private String nacionalidad;
    private String tipoActor;

    public Actor(String nombre, int edad, String nacionalidad, String tipoActor) {
        this.nombre = validarTexto(nombre, "Nombre");
        this.edad = validarEdad(edad);
        this.nacionalidad = validarTexto(nacionalidad, "Nacionalidad");
        this.tipoActor = validarTipoActor(tipoActor);
    }

    private String validarTexto(String texto, String campo) {
        if (texto == null || texto.trim().isEmpty()) {
            throw new IllegalArgumentException(campo + " no puede estar vacío");
        }
        return texto.trim();
    }

    private int validarEdad(int edad) {
        if (edad < 0 || edad > 120) {
            throw new IllegalArgumentException("La edad debe estar entre 0 y 120 años");
        }
        return edad;
    }

    private String validarTipoActor(String tipo) {
        String tipoValidado = validarTexto(tipo, "Tipo de actor");
        String[] tiposValidos = {"Principal", "Secundario", "Extra"};
        
        for (String tipoValido : tiposValidos) {
            if (tipoValido.equalsIgnoreCase(tipoValidado)) {
                return tipoValido;
            }
        }
        
        throw new IllegalArgumentException("Tipo de actor debe ser: Principal, Secundario o Extra");
    }

    // Getters y Setters con validación
    public String getNombre() { return nombre; }
    public int getEdad() { return edad; }
    public String getNacionalidad() { return nacionalidad; }
    public String getTipoActor() { return tipoActor; }

    public void setNombre(String nombre) {
        this.nombre = validarTexto(nombre, "Nombre");
    }

    public void setEdad(int edad) {
        this.edad = validarEdad(edad);
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = validarTexto(nacionalidad, "Nacionalidad");
    }

    public void setTipoActor(String tipoActor) {
        this.tipoActor = validarTipoActor(tipoActor);
    }

    public void mostrarInformacion() {
        System.out.println("Actor: " + nombre);
        System.out.println("Edad: " + edad + " años");
        System.out.println("Nacionalidad: " + nacionalidad);
        System.out.println("Tipo: " + tipoActor);
    }

    public String toCSV() {
        return String.format("%s,%d,%s,%s", nombre, edad, nacionalidad, tipoActor);
    }

    public static Actor fromCSV(String csvLine) {
        String[] parts = csvLine.split(",");
        if (parts.length < 4) {
            throw new IllegalArgumentException("Formato CSV inválido para Actor");
        }
        
        String nombre = parts[0];
        int edad = Integer.parseInt(parts[1]);
        String nacionalidad = parts[2];
        String tipoActor = parts[3];
        
        return new Actor(nombre, edad, nacionalidad, tipoActor);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Actor actor = (Actor) obj;
        return nombre.equals(actor.nombre) && edad == actor.edad;
    }

    @Override
    public int hashCode() {
        return nombre.hashCode() + Integer.hashCode(edad);
    }

    @Override
    public String toString() {
        return String.format("%s (%s, %d años) - %s", nombre, nacionalidad, edad, tipoActor);
    }
}