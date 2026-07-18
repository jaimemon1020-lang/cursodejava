public class CancelacionInvalidaDemo {
    public static void main(String[] args) {
        Cliente cliente = new Cliente("Juan Perez", "juan@ejemplo.com", "Av. Siempre Viva 123");
        Order pedido = new Order(cliente);
        pedido.agregarItem(new OrderItem(new Product("Teclado mecanico", 25000.0, 15), 1));
        pedido.confirmar();
        pedido.cancelar();
    }
}
