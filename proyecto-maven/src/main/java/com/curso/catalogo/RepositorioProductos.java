package com.curso.catalogo;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public interface RepositorioProductos {

    void agregar(Product producto);

    List<Product> listarTodos();

    Optional<Product> buscar(Predicate<Product> criterio);
}
