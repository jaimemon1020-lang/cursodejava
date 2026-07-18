public class PolimorfismoDemo {
    public static void main(String[] args) {
        double totalOriginal = 40000.0;

        Descuento[] descuentosDisponibles = {
            new DescuentoPorcentual(0.10),
            new DescuentoFijo(5000.0)
        };

        for (Descuento descuento : descuentosDisponibles) {
            double totalConDescuento = descuento.aplicar(totalOriginal);
            System.out.println("Total con descuento: " + totalConDescuento);
        }

        System.out.println("---");

        MetodoEnvio[] enviosDisponibles = {
            new EnvioEstandar(),
            new EnvioExpress()
        };

        for (MetodoEnvio envio : enviosDisponibles) {
            envio.informar(totalOriginal);
        }
    }
}
