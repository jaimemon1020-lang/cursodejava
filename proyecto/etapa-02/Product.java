import java.time.LocalDate;
import java.util.Objects;

public class Product implements Comparable<Product> {

    private String nombre;
    private double precio;
    private int stock;
    private LocalDate fechaIngreso;

    Product(String nombre, double precio, int stock) {
        if (precio < 0) {
            throw new IllegalArgumentException("El precio no puede ser negativo");
        }
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
        this.fechaIngreso = LocalDate.now();
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

    public LocalDate getFechaIngreso() {
        return this.fechaIngreso;
    }

    public void venderUnidades(int cantidad) {
        if (cantidad > this.stock) {
            throw new StockInsuficienteException(this.stock, cantidad);
        }
        this.stock = this.stock - cantidad;
    }

    double calcularValorInventario() {
        return this.precio * this.stock;
    }

    void describir() {
        System.out.println(this.nombre + " | precio: " + this.precio + " | stock: " + this.stock);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Product)) {
            return false;
        }
        Product otro = (Product) obj;
        return Double.compare(this.precio, otro.precio) == 0
                && this.stock == otro.stock
                && Objects.equals(this.nombre, otro.nombre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.nombre, this.precio, this.stock);
    }

    @Override
    public String toString() {
        return "Product{nombre='" + this.nombre + "', precio=" + this.precio + ", stock=" + this.stock + "}";
    }

    @Override
    public int compareTo(Product otro) {
        return Double.compare(this.precio, otro.precio);
    }
}
