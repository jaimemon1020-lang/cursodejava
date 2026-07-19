import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CollectorsAvanzadoDemo {
    public static void main(String[] args) {
        RepositorioEnMemoria<Product> repositorio = new RepositorioEnMemoria<>();
        repositorio.agregar(new Product("Teclado mecanico", 25000.0, 15));
        repositorio.agregar(new Product("Mouse inalambrico", 8000.0, 40));
        repositorio.agregar(new Product("Monitor 27 pulgadas", 180000.0, 3));
        repositorio.agregar(new Product("Auriculares", 15000.0, 0));

        Map<Boolean, List<Product>> porDisponibilidad = repositorio.listarTodos().stream()
                .collect(Collectors.partitioningBy(p -> p.getStock() > 0));
        System.out.println("Con stock: " + porDisponibilidad.get(true).size());
        System.out.println("Sin stock: " + porDisponibilidad.get(false).size());

        Function<Product, String> categoria = p -> p.getPrecio() > 50000.0 ? "Premium" : "Estandar";

        Map<String, List<Product>> porCategoria = repositorio.listarTodos().stream()
                .collect(Collectors.groupingBy(categoria));
        System.out.println("Premium: " + porCategoria.get("Premium").size());
        System.out.println("Estandar: " + porCategoria.get("Estandar").size());

        Map<String, Long> conteoPorCategoria = repositorio.listarTodos().stream()
                .collect(Collectors.groupingBy(categoria, Collectors.counting()));
        System.out.println("Conteo por categoria: " + conteoPorCategoria);

        Map<Boolean, Double> valorPorDisponibilidad = repositorio.listarTodos().stream()
                .collect(Collectors.partitioningBy(p -> p.getStock() > 0,
                        Collectors.summingDouble(Product::getPrecio)));
        System.out.println("Valor con stock: " + valorPorDisponibilidad.get(true));
        System.out.println("Valor sin stock: " + valorPorDisponibilidad.get(false));
    }
}
