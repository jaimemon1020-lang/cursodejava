public class RegistroDeOperaciones implements AutoCloseable {

    RegistroDeOperaciones() {
        System.out.println("Registro de operaciones abierto");
    }

    public void registrar(String mensaje) {
        System.out.println("LOG: " + mensaje);
    }

    @Override
    public void close() {
        System.out.println("Registro de operaciones cerrado");
    }
}
