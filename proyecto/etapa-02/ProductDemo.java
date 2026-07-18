public class ProductDemo {
    public static void main(String[] args) {
        Product teclado = new Product("Teclado mecanico", 25000.0, 15);

        teclado.describir();

        teclado.setPrecio(27000.0);
        System.out.println("Nuevo precio: " + teclado.getPrecio());

        teclado.venderUnidades(5);
        System.out.println("Stock despues de vender 5: " + teclado.getStock());

        System.out.println("Valor inventario: " + teclado.calcularValorInventario());
    }
}
