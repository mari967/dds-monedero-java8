package dds.monedero.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import dds.monedero.exceptions.MaximaCantidadDepositosException;
import dds.monedero.exceptions.MaximoExtraccionDiarioException;
import dds.monedero.exceptions.MontoNegativoException;
import dds.monedero.exceptions.SaldoMenorException;

public class Cuenta {

  private double saldo = 0;
  private List<Movimiento> movimientos = new ArrayList<>();
  private int cantMaxDepositos = 3;
  private int montoMaxExtraccion = 1000;


  public Cuenta() {
    saldo = 0;
  } // pa ké?

  public Cuenta(double montoInicial) {
    saldo = montoInicial;
  }

  public void setMovimientos(List<Movimiento> movimientos) {
    this.movimientos = movimientos;
  }

  public void poner(double cuanto) {

    controlarMonto(cuanto);

    if (getMovimientos().stream().filter(movimiento -> movimiento.getTipoMovimiento() instanceof Deposito).count()
            >= cantMaxDepositos) {
      throw new MaximaCantidadDepositosException("Ya excedio los " + cantMaxDepositos + " depositos diarios");
    }

    agregateA(new Movimiento(LocalDate.now(), cuanto, new Deposito())); // Debe hacerlo cuenta
  }

  public void sacar(double cuanto) {

    controlarMonto(cuanto);

    if (getSaldo() - cuanto < 0) {
      throw new SaldoMenorException("No puede sacar mas de " + getSaldo() + " $");
    }

    double montoExtraidoHoy = getMontoExtraidoA(LocalDate.now());
    double limite = montoMaxExtraccion - montoExtraidoHoy;

    if (cuanto > limite) {
      throw new MaximoExtraccionDiarioException("No puede extraer mas de $ " + montoMaxExtraccion
              + " diarios, límite: " + limite);
    }
    agregateA(new Movimiento(LocalDate.now(), cuanto, new Extraccion()));
  }

  private void controlarMonto(double cuanto) {
    if (cuanto <= 0) {
      throw new MontoNegativoException(cuanto + ": el monto a ingresar debe ser un valor positivo");
    }
  }


  public double getMontoExtraidoA(LocalDate fecha) {
    return getMovimientos().stream()
            .filter(movimiento -> movimiento.ocurrioOperacion(new Extraccion(), fecha))
            .mapToDouble(Movimiento::getMonto)
            .sum();
  }

  public List<Movimiento> getMovimientos() {
    return movimientos;
  }

  public double getSaldo() {
    return saldo;
  }

  public void setSaldo(double saldo) {
    this.saldo = saldo;
  }


  private void agregateA(Movimiento unMovimiento) {
    setSaldo(calcularValor(unMovimiento));
    movimientos.add(unMovimiento);
  }

  /*  public void agregarMovimiento(Movimiento unMovimiento) {
    //Movimiento movimiento = new Movimiento(fecha, cuanto, esDeposito);
    movimientos.add(unMovimiento);
  }*/

  private double calcularValor(Movimiento unMovimiento) {
    return getSaldo() + unMovimiento.getMontoConSigno();
  }
}
