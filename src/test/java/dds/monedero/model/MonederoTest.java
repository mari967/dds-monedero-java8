package dds.monedero.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import dds.monedero.exceptions.MaximaCantidadDepositosException;
import dds.monedero.exceptions.MaximoExtraccionDiarioException;
import dds.monedero.exceptions.MontoNegativoException;
import dds.monedero.exceptions.SaldoMenorException;

public class MonederoTest {
  private Cuenta cuenta;

  @Before
  public void init() {
    cuenta = new Cuenta();
  }

  @Test
  public void Poner() {
    cuenta.poner(1500);
    Assert.assertEquals(1500, cuenta.getSaldo(), 0.01);
  }

  @Test(expected = MontoNegativoException.class)
  public void PonerMontoNegativo() {
    cuenta.poner(-1500);
  }

  @Test
  public void Poner1500ySacar600dejaSaldo900() {
    cuenta.poner(1500);
    cuenta.sacar(600);
    Assert.assertEquals(900.0, cuenta.getSaldo(), 0.01);
  }


  @Test
  public void TresDepositos() {
    cuenta.poner(1500);
    cuenta.poner(456);
    cuenta.poner(1900);
    Assert.assertEquals(1500+456+1900, cuenta.getSaldo(), 0.01);
  }

  @Test(expected = MaximaCantidadDepositosException.class)
  public void MasDeTresDepositos() {
    cuenta.poner(1500);
    cuenta.poner(456);
    cuenta.poner(1900);
    cuenta.poner(245);
  }

  @Test(expected = SaldoMenorException.class)
  public void ExtraerMasQueElSaldo() {
    cuenta.setSaldo(90);
    cuenta.sacar(1001);
  }

  @Test(expected = MaximoExtraccionDiarioException.class)
  public void ExtraerMasDe1000() {
    cuenta.setSaldo(5000);
    cuenta.sacar(1001);
  }

  @Test(expected = MontoNegativoException.class)
  public void ExtraerMontoNegativo() {
    cuenta.sacar(-500);
  }

  @Test
  public void cantMovimientosIgualAcantOperacionesCorrectas() {

    cuenta.poner(1500);
    cuenta.poner(456);
    cuenta.sacar(10);
    cuenta.poner(245);  //Cuatro operaciones

    Assert.assertEquals(4,cuenta.getMovimientos().size(), 0.01);
  }

}