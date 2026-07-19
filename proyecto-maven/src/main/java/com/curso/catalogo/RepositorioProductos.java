package com.curso.catalogo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import org.springframework.stereotype.Repository;

@Repository
public class RepositorioProductos {

    private final List<Product> productos = new ArrayList<>();

    public void agregar(Product producto) {
        this.productos.add(producto);
    }

    public List<Product> listarTodos() {
        return this.productos;
    }

    public Optional<Product> buscar(Predicate<Product> criterio) {
        return this.productos.stream().filter(criterio).findFirst();
    }
}
