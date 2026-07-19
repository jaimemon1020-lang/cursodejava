import java.util.List;

public class SolidDemo {
    public static void main(String[] args) {
        Product teclado = new Product("Teclado mecanico", 25000.0, 15);

        List<Descuento> descuentosDisponibles = List.of(
                new DescuentoFijo(2000.0),
                new DescuentoPorcentual(0.10)
        );

        for (Descuento descuento : descuentosDisponibles) {
            System.out.println("Total con " + descuento.getClass().getSimpleName() + ": "
                    + aplicarDescuento(teclado, descuento));
        }

        List<MetodoEnvio> metodosDisponibles = List.of(new EnvioEstandar(), new EnvioExpress());
        for (MetodoEnvio metodo : metodosDisponibles) {
            metodo.informar(teclado.getPrecio());
        }
    }

    private static double aplicarDescuento(Product producto, Descuento descuento) {
        return descuento.aplicar(producto.getPrecio());
    }
}
