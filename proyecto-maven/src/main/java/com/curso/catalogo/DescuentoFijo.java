package com.curso.catalogo;

import org.springframework.stereotype.Component;

@Component("descuentoFijo")
public class DescuentoFijo implements Descuento {

    private static final double MONTO = 2000.0;

    @Override
    public double aplicar(double total) {
        double resultado = total - MONTO;
        return resultado < 0 ? 0 : resultado;
    }
}
