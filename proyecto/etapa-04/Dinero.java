public record Dinero(double monto) {

    public Dinero {
        if (monto < 0) {
            throw new IllegalArgumentException("El monto no puede ser negativo");
        }
    }

    public Dinero sumar(Dinero otro) {
        return new Dinero(this.monto + otro.monto);
    }

    public Dinero aplicarDescuento(double porcentaje) {
        return new Dinero(this.monto - (this.monto * porcentaje));
    }
}
