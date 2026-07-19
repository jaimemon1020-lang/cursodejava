public class ServicioVentas {

    private final RepositorioEnMemoria<Product> repositorio;

    public ServicioVentas(RepositorioEnMemoria<Product> repositorio) {
        this.repositorio = repositorio;
    }

    public void venderProducto(String nombre, int cantidad) {
        Product producto = repositorio.buscar(p -> p.getNombre().equals(nombre))
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado: " + nombre));
        producto.venderUnidades(cantidad);
        System.out.println("Vendido: " + cantidad + " x " + producto.getNombre());
    }

    public double calcularValorInventario() {
        return repositorio.listarTodos().stream()
                .map(p -> p.getPrecio() * p.getStock())
                .reduce(0.0, Double::sum);
    }
}
