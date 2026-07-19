package com.curso.catalogo;

import org.springframework.stereotype.Component;

@Component
public class ProductoMapper {

    public ProductoResponse toResponse(Product producto) {
        return new ProductoResponse(producto.getNombre(), producto.getPrecio(), producto.getStock());
    }

    public Product toEntity(NuevoProductoRequest request) {
        return new Product(request.nombre(), request.precio(), request.stock());
    }
}
