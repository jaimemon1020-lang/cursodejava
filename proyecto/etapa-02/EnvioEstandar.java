public class EnvioEstandar extends MetodoEnvio {

    EnvioEstandar() {
        super("Envio estandar");
    }

    @Override
    public double calcularCosto(double totalCompra) {
        return totalCompra >= 50000.0 ? 0.0 : 1500.0;
    }
}
