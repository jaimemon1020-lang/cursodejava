package com.curso.catalogo;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ServicioVentasTest {

    @Mock
    private RepositorioProductos repositorio;

    @InjectMocks
    private ServicioVentas servicioVentas;

    @Test
    @DisplayName("Vender un producto existente reduce su stock")
    void venderProductoExistenteReduceElStock() {
        Product teclado = new Product("Teclado mecanico", 25000.0, 15);
        when(repositorio.buscar(any())).thenReturn(Optional.of(teclado));

        servicioVentas.venderProducto("Teclado mecanico", 5);

        assertEquals(10, teclado.getStock());
    }

    @Test
    @DisplayName("Vender un producto inexistente lanza ProductoNoEncontradoException")
    void venderProductoInexistenteLanzaExcepcion() {
        when(repositorio.buscar(any())).thenReturn(Optional.empty());

        assertThrows(ProductoNoEncontradoException.class, () -> servicioVentas.venderProducto("NoExiste", 1));
    }

    @Test
    @DisplayName("Vender un producto consulta el repositorio exactamente una vez")
    void venderProductoLlamaAlRepositorioUnaVez() {
        Product teclado = new Product("Teclado mecanico", 25000.0, 15);
        when(repositorio.buscar(any())).thenReturn(Optional.of(teclado));

        servicioVentas.venderProducto("Teclado mecanico", 5);

        verify(repositorio).buscar(any());
    }
}
