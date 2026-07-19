import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ReduceSortedDemo {
    public static void main(String[] args) {
        RepositorioEnMemoria<Product> repositorio = new RepositorioEnMemoria<>();
        repositorio.agregar(new Product("Teclado mecanico", 25000.0, 15));
        repositorio.agregar(new Product("Mouse inalambrico", 8000.0, 40));
        repositorio.agregar(new Product("Monitor 27 pulgadas", 180000.0, 3));
        repositorio.agregar(new Product("Auriculares", 15000.0, 0));

        double valorTotal = repositorio.listarTodos().stream()
                .map(Product::getPrecio)
                .reduce(0.0, Double::sum);
        System.out.println("Valor total en precios: " + valorTotal);

        List<String> porPrecioAscendente = repositorio.listarTodos().stream()
                .sorted(Comparator.comparingDouble(Product::getPrecio))
                .map(Product::getNombre)
                .collect(Collectors.toList());
        System.out.println("Ascendente por precio: " + porPrecioAscendente);

        List<String> porPrecioDescendente = repositorio.listarTodos().stream()
                .sorted(Comparator.comparingDouble(Product::getPrecio).reversed())
                .map(Product::getNombre)
                .collect(Collectors.toList());
        System.out.println("Descendente por precio: " + porPrecioDescendente);

        List<String> porStockLuegoNombre = repositorio.listarTodos().stream()
                .sorted(Comparator.comparingInt(Product::getStock)
                        .thenComparing(Product::getNombre))
                .map(Product::getNombre)
                .collect(Collectors.toList());
        System.out.println("Por stock, luego nombre: " + porStockLuegoNombre);

        Product masCaro = repositorio.listarTodos().stream()
                .reduce((p1, p2) -> p1.getPrecio() >= p2.getPrecio() ? p1 : p2)
                .orElseThrow();
        System.out.println("Mas caro: " + masCaro.getNombre());
    }
}
