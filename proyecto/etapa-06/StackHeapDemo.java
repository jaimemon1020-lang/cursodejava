public class StackHeapDemo {
    public static void main(String[] args) {
        int cantidad = 15;
        Product teclado = new Product("Teclado mecanico", 25000.0, cantidad);
        Product mismoTeclado = teclado;
        Product otroTeclado = new Product("Teclado mecanico", 25000.0, cantidad);

        System.out.println("teclado == mismoTeclado: " + (teclado == mismoTeclado));
        System.out.println("teclado == otroTeclado: " + (teclado == otroTeclado));
        System.out.println("Identity hash teclado: " + System.identityHashCode(teclado));
        System.out.println("Identity hash mismoTeclado: " + System.identityHashCode(mismoTeclado));
        System.out.println("Identity hash otroTeclado: " + System.identityHashCode(otroTeclado));

        mismoTeclado = null;
        System.out.println("mismoTeclado ya no apunta a nada, pero teclado sigue accesible: " + teclado.getNombre());
    }
}
