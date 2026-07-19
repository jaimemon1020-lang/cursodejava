public class CleanCodeAntes {
    public static void main(String[] args) {
        Product p = new Product("Teclado mecanico", 25000.0, 15);
        int c = 3;
        double t;
        if (c > 0 && c <= p.getStock()) {
            t = p.getPrecio() * c;
            if (t > 50000) {
                t = t * 0.9;
            }
            System.out.println("Total: " + t);
        } else {
            System.out.println("Cantidad invalida");
        }
    }
}
