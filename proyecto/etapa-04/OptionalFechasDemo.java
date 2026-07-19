import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class OptionalFechasDemo {
    public static void main(String[] args) {
        RepositorioEnMemoria<Product> repositorio = new RepositorioEnMemoria<>();
        repositorio.agregar(new Product("Teclado mecanico", 25000.0, 15));
        repositorio.agregar(new Product("Mouse inalambrico", 8000.0, 40));

        Optional<Product> encontrado = repositorio.buscar(p -> p.getNombre().equals("Teclado mecanico"));
        if (encontrado.isPresent()) {
            System.out.println("Encontrado: " + encontrado.get().getNombre());
        }

        Optional<Product> noEncontrado = repositorio.buscar(p -> p.getNombre().equals("Monitor 4K"));
        String nombreOMensaje = noEncontrado.map(Product::getNombre).orElse("Producto no disponible");
        System.out.println(nombreOMensaje);

        noEncontrado.ifPresent(p -> System.out.println("Esto nunca se imprime"));

        Product teclado = encontrado.orElseThrow();
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        System.out.println("Fecha de ingreso: " + teclado.getFechaIngreso().format(formato));

        LocalDate fechaCompra = LocalDate.of(2025, 1, 15);
        LocalDate hoy = LocalDate.now();
        Period antiguedad = Period.between(fechaCompra, hoy);
        System.out.println("Antiguedad desde " + fechaCompra.format(formato) + ": "
                + antiguedad.getYears() + " anios, " + antiguedad.getMonths() + " meses, " + antiguedad.getDays() + " dias");
    }
}
