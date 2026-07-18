public class Order {

    private Cliente cliente;
    private Product producto;
    private int cantidad;

    Order(Cliente cliente, Product producto, int cantidad) {
        this.cliente = cliente;
        this.producto = producto;
        this.cantidad = cantidad;
    }

    public double calcularTotal() {
        return this.producto.getPrecio() * this.cantidad;
    }

    public void describir() {
        System.out.println("Pedido de: " + this.cliente.getNombre());
        System.out.println("Producto: " + this.producto.getNombre() + " x" + this.cantidad);
        System.out.println("Total: " + this.calcularTotal());
    }
}
