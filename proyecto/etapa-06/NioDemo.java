import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public class NioDemo {
    public static void main(String[] args) throws IOException {
        RepositorioEnMemoria<Product> repositorio = new RepositorioEnMemoria<>();
        repositorio.agregar(new Product("Teclado mecanico", 25000.0, 15));
        repositorio.agregar(new Product("Mouse inalambrico", 8000.0, 40));

        Path archivo = Path.of(System.getProperty("java.io.tmpdir"), "catalogo.txt");

        String contenido = repositorio.listarTodos().stream()
                .map(p -> p.getNombre() + ";" + p.getPrecio() + ";" + p.getStock())
                .collect(Collectors.joining(System.lineSeparator()));

        Files.writeString(archivo, contenido);
        System.out.println("Archivo existe: " + Files.exists(archivo));
        System.out.println("Tamano en bytes: " + Files.size(archivo));

        List<String> lineas = Files.readAllLines(archivo);
        System.out.println("Lineas leidas: " + lineas.size());
        for (String linea : lineas) {
            System.out.println("  " + linea);
        }

        Files.delete(archivo);
        System.out.println("Archivo existe despues de borrar: " + Files.exists(archivo));
    }
}
