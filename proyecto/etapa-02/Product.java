public class Product {

    private String nombre;
    private double precio;
    private int stock;

    Product(String nombre, double precio, int stock) {
        if (precio < 0) {
            throw new IllegalArgumentException("El precio no puede ser negativo");
        }
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
    }

    public String getNombre() {
        return this.nombre;
    }

    public double getPrecio() {
        return this.precio;
    }

    public void setPrecio(double nuevoPrecio) {
        if (nuevoPrecio < 0) {
            throw new IllegalArgumentException("El precio no puede ser negativo");
        }
        this.precio = nuevoPrecio;
    }

    public int getStock() {
        return this.stock;
    }

    public void venderUnidades(int cantidad) {
        if (cantidad > this.stock) {
            throw new IllegalStateException("No hay stock suficiente");
        }
        this.stock = this.stock - cantidad;
    }

    double calcularValorInventario() {
        return this.precio * this.stock;
    }

    void describir() {
        System.out.println(this.nombre + " | precio: " + this.precio + " | stock: " + this.stock);
    }
}
