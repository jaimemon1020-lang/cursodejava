package com.curso.catalogo;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    private final RepositorioProductos repositorio;

    public ProductoController(RepositorioProductos repositorio) {
        this.repositorio = repositorio;
    }

    @GetMapping
    public List<Product> listar() {
        return repositorio.listarTodos();
    }

    @GetMapping("/{nombre}")
    public Product buscarPorNombre(@PathVariable String nombre) {
        return repositorio.buscar(p -> p.getNombre().equals(nombre))
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado: " + nombre));
    }
}
