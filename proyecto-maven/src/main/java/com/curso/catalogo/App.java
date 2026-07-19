package com.curso.catalogo;

public class App {
    public static void main(String[] args) {
        Product teclado = new Product("Teclado mecanico", 25000.0, 15);
        teclado.describir();

        teclado.venderUnidades(5);
        System.out.println("Stock restante: " + teclado.getStock());
        System.out.println("Valor de inventario: " + teclado.calcularValorInventario());
    }
}
