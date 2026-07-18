import java.util.ArrayList;
import java.util.List;

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
}
