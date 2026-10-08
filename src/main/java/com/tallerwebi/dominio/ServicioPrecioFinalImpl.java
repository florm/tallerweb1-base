package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.TipoClienteInvalidoException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ServicioPrecioFinalImpl implements ServicioPrecioFinal {

  private final String VIP = "VIP";
  private final String PREMIUM = "PREMIUM";
  private final String NORMAL = "NORMAL";

  @Override
  public Double calcularPrecio(Double precioBase, String tipoCliente) {
    if (tipoCliente.equals(VIP)) {
      return precioBase * 0.9; // 10% de descuento
    }

    if (tipoCliente.equals(PREMIUM)) {
      return precioBase * 0.8; // 20% de descuento
    }

    if (tipoCliente.equals(NORMAL)) {
      return precioBase;
    }

    throw new TipoClienteInvalidoException();
  }
}
