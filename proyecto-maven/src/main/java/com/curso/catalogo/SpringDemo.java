package com.curso.catalogo;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SpringDemo {
    public static void main(String[] args) {
        ApplicationContext contexto = new AnnotationConfigApplicationContext(AppConfig.class);

        RepositorioProductos repositorio = contexto.getBean(RepositorioProductos.class);
        repositorio.agregar(new Product("Teclado mecanico", 25000.0, 15));

        ServicioVentas servicio = contexto.getBean(ServicioVentas.class);
        servicio.venderProducto("Teclado mecanico", 5);

        ServicioVentas otraReferencia = contexto.getBean(ServicioVentas.class);
        System.out.println("Es el mismo bean (singleton): " + (servicio == otraReferencia));
    }
}
