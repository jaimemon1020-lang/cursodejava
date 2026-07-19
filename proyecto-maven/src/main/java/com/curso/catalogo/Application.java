package com.curso.catalogo;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;

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

    @Bean
    public CommandLineRunner jdbcDemo(JdbcTemplate jdbcTemplate) {
        return args -> {
            jdbcTemplate.execute("CREATE TABLE personas_demo (id INT PRIMARY KEY, nombre VARCHAR(100))");
            jdbcTemplate.update("INSERT INTO personas_demo (id, nombre) VALUES (?, ?)", 1, "Ana");
            jdbcTemplate.update("INSERT INTO personas_demo (id, nombre) VALUES (?, ?)", 2, "Beto");

            List<String> nombres = jdbcTemplate.query(
                    "SELECT nombre FROM personas_demo ORDER BY id",
                    (rs, rowNum) -> rs.getString("nombre"));

            System.out.println("Nombres desde JdbcTemplate: " + nombres);
        };
    }

    @Bean
    public CommandLineRunner jpaDemo(ClienteRepository clienteRepository) {
        return args -> {
            Cliente guardado = clienteRepository.save(
                    new Cliente("Juan Perez", "juan@ejemplo.com", "Av. Siempre Viva 123, Cordoba"));
            System.out.println("Cliente guardado con id: " + guardado.getId());

            Optional<Cliente> encontrado = clienteRepository.findById(guardado.getId());
            encontrado.ifPresent(c -> System.out.println("Cliente encontrado: " + c.getNombre()));

            System.out.println("Total de clientes: " + clienteRepository.count());

            clienteRepository.save(new Cliente("Juan Gomez", "juan.gomez@ejemplo.com", "Calle Falsa 456, Rosario"));

            Optional<Cliente> porEmail = clienteRepository.findByEmail("juan@ejemplo.com");
            porEmail.ifPresent(c -> System.out.println("Encontrado por email: " + c.getNombre()));

            List<Cliente> porNombre = clienteRepository.findByNombreContaining("Juan");
            System.out.println("Clientes con 'Juan' en el nombre: " + porNombre.size());

            List<Cliente> porCiudad = clienteRepository.buscarPorCiudad("Cordoba");
            System.out.println("Clientes en Cordoba: " + porCiudad.size());
        };
    }
}
