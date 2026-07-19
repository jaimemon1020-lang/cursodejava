import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class RepositorioEnMemoria<T> {

    private List<T> elementos = new ArrayList<>();

    public void agregar(T elemento) {
        this.elementos.add(elemento);
    }

    public List<T> listarTodos() {
        return this.elementos;
    }

    public int contarElementos() {
        return this.elementos.size();
    }

    public Optional<T> buscar(Predicate<T> criterio) {
        for (T elemento : this.elementos) {
            if (criterio.test(elemento)) {
                return Optional.of(elemento);
            }
        }
        return Optional.empty();
    }
}
