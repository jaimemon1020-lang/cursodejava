public class Cliente extends Persona {

    private String direccionEnvio;

    Cliente(String nombre, String email, String direccionEnvio) {
        super(nombre, email);
        this.direccionEnvio = direccionEnvio;
    }

    public String getDireccionEnvio() {
        return this.direccionEnvio;
    }

    @Override
    public void describir() {
        super.describir();
        System.out.println("Direccion de envio: " + this.direccionEnvio);
    }
}
