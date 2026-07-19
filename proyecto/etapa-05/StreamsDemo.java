import java.util.List;
import java.util.stream.Collectors;

public class StreamsDemo {
    public static void main(String[] args) {
        RepositorioEnMemoria<Product> repositorio = new RepositorioEnMemoria<>();
        repositorio.agregar(new Product("Teclado mecanico", 25000.0, 15));
        repositorio.agregar(new Product("Mouse inalambrico", 8000.0, 40));
        repositorio.agregar(new Product("Monitor 27 pulgadas", 180000.0, 3));
        repositorio.agregar(new Product("Auriculares", 15000.0, 0));

        List<Product> conStockBajo = repositorio.listarTodos().stream()
                .filter(p -> p.getStock() > 0 && p.getStock() < 20)
                .collect(Collectors.toList());
        System.out.println("Con stock bajo: " + conStockBajo.size());

        List<String> nombres = repositorio.listarTodos().stream()
                .map(Product::getNombre)
                .collect(Collectors.toList());
        System.out.println("Nombres: " + nombres);

        long sinStock = repositorio.listarTodos().stream()
                .filter(p -> p.getStock() == 0)
                .count();
        System.out.println("Sin stock: " + sinStock);

        repositorio.listarTodos().stream()
                .filter(p -> p.getPrecio() > 10000.0)
                .forEach(p -> System.out.println("Caro: " + p.getNombre()));
    }
}
