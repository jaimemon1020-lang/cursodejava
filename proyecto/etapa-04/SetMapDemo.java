import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class SetMapDemo {
    public static void main(String[] args) {
        Set<String> categorias = new HashSet<>();
        categorias.add("Perifericos");
        categorias.add("Monitores");
        categorias.add("Perifericos");

        System.out.println("Cantidad de categorias: " + categorias.size());
        System.out.println("Contiene Perifericos: " + categorias.contains("Perifericos"));

        Map<String, Product> inventarioPorCodigo = new HashMap<>();
        inventarioPorCodigo.put("SKU-001", new Product("Teclado mecanico", 25000.0, 15));
        inventarioPorCodigo.put("SKU-002", new Product("Mouse inalambrico", 8000.0, 40));

        Product encontrado = inventarioPorCodigo.get("SKU-001");
        System.out.println("Producto SKU-001: " + encontrado.getNombre());

        System.out.println("Existe SKU-999: " + inventarioPorCodigo.containsKey("SKU-999"));

        Product noEncontrado = inventarioPorCodigo.get("SKU-999");
        System.out.println("Buscar SKU-999 (sin existir): " + noEncontrado);
    }
}
