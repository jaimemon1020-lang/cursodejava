package com.curso.catalogo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.curso.catalogo")
public class AppConfig {

    @Bean
    public ProcesadorPago procesadorPago() {
        return new AdaptadorPagoExterno(new ProveedorPagoExterno());
    }
}
