import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;

public class ComparableDemo {
    public static void main(String[] args) {
        List<Product> productos = new ArrayList<>();
        productos.add(new Product("Teclado mecanico", 25000.0, 15));
        productos.add(new Product("Mouse inalambrico", 8000.0, 40));
        productos.add(new Product("Monitor 27 pulgadas", 180000.0, 3));

        Collections.sort(productos);
        System.out.println("Orden natural (Comparable, por precio ascendente):");
        for (Product p : productos) {
            System.out.println("  " + p.getNombre() + ": " + p.getPrecio());
        }

        TreeSet<Product> ordenadoPorNaturaleza = new TreeSet<>(productos);
        System.out.println("Mas barato (TreeSet.first): " + ordenadoPorNaturaleza.first().getNombre());
        System.out.println("Mas caro (TreeSet.last): " + ordenadoPorNaturaleza.last().getNombre());

        productos.sort(Comparator.comparing(Product::getNombre));
        System.out.println("Orden alternativo (Comparator externo, por nombre):");
        for (Product p : productos) {
            System.out.println("  " + p.getNombre());
        }
    }
}
