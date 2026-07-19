package com.curso.catalogo;

import java.util.List;
import java.util.stream.Collectors;

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
    public List<ProductoResponse> listar() {
        return repositorio.listarTodos().stream()
                .map(ProductoResponse::desde)
                .collect(Collectors.toList());
    }

    @GetMapping("/{nombre}")
    public ProductoResponse buscarPorNombre(@PathVariable String nombre) {
        Product producto = buscarOFallar(nombre);
        return ProductoResponse.desde(producto);
    }

    @PostMapping
    public ProductoResponse crear(@RequestBody NuevoProductoRequest request) {
        Product producto = new Product(request.nombre(), request.precio(), request.stock());
        repositorio.agregar(producto);
        return ProductoResponse.desde(producto);
    }

    @PostMapping("/{nombre}/vender")
    public ProductoResponse vender(@PathVariable String nombre, @RequestParam int cantidad) {
        servicioVentas.venderProducto(nombre, cantidad);
        return ProductoResponse.desde(buscarOFallar(nombre));
    }

    private Product buscarOFallar(String nombre) {
        return repositorio.buscar(p -> p.getNombre().equals(nombre))
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado: " + nombre));
    }
}
