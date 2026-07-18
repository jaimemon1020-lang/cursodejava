public class EnvioExpress extends MetodoEnvio {

    EnvioExpress() {
        super("Envio express");
    }

    @Override
    public double calcularCosto(double totalCompra) {
        return 3500.0;
    }
}
