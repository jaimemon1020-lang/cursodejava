public class FactoryStrategyDemo {
    public static void main(String[] args) {
        double[] montos = {30000.0, 60000.0, 150000.0};

        for (double monto : montos) {
            Descuento descuento = DescuentoFactory.crearParaMontoTotal(monto);
            double total = descuento.aplicar(monto);
            System.out.println("Monto " + monto + " -> " + descuento.getClass().getSimpleName() + " -> total: " + total);
        }
    }
}
