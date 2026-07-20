package com.curso.catalogo.infraestructura.persistencia;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import org.springframework.stereotype.Repository;

import com.curso.catalogo.Product;
import com.curso.catalogo.RepositorioProductos;

@Repository
public class RepositorioProductosEnMemoria implements RepositorioProductos {

    private final List<Product> productos = new ArrayList<>();

    @Override
    public void agregar(Product producto) {
        this.productos.add(producto);
    }

    @Override
    public List<Product> listarTodos() {
        return this.productos;
    }

    @Override
    public Optional<Product> buscar(Predicate<Product> criterio) {
        return this.productos.stream().filter(criterio).findFirst();
    }
}
