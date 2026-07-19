package com.curso.catalogo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ProductTest {

    @Test
    void venderUnidadesReduceElStock() {
        Product teclado = new Product("Teclado mecanico", 25000.0, 15);

        teclado.venderUnidades(5);

        assertEquals(10, teclado.getStock());
    }

    @Test
    void venderMasDeLoDisponibleLanzaExcepcion() {
        Product teclado = new Product("Teclado mecanico", 25000.0, 15);

        assertThrows(StockInsuficienteException.class, () -> teclado.venderUnidades(100));
    }
}
