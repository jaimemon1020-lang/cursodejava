public class ModeladoDemo {
    public static void main(String[] args) {
        Cliente cliente = new Cliente("Juan Perez", "juan@ejemplo.com", "Av. Siempre Viva 123");

        Product teclado = new Product("Teclado mecanico", 25000.0, 15);
        Product mouse = new Product("Mouse inalambrico", 8000.0, 40);

        OrderItem[] items = {
            new OrderItem(teclado, 1),
            new OrderItem(mouse, 2)
        };

        Order pedido = new Order(cliente, items);
        pedido.describir();
    }
}
