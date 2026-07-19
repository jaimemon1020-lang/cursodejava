import java.util.concurrent.atomic.AtomicInteger;

public class ContadorConcurrenteDemo {
    public static void main(String[] args) throws InterruptedException {
        AtomicInteger contador = new AtomicInteger(0);
        Runnable incrementar = () -> {
            for (int i = 0; i < 1000; i++) {
                contador.incrementAndGet();
            }
        };

        Thread hilo1 = new Thread(incrementar);
        Thread hilo2 = new Thread(incrementar);
        hilo1.start();
        hilo2.start();
        hilo1.join();
        hilo2.join();

        System.out.println("Contador final (2 hilos x 1000 incrementos): " + contador.get());
    }
}
