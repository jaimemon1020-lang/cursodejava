package com.curso.catalogo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public record NuevoProductoRequest(
        @NotBlank(message = "El nombre no puede estar vacio") String nombre,
        @Positive(message = "El precio debe ser mayor a cero") double precio,
        @PositiveOrZero(message = "El stock no puede ser negativo") int stock) {
}
