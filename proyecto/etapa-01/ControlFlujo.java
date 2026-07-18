public class ControlFlujo {
    public static void main(String[] args) {
        double totalCompra = 45000.0;

        if (totalCompra >= 50000.0) {
            System.out.println("Envio gratis");
        } else if (totalCompra >= 20000.0) {
            System.out.println("Envio con 50% de descuento");
        } else {
            System.out.println("Envio con costo completo");
        }

        char categoria = 'P';
        String nombreCategoria;
        switch (categoria) {
            case 'P':
                nombreCategoria = "Periféricos";
                break;
            case 'M':
                nombreCategoria = "Monitores";
                break;
            case 'A':
                nombreCategoria = "Accesorios";
                break;
            default:
                nombreCategoria = "Sin categoria";
        }
        System.out.println("Categoria: " + nombreCategoria);

        int diaSemana = 3;
        String nombreDia = switch (diaSemana) {
            case 1 -> "Lunes";
            case 2 -> "Martes";
            case 3 -> "Miercoles";
            case 4 -> "Jueves";
            case 5 -> "Viernes";
            default -> "Fin de semana";
        };
        System.out.println("Dia: " + nombreDia);
    }
}
