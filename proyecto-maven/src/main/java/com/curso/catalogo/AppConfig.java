package com.curso.catalogo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(
        basePackages = "com.curso.catalogo",
        excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = Application.class))
public class AppConfig {

    @Bean
    public ProcesadorPago procesadorPago() {
        return new AdaptadorPagoExterno(new ProveedorPagoExterno());
    }
}
