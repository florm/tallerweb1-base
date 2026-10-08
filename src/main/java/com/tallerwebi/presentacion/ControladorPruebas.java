package com.tallerwebi.presentacion;

import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
// import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControladorPruebas {

  @RequestMapping("/saludo")
  public ModelAndView irASaludo() {
    return new ModelAndView("saludo-form");
  }

  @RequestMapping("/mostrar-saludo")
  public ModelAndView saludar(@RequestParam("nombre") String nombre) {
    Map<String, Object> model = new ModelMap();
    model.put("nombre", nombre);
    return new ModelAndView("saludo-resultado", model);
  }
  // @RequestMapping("/productos")
  // public ModelAndView buscarProducto() {
  //     return new ModelAndView("buscar-producto");
  // }

  // @RequestMapping(value = "/buscar-producto", method = RequestMethod.POST)
  // public ModelAndView buscarProducto(@RequestParam Long id) {

  //     return new ModelAndView("redirect:/productos/" + id);
  // }

  // @RequestMapping("/productos/{id}")
  // public ModelAndView detalleProducto(@PathVariable Long id) {
  //     ModelMap model = new ModelMap();
  //     model.put("id", id);
  //     return new ModelAndView("detalle-producto", model);
  // }
}
