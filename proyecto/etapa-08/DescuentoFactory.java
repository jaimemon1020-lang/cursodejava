public class DescuentoFactory {

    public static Descuento crearParaMontoTotal(double montoTotal) {
        if (montoTotal >= 100000.0) {
            return new DescuentoPorcentual(0.15);
        } else if (montoTotal >= 50000.0) {
            return new DescuentoPorcentual(0.10);
        } else {
            return new DescuentoFijo(0.0);
        }
    }
}
