package com.curso.catalogo;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Primary
@Component("descuentoPorcentual")
public class DescuentoPorcentual implements Descuento {

    private static final double PORCENTAJE = 0.10;

    @Override
    public double aplicar(double total) {
        return total - (total * PORCENTAJE);
    }
}
