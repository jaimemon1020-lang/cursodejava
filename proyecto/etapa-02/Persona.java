public class Persona {

    protected String nombre;
    protected String email;

    Persona(String nombre, String email) {
        this.nombre = nombre;
        this.email = email;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void describir() {
        System.out.println("Persona: " + this.nombre + " (" + this.email + ")");
    }
}
