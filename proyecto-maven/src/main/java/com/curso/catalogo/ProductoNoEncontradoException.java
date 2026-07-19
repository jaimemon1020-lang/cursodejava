package com.curso.catalogo;

public class ProductoNoEncontradoException extends RuntimeException {

    public ProductoNoEncontradoException(String nombre) {
        super("Producto no encontrado: " + nombre);
    }
}
