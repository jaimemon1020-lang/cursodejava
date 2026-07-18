import java.util.ArrayList;
import java.util.List;

public class ListDemo {
    public static void main(String[] args) {
        Cliente cliente = new Cliente("Juan Perez", "juan@ejemplo.com", "Av. Siempre Viva 123");
        Order pedido = new Order(cliente);

        Product teclado = new Product("Teclado mecanico", 25000.0, 15);
        Product mouse = new Product("Mouse inalambrico", 8000.0, 40);

        pedido.agregarItem(new OrderItem(teclado, 1));
        System.out.println("Items despues de agregar 1: " + pedido.cantidadDeItems());

        pedido.agregarItem(new OrderItem(mouse, 2));
        System.out.println("Items despues de agregar 2: " + pedido.cantidadDeItems());

        pedido.describir();

        List<String> nombresProductos = new ArrayList<>();
        nombresProductos.add("Teclado mecanico");
        nombresProductos.add("Mouse inalambrico");
        nombresProductos.add("Monitor");
        nombresProductos.remove("Mouse inalambrico");

        System.out.println("Productos restantes: " + nombresProductos);
        System.out.println("Contiene Monitor: " + nombresProductos.contains("Monitor"));
    }
}
