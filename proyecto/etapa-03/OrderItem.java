public class OrderItem {

    private Product producto;
    private int cantidad;

    OrderItem(Product producto, int cantidad) {
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor a cero");
        }
        this.producto = producto;
        this.cantidad = cantidad;
    }

    public double calcularSubtotal() {
        return this.producto.getPrecio() * this.cantidad;
    }

    public String getNombreProducto() {
        return this.producto.getNombre();
    }

    public int getCantidad() {
        return this.cantidad;
    }
}
