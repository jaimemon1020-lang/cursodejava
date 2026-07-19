package com.curso.catalogo;

public class StockInsuficienteException extends RuntimeException {

    private int stockDisponible;
    private int cantidadSolicitada;

    public StockInsuficienteException(int stockDisponible, int cantidadSolicitada) {
        super("Stock insuficiente: disponible " + stockDisponible + ", solicitado " + cantidadSolicitada);
        this.stockDisponible = stockDisponible;
        this.cantidadSolicitada = cantidadSolicitada;
    }

    public int getStockDisponible() {
        return this.stockDisponible;
    }

    public int getCantidadSolicitada() {
        return this.cantidadSolicitada;
    }
}
