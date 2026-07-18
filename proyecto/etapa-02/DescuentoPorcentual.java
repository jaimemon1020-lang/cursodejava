public class DescuentoPorcentual implements Descuento {

    private double porcentaje;

    DescuentoPorcentual(double porcentaje) {
        this.porcentaje = porcentaje;
    }

    @Override
    public double aplicar(double total) {
        return total - (total * this.porcentaje);
    }
}
