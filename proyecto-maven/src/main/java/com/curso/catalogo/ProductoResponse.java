package com.curso.catalogo;

public record ProductoResponse(String nombre, double precio, int stock) {

    public static ProductoResponse desde(Product producto) {
        return new ProductoResponse(producto.getNombre(), producto.getPrecio(), producto.getStock());
    }
}
