public class ExceptionsDemo {
    public static void main(String[] args) {
        Product teclado = new Product("Teclado mecanico", 25000.0, 15);

        try {
            teclado.venderUnidades(100);
            System.out.println("Venta realizada");
        } catch (StockInsuficienteException e) {
            System.out.println("No se pudo vender: " + e.getMessage());
            System.out.println("Stock disponible era: " + e.getStockDisponible());
        } finally {
            System.out.println("Intento de venta finalizado (esto se ejecuta siempre)");
        }

        System.out.println("Stock actual: " + teclado.getStock());

        try (RegistroDeOperaciones registro = new RegistroDeOperaciones()) {
            registro.registrar("Venta procesada para " + teclado.getNombre());
        }

        System.out.println("Programa continua normalmente");
    }
}
