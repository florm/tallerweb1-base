package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioPrecioFinal;
import com.tallerwebi.dominio.excepcion.TipoClienteInvalidoException;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControladorPrecioFinal {

  ServicioPrecioFinal servicioPrecioFinal;

  @Autowired
  public ControladorPrecioFinal(ServicioPrecioFinal servicioPrecioFinal) {
    this.servicioPrecioFinal = servicioPrecioFinal;
  }

  @RequestMapping("/calcular-precio")
  public ModelAndView calcularPrecio(
    @RequestParam Double precio,
    @RequestParam String tipoCliente
  ) {
    Map<String, Object> model = new ModelMap();

    try {
      Double precioCalculado = this.servicioPrecioFinal.calcularPrecio(precio, tipoCliente);
      model.put("precioFinal", precioCalculado);

      return new ModelAndView("resultado", model);
    } catch (TipoClienteInvalidoException e) {
      model.put("mensajeError", "No existe el tipo de cliente");
      return new ModelAndView("listado-clases", model);
    } catch (Exception e) {
      model.put("mensajeError", "Ocurrió un error inesperado");
      return new ModelAndView("listado-clases", model);
    }
  }
}
