public class Product {

    String nombre;
    double precio;
    int stock;

    Product(String nombre, double precio, int stock) {
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
    }

    double calcularValorInventario() {
        return this.precio * this.stock;
    }

    void describir() {
        System.out.println(this.nombre + " | precio: " + this.precio + " | stock: " + this.stock);
    }
}
