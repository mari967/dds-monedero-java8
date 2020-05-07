package dds.monedero.model;

import java.time.LocalDate;

public class Movimiento {
  private LocalDate fecha;
  //En ningún lenguaje de programación usen jamás doubles para modelar dinero en el mundo real
  //siempre usen numeros de precision arbitraria, como BigDecimal en Java y similares
  private double monto;
  private TipoMovimiento tipoMovimiento;

  public Movimiento(LocalDate fecha, double monto, TipoMovimiento tipoMovimiento) {
    this.fecha = fecha;
    this.monto = monto;
    this.tipoMovimiento = tipoMovimiento;
  }

  public double getMonto() {
    return monto * tipoMovimiento.signoDelMonto();
  }

public double getMontoConSigno() {
    return monto * tipoMovimiento.signoDelMonto();
  }

  public LocalDate getFecha() {
    return fecha;
  }

  //SMELL 1
/*  public boolean fueDepositado(LocalDate fecha) {
    return isDeposito() && esDeLaFecha(fecha);
  }

  public boolean fueExtraido(LocalDate fecha) {
    return isExtraccion() && esDeLaFecha(fecha);
  }

  */
//SOL. SMELL 1
  public boolean ocurrioOperacion (TipoMovimiento unMovimiento, LocalDate fecha) {
    return tipoMovimiento == unMovimiento && esDeLaFecha(fecha);
  }

  public TipoMovimiento getTipoMovimiento() {
    return tipoMovimiento;
  }

  public boolean esDeLaFecha(LocalDate fecha) {
    return this.fecha.equals(fecha);
  }

}
