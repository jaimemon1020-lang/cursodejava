public class DatosProducto {
    public static void main(String[] args) {
        final double IVA = 0.21;

        String nombre = "Teclado mecanico";
        int stock = 15;
        double precio = 25000.0;
        boolean disponible = stock > 0;
        char categoria = 'P';

        double precioConIva = precio + (precio * IVA);

        System.out.println("Producto: " + nombre);
        System.out.println("Categoria: " + categoria);
        System.out.println("Stock: " + stock);
        System.out.println("Disponible: " + disponible);
        System.out.println("Precio sin IVA: " + precio);
        System.out.println("Precio con IVA: " + precioConIva);
    }
}
