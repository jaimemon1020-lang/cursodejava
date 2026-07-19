public class DiDemo {
    public static void main(String[] args) {
        RepositorioEnMemoria<Product> repositorio = new RepositorioEnMemoria<>();
        repositorio.agregar(new Product("Teclado mecanico", 25000.0, 15));
        repositorio.agregar(new Product("Mouse inalambrico", 8000.0, 40));

        ServicioVentas servicio = new ServicioVentas(repositorio);
        servicio.venderProducto("Teclado mecanico", 5);
        System.out.println("Valor inventario: " + servicio.calcularValorInventario());

        RepositorioEnMemoria<Product> repositorioDePrueba = new RepositorioEnMemoria<>();
        repositorioDePrueba.agregar(new Product("Producto de prueba", 1000.0, 1));
        ServicioVentas servicioDePrueba = new ServicioVentas(repositorioDePrueba);
        servicioDePrueba.venderProducto("Producto de prueba", 1);
        System.out.println("Valor inventario de prueba: " + servicioDePrueba.calcularValorInventario());
    }
}
