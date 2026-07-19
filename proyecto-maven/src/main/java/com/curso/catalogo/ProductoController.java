package com.curso.catalogo;

import java.util.List;
import java.util.stream.Collectors;

import jakarta.validation.Valid;

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
    private final ProductoMapper mapper;

    public ProductoController(RepositorioProductos repositorio, ServicioVentas servicioVentas, ProductoMapper mapper) {
        this.repositorio = repositorio;
        this.servicioVentas = servicioVentas;
        this.mapper = mapper;
    }

    @GetMapping
    public List<ProductoResponse> listar() {
        return repositorio.listarTodos().stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @GetMapping("/{nombre}")
    public ProductoResponse buscarPorNombre(@PathVariable String nombre) {
        return mapper.toResponse(buscarOFallar(nombre));
    }

    @PostMapping
    public ProductoResponse crear(@Valid @RequestBody NuevoProductoRequest request) {
        Product producto = mapper.toEntity(request);
        repositorio.agregar(producto);
        return mapper.toResponse(producto);
    }

    @PostMapping("/{nombre}/vender")
    public ProductoResponse vender(@PathVariable String nombre, @RequestParam int cantidad) {
        servicioVentas.venderProducto(nombre, cantidad);
        return mapper.toResponse(buscarOFallar(nombre));
    }

    private Product buscarOFallar(String nombre) {
        return repositorio.buscar(p -> p.getNombre().equals(nombre))
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado: " + nombre));
    }
}
