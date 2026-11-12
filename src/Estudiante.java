public class Estudiante extends Persona{
    private String carrera;

    public Estudiante (String cedula, String nombre, String telefono, String carrera){
        super(cedula, nombre, telefono);
        this.carrera = carrera;
    }

    @Override
    public String toString() {
        return "Estudiante{" +
                "cedula='" + cedula + '\'' +
                "nombre='" + nombre + '\'' +
                "telefono='" + telefono + '\'' +
                "carrera='" + carrera + '\'' +
                '}';
    }
}
