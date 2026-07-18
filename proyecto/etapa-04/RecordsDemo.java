public class RecordsDemo {
    public static void main(String[] args) {
        Dinero precioTeclado = new Dinero(25000.0);
        Dinero precioMouse = new Dinero(8000.0);

        Dinero total = precioTeclado.sumar(precioMouse);
        System.out.println("Total: " + total);
        System.out.println("Monto accesible: " + total.monto());

        Dinero totalConDescuento = total.aplicarDescuento(0.10);
        System.out.println("Total con descuento: " + totalConDescuento);

        Dinero otroPrecioTeclado = new Dinero(25000.0);
        System.out.println("precioTeclado equals otroPrecioTeclado: " + precioTeclado.equals(otroPrecioTeclado));
        System.out.println("precioTeclado == otroPrecioTeclado: " + (precioTeclado == otroPrecioTeclado));
    }
}
