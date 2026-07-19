package com.curso.catalogo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner demo(RepositorioProductos repositorio, ServicioVentas servicio) {
        return args -> {
            repositorio.agregar(new Product("Teclado mecanico", 25000.0, 15));
            servicio.venderProducto("Teclado mecanico", 5);
        };
    }
}
