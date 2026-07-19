public class ThreadsDemo {
    public static void main(String[] args) throws InterruptedException {
        Runnable tarea = () -> {
            System.out.println("Ejecutando en: " + Thread.currentThread().getName());
            for (int i = 1; i <= 3; i++) {
                System.out.println("Paso " + i);
            }
        };

        Thread hilo = new Thread(tarea, "hilo-inventario");
        System.out.println("Antes de start, hilo principal: " + Thread.currentThread().getName());
        hilo.start();
        hilo.join();
        System.out.println("Despues de join: el hilo termino, el main continua");
    }
}
