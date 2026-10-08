package com.tallerwebi.infraestructura;

import java.time.LocalDate;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tallerwebi.dominio.Pedido;
import com.tallerwebi.dominio.RepositorioPedido;

@Repository
public class RepositorioPedidoImpl implements RepositorioPedido {

    SessionFactory sessionFactory;

    @Autowired
    public RepositorioPedidoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public LocalDate obtenerFechaEntregaPorCodigo(String codigoBuscado) {
        String query = "select p.fechaEntrega from Pedido p where p.codigo = :superCodigo";

        return sessionFactory.getCurrentSession()
                .createQuery(query, LocalDate.class)
                .setParameter("superCodigo", codigoBuscado)
                .uniqueResult();
    }

    @Override
    public String obtenerNombreCompletoClientePorCodigo(String codigo) {
        // String query = "select concat(c.nombre, ' ' , c.apellido) from Pedido p join
        // p.cliente c where p.codigo = :codigo";
        String queryPropiedadesImplicitas = "select concat(p.cliente.nombre, ' ' , p.cliente.apellido) from Pedido p where p.codigo = :codigo";
        return sessionFactory.getCurrentSession()
                .createQuery(queryPropiedadesImplicitas, String.class)
                .setParameter("codigo", codigo)
                .uniqueResult();
    }

    @Override
    public List<Pedido> listarPedidosDeUsuario(String email) {
        String query = "select p from Pedido p where p.cliente.usuario.email = :email order by p.fechaEntrega desc";

        return sessionFactory.getCurrentSession()
                .createQuery(query, Pedido.class)
                .setParameter("email", email)
                .getResultList();
    }

}
