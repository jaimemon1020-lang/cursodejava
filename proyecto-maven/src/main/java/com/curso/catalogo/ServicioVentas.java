package com.curso.catalogo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServicioVentas {

    private final RepositorioProductos repositorio;

    @Autowired
    public ServicioVentas(RepositorioProductos repositorio) {
        this.repositorio = repositorio;
    }

    public void venderProducto(String nombre, int cantidad) {
        Product producto = repositorio.buscar(p -> p.getNombre().equals(nombre))
                .orElseThrow(() -> new ProductoNoEncontradoException(nombre));
        producto.venderUnidades(cantidad);
        System.out.println("Vendido: " + cantidad + " x " + producto.getNombre());
    }
}
