public class Bucles {
    public static void main(String[] args) {
        int[] preciosDelDia = {1200, 3400, 0, 5600, 2100};

        double totalVentas = 0;
        for (int i = 0; i < preciosDelDia.length; i++) {
            totalVentas += preciosDelDia[i];
        }
        System.out.println("Total ventas (for): " + totalVentas);

        int cantidadValidas = 0;
        for (int precio : preciosDelDia) {
            if (precio == 0) {
                continue;
            }
            cantidadValidas++;
        }
        System.out.println("Ventas validas (for-each + continue): " + cantidadValidas);

        int stock = 10;
        int pedidosProcesados = 0;
        while (stock > 0) {
            stock = stock - 3;
            pedidosProcesados++;
        }
        System.out.println("Pedidos procesados (while): " + pedidosProcesados);
        System.out.println("Stock final: " + stock);

        int intentos = 0;
        do {
            intentos++;
        } while (intentos < 1);
        System.out.println("Intentos (do-while, corre al menos una vez): " + intentos);

        for (int i = 0; i < preciosDelDia.length; i++) {
            if (preciosDelDia[i] > 5000) {
                System.out.println("Primer precio alto encontrado: " + preciosDelDia[i]);
                break;
            }
        }
    }
}
