import java.util.List;

public class GenericsDemo {

    static <T> T primerElemento(List<T> lista) {
        return lista.get(0);
    }

    public static void main(String[] args) {
        RepositorioEnMemoria<Product> repositorioProductos = new RepositorioEnMemoria<>();
        repositorioProductos.agregar(new Product("Teclado mecanico", 25000.0, 15));
        repositorioProductos.agregar(new Product("Mouse inalambrico", 8000.0, 40));

        System.out.println("Productos guardados: " + repositorioProductos.contarElementos());

        RepositorioEnMemoria<Cliente> repositorioClientes = new RepositorioEnMemoria<>();
        repositorioClientes.agregar(new Cliente("Juan Perez", "juan@ejemplo.com", "Av. Siempre Viva 123"));

        System.out.println("Clientes guardados: " + repositorioClientes.contarElementos());

        Product primerProducto = primerElemento(repositorioProductos.listarTodos());
        System.out.println("Primer producto: " + primerProducto.getNombre());

        Cliente primerCliente = primerElemento(repositorioClientes.listarTodos());
        System.out.println("Primer cliente: " + primerCliente.getNombre());
    }
}
