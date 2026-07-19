import java.util.HashSet;
import java.util.Set;

public class EqualsHashCodeDemo {
    public static void main(String[] args) {
        Product productoA = new Product("Teclado mecanico", 25000.0, 15);
        Product productoB = new Product("Teclado mecanico", 25000.0, 15);

        System.out.println("productoA == productoB: " + (productoA == productoB));
        System.out.println("productoA.equals(productoB): " + productoA.equals(productoB));
        System.out.println("Mismo hashCode: " + (productoA.hashCode() == productoB.hashCode()));
        System.out.println("toString: " + productoA);

        Set<Product> catalogo = new HashSet<>();
        catalogo.add(productoA);
        catalogo.add(productoB);
        System.out.println("Tamano del Set (A y B son 'iguales' en contenido): " + catalogo.size());

        Product productoDistinto = new Product("Mouse inalambrico", 8000.0, 40);
        catalogo.add(productoDistinto);
        System.out.println("Tamano del Set con producto distinto agregado: " + catalogo.size());
    }
}
