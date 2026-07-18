import java.util.ArrayList;
import java.util.List;

public class Order {

    private Cliente cliente;
    private List<OrderItem> items;

    Order(Cliente cliente) {
        this.cliente = cliente;
        this.items = new ArrayList<>();
    }

    public void agregarItem(OrderItem item) {
        this.items.add(item);
    }

    public double calcularTotal() {
        double total = 0;
        for (OrderItem item : items) {
            total += item.calcularSubtotal();
        }
        return total;
    }

    public int cantidadDeItems() {
        return this.items.size();
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
