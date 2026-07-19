package com.curso.catalogo;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "catalogo")
public record CatalogoProperties(String nombreApp, double umbralDescuento) {
}
