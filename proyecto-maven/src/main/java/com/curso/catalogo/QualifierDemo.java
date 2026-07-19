package com.curso.catalogo;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class QualifierDemo {
    public static void main(String[] args) {
        ApplicationContext contexto = new AnnotationConfigApplicationContext(AppConfig.class);

        ServicioDescuentos servicio = contexto.getBean(ServicioDescuentos.class);
        System.out.println("Con descuento por defecto (Primary): " + servicio.aplicarPorDefecto(25000.0));
        System.out.println("Con descuento fijo (Qualifier): " + servicio.aplicarFijo(25000.0));

        Descuento resuelto = contexto.getBean(Descuento.class);
        System.out.println("Bean resuelto sin qualifier es: " + resuelto.getClass().getSimpleName());
    }
}
