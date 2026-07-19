public class CleanCodeDespues {
    private static final double UMBRAL_DESCUENTO = 50000.0;
    private static final double PORCENTAJE_DESCUENTO = 0.9;

    public static void main(String[] args) {
        Product teclado = new Product("Teclado mecanico", 25000.0, 15);
        int cantidadAComprar = 3;

        if (!esCantidadValida(cantidadAComprar, teclado.getStock())) {
            System.out.println("Cantidad invalida");
            return;
        }

        double total = calcularTotalConDescuento(teclado.getPrecio(), cantidadAComprar);
        System.out.println("Total: " + total);
    }

    private static boolean esCantidadValida(int cantidad, int stockDisponible) {
        return cantidad > 0 && cantidad <= stockDisponible;
    }

    private static double calcularTotalConDescuento(double precioUnitario, int cantidad) {
        double subtotal = precioUnitario * cantidad;
        return aplicaDescuento(subtotal) ? subtotal * PORCENTAJE_DESCUENTO : subtotal;
    }

    private static boolean aplicaDescuento(double subtotal) {
        return subtotal > UMBRAL_DESCUENTO;
    }
}
