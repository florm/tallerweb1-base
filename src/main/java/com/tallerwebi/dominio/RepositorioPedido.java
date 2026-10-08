package com.tallerwebi.dominio;

import java.time.LocalDate;
import java.util.List;

/**
 * RepositorioPedido
 */
public interface RepositorioPedido {

    LocalDate obtenerFechaEntregaPorCodigo(String string);

    String obtenerNombreCompletoClientePorCodigo(String string);

    List<Pedido> listarPedidosDeUsuario(String string);

}
