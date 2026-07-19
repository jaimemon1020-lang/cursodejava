package com.curso.catalogo;

public class AdaptadorPagoExterno implements ProcesadorPago {

    private final ProveedorPagoExterno proveedor;

    public AdaptadorPagoExterno(ProveedorPagoExterno proveedor) {
        this.proveedor = proveedor;
    }

    @Override
    public boolean procesar(double monto) {
        String resultado = proveedor.cobrar(String.valueOf(monto));
        return "OK".equals(resultado);
    }
}
