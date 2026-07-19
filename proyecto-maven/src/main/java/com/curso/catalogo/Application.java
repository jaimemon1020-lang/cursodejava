package com.curso.catalogo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@ConfigurationPropertiesScan
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

    @Bean
    public CommandLineRunner configDemo(CatalogoProperties propiedades,
                                         @Value("${catalogo.nombre-app}") String nombreAppPorValue) {
        return args -> {
            System.out.println("Nombre de la app (ConfigurationProperties): " + propiedades.nombreApp());
            System.out.println("Umbral de descuento (ConfigurationProperties): " + propiedades.umbralDescuento());
            System.out.println("Nombre de la app (Value): " + nombreAppPorValue);
        };
    }
}
