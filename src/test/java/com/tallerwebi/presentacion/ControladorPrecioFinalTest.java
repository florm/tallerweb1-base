package com.tallerwebi.presentacion;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.tallerwebi.dominio.ServicioPrecioFinal;
import com.tallerwebi.dominio.excepcion.TipoClienteInvalidoException;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

public class ControladorPrecioFinalTest {

  ServicioPrecioFinal servicioPrecioFinal = mock(ServicioPrecioFinal.class);

  ControladorPrecioFinal controladorPrecioFinal = new ControladorPrecioFinal(servicioPrecioFinal);

  @Test
  public void deberiaMostrarAlUsuarioElPrecioBaseCuandoEsDeTipoNormal() {
    //preparacion
    when(servicioPrecioFinal.calcularPrecio(1000.00, "NORMAL")).thenReturn(1000.00);

    //ejecucion
    ModelAndView mav = whenUsuarioEsDeTipo("NORMAL");

    //comprobacion
    thenElPrecioMostradoEs(1000.00, mav);
    thenLaVistaEs("resultado", mav);
  }

  @Test
  public void deberiaMostrarAlUsuarioElPrecioCOnDescuento10PorcientoCuandoEsDeTipoVIP() {
    //preparacion
    when(servicioPrecioFinal.calcularPrecio(1000.00, "VIP")).thenReturn(900.00);

    //ejecucion
    ModelAndView mav = whenUsuarioEsDeTipo("VIP");

    //comprobacion
    thenElPrecioMostradoEs(900.00, mav);
    thenLaVistaEs("resultado", mav);
  }

  @Test
  public void deberiaMostrarAlUsuarioElPrecioConDescuento20PorcientoCuandoEsDeTipoPremium() {
    //preparacion
    when(servicioPrecioFinal.calcularPrecio(1000.00, "PREMIUM")).thenReturn(800.00);

    //ejecucion
    ModelAndView mav = whenUsuarioEsDeTipo("PREMIUM");

    //comprobacion
    thenElPrecioMostradoEs(800.00, mav);
    thenLaVistaEs("resultado", mav);
  }

  @Test
  public void siNoExisteElTipoDeClienteSeMuestraAlUsuarioUnMensajeDeError() {
    when(servicioPrecioFinal.calcularPrecio(1000.00, "TIPO_NO_EXISTE"))
      .thenThrow(new TipoClienteInvalidoException());

    ModelAndView mav = whenUsuarioEsDeTipo("TIPO_NO_EXISTE");
    thenLaVistaEs("listado-clases", mav);
    thenElMensajeDeErrorEs("No existe el tipo de cliente", mav);
  }

  private void thenElMensajeDeErrorEs(String mensajeErrorEsperado, ModelAndView mav) {
    String mensajeError = (String) mav.getModel().get("mensajeError");
    assertThat(mensajeErrorEsperado, equalTo(mensajeError));
  }

  private void thenLaVistaEs(String nombreEsperadoDeVista, ModelAndView mav) {
    String nombreVista = mav.getViewName();
    assertThat(nombreEsperadoDeVista, equalTo(nombreVista));
  }

  private ModelAndView whenUsuarioEsDeTipo(String tipoCliente) {
    ModelAndView mav = controladorPrecioFinal.calcularPrecio(1000.00, tipoCliente);
    return mav;
  }

  private void thenElPrecioMostradoEs(Double precioMostrado, ModelAndView mav) {
    Double precioCalculado = (Double) mav.getModel().get("precioFinal");
    assertThat(precioMostrado, equalTo(precioCalculado));
  }
}
