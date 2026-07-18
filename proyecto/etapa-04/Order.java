import java.util.ArrayList;
import java.util.List;

public class Order {

    private Cliente cliente;
    private List<OrderItem> items;
    private EstadoPedido estado;

    Order(Cliente cliente) {
        this.cliente = cliente;
        this.items = new ArrayList<>();
        this.estado = EstadoPedido.PENDIENTE;
    }

    public void agregarItem(OrderItem item) {
        if (this.estado != EstadoPedido.PENDIENTE) {
            throw new IllegalStateException("No se pueden agregar items a un pedido que no esta pendiente");
        }
        this.items.add(item);
    }

    public void confirmar() {
        if (this.estado != EstadoPedido.PENDIENTE) {
            throw new IllegalStateException("Solo un pedido pendiente puede confirmarse");
        }
        this.estado = EstadoPedido.CONFIRMADO;
    }

    public void cancelar() {
        if (this.estado == EstadoPedido.CONFIRMADO) {
            throw new IllegalStateException("Un pedido confirmado no puede cancelarse");
        }
        this.estado = EstadoPedido.CANCELADO;
    }

    public EstadoPedido getEstado() {
        return this.estado;
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
        System.out.println("Pedido de: " + this.cliente.getNombre() + " | Estado: " + this.estado);
        for (OrderItem item : items) {
            System.out.println("  - " + item.getNombreProducto() + " x" + item.getCantidad()
                + " = " + item.calcularSubtotal());
        }
        System.out.println("Total: " + this.calcularTotal());
    }
}
