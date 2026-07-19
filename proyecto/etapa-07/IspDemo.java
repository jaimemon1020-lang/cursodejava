interface GestorDeVentasYReportes {
    void venderProducto(Product producto, int cantidad);
    void generarReporteMensual();
}

class CajaRegistradora implements GestorDeVentasYReportes {
    @Override
    public void venderProducto(Product producto, int cantidad) {
        producto.venderUnidades(cantidad);
        System.out.println("Vendido (interfaz gorda): " + cantidad + " x " + producto.getNombre());
    }

    @Override
    public void generarReporteMensual() {
        throw new UnsupportedOperationException("Esta caja no genera reportes");
    }
}

interface Vendedor {
    void venderProducto(Product producto, int cantidad);
}

class CajaRegistradoraSimple implements Vendedor {
    @Override
    public void venderProducto(Product producto, int cantidad) {
        producto.venderUnidades(cantidad);
        System.out.println("Vendido (interfaz segregada): " + cantidad + " x " + producto.getNombre());
    }
}

public class IspDemo {
    public static void main(String[] args) {
        Product mouse = new Product("Mouse inalambrico", 8000.0, 40);
        Product auriculares = new Product("Auriculares", 15000.0, 20);

        CajaRegistradora cajaGorda = new CajaRegistradora();
        cajaGorda.venderProducto(mouse, 5);
        try {
            cajaGorda.generarReporteMensual();
        } catch (UnsupportedOperationException e) {
            System.out.println("Error esperado: " + e.getMessage());
        }

        Vendedor cajaSimple = new CajaRegistradoraSimple();
        cajaSimple.venderProducto(auriculares, 3);
    }
}
