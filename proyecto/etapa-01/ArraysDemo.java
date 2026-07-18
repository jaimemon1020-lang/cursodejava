public class ArraysDemo {
    public static void main(String[] args) {
        String[] nombresProductos = new String[3];
        nombresProductos[0] = "Teclado";
        nombresProductos[1] = "Mouse";
        nombresProductos[2] = "Monitor";

        int[] stockPorProducto = {15, 40, 8};

        for (int i = 0; i < nombresProductos.length; i++) {
            System.out.println(nombresProductos[i] + ": " + stockPorProducto[i] + " unidades");
        }

        double[][] matrizPreciosPorMes = {
            {1200.0, 1250.0, 1300.0},
            {450.0, 470.0, 460.0},
        };

        System.out.println("Precio teclado mes 1: " + matrizPreciosPorMes[0][0]);
        System.out.println("Precio mouse mes 3: " + matrizPreciosPorMes[1][2]);

        double totalStock = 0;
        for (int cantidad : stockPorProducto) {
            totalStock += cantidad;
        }
        System.out.println("Stock total: " + totalStock);
    }
}
