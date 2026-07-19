package com.curso.catalogo;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class SolicitudDeVenta {

    private static int contadorDeInstancias = 0;

    private final int numero;

    public SolicitudDeVenta() {
        contadorDeInstancias++;
        this.numero = contadorDeInstancias;
    }

    public int getNumero() {
        return this.numero;
    }
}
