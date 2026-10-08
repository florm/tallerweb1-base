package com.tallerwebi.dominio;

/**
 * ServicioPrecioFinal
 */
@FunctionalInterface
public interface ServicioPrecioFinal {
  Double calcularPrecio(Double precioBase, String tipoCliente);
}
