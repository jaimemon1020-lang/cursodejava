public class Operadores {
    public static void main(String[] args) {
        int stock = 15;
        int vendidos = 4;
        int restante = stock - vendidos;

        double precio = 25000.0;
        int cantidad = 3;
        double total = precio * cantidad;

        boolean stockBajo = restante < 5;
        boolean hayStock = restante != 0;

        // Casting implicito: int -> double, sin perdida de datos
        double stockComoDecimal = restante;

        // Casting explicito: double -> int, se trunca (no redondea)
        double promedio = 7.9;
        int promedioTruncado = (int) promedio;

        var descripcion = "Stock restante: " + restante;

        System.out.println("Restante: " + restante);
        System.out.println("Total venta: " + total);
        System.out.println("Stock bajo: " + stockBajo);
        System.out.println("Hay stock: " + hayStock);
        System.out.println("Stock como decimal: " + stockComoDecimal);
        System.out.println("Promedio truncado: " + promedioTruncado);
        System.out.println(descripcion);
    }
}
