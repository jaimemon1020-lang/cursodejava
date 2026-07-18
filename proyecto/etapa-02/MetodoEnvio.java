public abstract class MetodoEnvio {

    protected String nombre;

    MetodoEnvio(String nombre) {
        this.nombre = nombre;
    }

    public abstract double calcularCosto(double totalCompra);

    public void informar(double totalCompra) {
        System.out.println(this.nombre + ": $" + this.calcularCosto(totalCompra));
    }
}
