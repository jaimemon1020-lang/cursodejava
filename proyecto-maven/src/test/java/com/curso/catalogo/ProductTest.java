package com.curso.catalogo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ProductTest {

    private Product teclado;

    @BeforeEach
    void configurar() {
        teclado = new Product("Teclado mecanico", 25000.0, 15);
    }

    @Test
    @DisplayName("Vender unidades reduce el stock")
    void venderUnidadesReduceElStock() {
        teclado.venderUnidades(5);

        assertEquals(10, teclado.getStock());
    }

    @Test
    @DisplayName("Vender mas de lo disponible lanza StockInsuficienteException")
    void venderMasDeLoDisponibleLanzaExcepcion() {
        assertThrows(StockInsuficienteException.class, () -> teclado.venderUnidades(100));
    }

    @ParameterizedTest(name = "vender {0} unidades deja stock en {1}")
    @CsvSource({
            "1, 14",
            "5, 10",
            "15, 0"
    })
    @DisplayName("Vender distintas cantidades reduce el stock correctamente")
    void venderDistintasCantidadesReduceElStockCorrectamente(int cantidad, int stockEsperado) {
        teclado.venderUnidades(cantidad);

        assertEquals(stockEsperado, teclado.getStock());
    }

    @ParameterizedTest
    @ValueSource(doubles = {-1.0, -100.0, -0.01})
    @DisplayName("Precio negativo lanza excepcion al crear el producto")
    void precioNegativoLanzaExcepcionAlCrear(double precioInvalido) {
        assertThrows(IllegalArgumentException.class, () -> new Product("Producto invalido", precioInvalido, 10));
    }
}
