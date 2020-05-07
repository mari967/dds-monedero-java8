package dds.monedero.model;

public class Deposito implements TipoMovimiento {


    @Override
    public double signoDelMonto() {
        return 1;
    }
}
