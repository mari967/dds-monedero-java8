package dds.monedero.model;

public class Extraccion implements  TipoMovimiento {

    @Override
    public double signoDelMonto() {
        return -1;
    }
}
