public class ProductDemo {
    public static void main(String[] args) {
        Product teclado = new Product("Teclado mecanico", 25000.0, 15);
        Product mouse = new Product("Mouse inalambrico", 8000.0, 40);

        teclado.describir();
        mouse.describir();

        System.out.println("Valor inventario teclado: " + teclado.calcularValorInventario());
        System.out.println("Valor inventario mouse: " + mouse.calcularValorInventario());

        teclado.stock = teclado.stock - 3;
        System.out.println("Stock teclado despues de vender 3: " + teclado.stock);
        System.out.println("Stock mouse (no deberia cambiar): " + mouse.stock);
    }
}
