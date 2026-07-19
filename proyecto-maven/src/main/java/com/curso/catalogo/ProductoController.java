package com.curso.catalogo;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    private final RepositorioProductos repositorio;
    private final ServicioVentas servicioVentas;

    public ProductoController(RepositorioProductos repositorio, ServicioVentas servicioVentas) {
        this.repositorio = repositorio;
        this.servicioVentas = servicioVentas;
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

    @PostMapping
    public Product crear(@RequestBody NuevoProductoRequest request) {
        Product producto = new Product(request.nombre(), request.precio(), request.stock());
        repositorio.agregar(producto);
        return producto;
    }

    @PostMapping("/{nombre}/vender")
    public Product vender(@PathVariable String nombre, @RequestParam int cantidad) {
        servicioVentas.venderProducto(nombre, cantidad);
        return repositorio.buscar(p -> p.getNombre().equals(nombre)).orElseThrow();
    }
}
