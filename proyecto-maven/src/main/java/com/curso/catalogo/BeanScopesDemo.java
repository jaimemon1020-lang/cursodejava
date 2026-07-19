package com.curso.catalogo;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class BeanScopesDemo {
    public static void main(String[] args) {
        ApplicationContext contexto = new AnnotationConfigApplicationContext(AppConfig.class);

        ServicioVentas servicioA = contexto.getBean(ServicioVentas.class);
        ServicioVentas servicioB = contexto.getBean(ServicioVentas.class);
        System.out.println("ServicioVentas (singleton) es el mismo: " + (servicioA == servicioB));

        SolicitudDeVenta solicitud1 = contexto.getBean(SolicitudDeVenta.class);
        SolicitudDeVenta solicitud2 = contexto.getBean(SolicitudDeVenta.class);
        System.out.println("SolicitudDeVenta (prototype) es el mismo: " + (solicitud1 == solicitud2));
        System.out.println("Numero solicitud1: " + solicitud1.getNumero());
        System.out.println("Numero solicitud2: " + solicitud2.getNumero());

        ProcesadorPago procesador = contexto.getBean(ProcesadorPago.class);
        System.out.println("Pago procesado: " + procesador.procesar(25000.0));
    }
}
