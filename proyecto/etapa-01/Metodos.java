public class Metodos {

    static double calcularTotal(double precio, int cantidad) {
        return precio * cantidad;
    }

    static double calcularTotal(double precio, int cantidad, double descuento) {
        double total = precio * cantidad;
        return total - (total * descuento);
    }

    static boolean hayStockSuficiente(int stockActual, int cantidadPedida) {
        return stockActual >= cantidadPedida;
    }

    public static void main(String[] args) {
        double totalSinDescuento = calcularTotal(1200.0, 5);
        System.out.println("Total sin descuento: " + totalSinDescuento);

        double totalConDescuento = calcularTotal(1200.0, 5, 0.10);
        System.out.println("Total con descuento: " + totalConDescuento);

        boolean alcanza = hayStockSuficiente(10, 4);
        System.out.println("Hay stock suficiente: " + alcanza);
    }
}
