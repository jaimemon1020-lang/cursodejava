public class EnumDemo {
    public static void main(String[] args) {
        Cliente cliente = new Cliente("Juan Perez", "juan@ejemplo.com", "Av. Siempre Viva 123");
        Order pedido = new Order(cliente);

        Product teclado = new Product("Teclado mecanico", 25000.0, 15);
        pedido.agregarItem(new OrderItem(teclado, 1));

        System.out.println("Estado inicial: " + pedido.getEstado());

        pedido.confirmar();
        System.out.println("Estado despues de confirmar: " + pedido.getEstado());

        String mensaje = switch (pedido.getEstado()) {
            case PENDIENTE -> "Esperando confirmacion";
            case CONFIRMADO -> "Listo para preparar envio";
            case CANCELADO -> "El pedido fue cancelado";
        };
        System.out.println("Mensaje: " + mensaje);
    }
}
