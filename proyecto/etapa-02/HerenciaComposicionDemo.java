public class HerenciaComposicionDemo {
    public static void main(String[] args) {
        Persona personaGenerica = new Persona("Ana Generica", "ana@ejemplo.com");
        personaGenerica.describir();

        System.out.println("---");

        Cliente cliente = new Cliente("Juan Perez", "juan@ejemplo.com", "Av. Siempre Viva 123");
        cliente.describir();

        System.out.println("---");

        Product teclado = new Product("Teclado mecanico", 25000.0, 15);
        Order pedido = new Order(cliente, teclado, 2);
        pedido.describir();
    }
}
