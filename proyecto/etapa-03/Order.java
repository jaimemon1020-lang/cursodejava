public class Order {

    private Cliente cliente;
    private OrderItem[] items;

    Order(Cliente cliente, OrderItem[] items) {
        this.cliente = cliente;
        this.items = items;
    }

    public double calcularTotal() {
        double total = 0;
        for (OrderItem item : items) {
            total += item.calcularSubtotal();
        }
        return total;
    }

    public void describir() {
        System.out.println("Pedido de: " + this.cliente.getNombre());
        for (OrderItem item : items) {
            System.out.println("  - " + item.getNombreProducto() + " x" + item.getCantidad()
                + " = " + item.calcularSubtotal());
        }
        System.out.println("Total: " + this.calcularTotal());
    }
}
