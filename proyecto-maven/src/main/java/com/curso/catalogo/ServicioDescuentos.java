package com.curso.catalogo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class ServicioDescuentos {

    private final Descuento descuentoPorDefecto;
    private final Descuento descuentoFijo;

    @Autowired
    public ServicioDescuentos(Descuento descuentoPorDefecto,
                               @Qualifier("descuentoFijo") Descuento descuentoFijo) {
        this.descuentoPorDefecto = descuentoPorDefecto;
        this.descuentoFijo = descuentoFijo;
    }

    public double aplicarPorDefecto(double total) {
        return descuentoPorDefecto.aplicar(total);
    }

    public double aplicarFijo(double total) {
        return descuentoFijo.aplicar(total);
    }
}
