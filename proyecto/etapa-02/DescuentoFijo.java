public class DescuentoFijo implements Descuento {

    private double monto;

    DescuentoFijo(double monto) {
        this.monto = monto;
    }

    @Override
    public double aplicar(double total) {
        double resultado = total - this.monto;
        return resultado < 0 ? 0 : resultado;
    }
}
