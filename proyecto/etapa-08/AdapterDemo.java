public class AdapterDemo {
    public static void main(String[] args) {
        ProcesadorPago procesador = new AdaptadorPagoExterno(new ProveedorPagoExterno());

        System.out.println("Pago de 25000.0: " + procesador.procesar(25000.0));
        System.out.println("Pago de -100.0: " + procesador.procesar(-100.0));
    }
}
