public class ProveedorPagoExterno {

    public String cobrar(String montoEnTexto) {
        double monto = Double.parseDouble(montoEnTexto);
        return monto > 0 ? "OK" : "ERROR";
    }
}
