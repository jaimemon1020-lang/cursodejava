import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class LambdasDemo {
    public static void main(String[] args) {
        Product teclado = new Product("Teclado mecanico", 25000.0, 15);

        Predicate<Product> tieneStockBajo = p -> p.getStock() < 20;
        System.out.println("Stock bajo: " + tieneStockBajo.test(teclado));

        Function<Product, String> resumen = p -> p.getNombre() + " ($" + p.getPrecio() + ")";
        System.out.println(resumen.apply(teclado));

        Consumer<Product> imprimir = p -> System.out.println("Producto: " + p.getNombre());
        imprimir.accept(teclado);

        Supplier<Product> productoPorDefecto = () -> new Product("Producto generico", 0.0, 0);
        Product generico = productoPorDefecto.get();
        System.out.println("Generico: " + generico.getNombre());

        CalculadoraDescuento diezPorCiento = p -> p.getPrecio() * 0.9;
        System.out.println("Precio con descuento: " + diezPorCiento.calcular(teclado));
    }
}
