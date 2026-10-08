package com.tallerwebi.infraestructura;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import java.time.LocalDate;
import java.util.List;

import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.tallerwebi.dominio.Cliente;
import com.tallerwebi.dominio.Pedido;
import com.tallerwebi.dominio.RepositorioPedido;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.infraestructura.config.HibernateInfraestructuraTestConfig;

import jakarta.transaction.Transactional;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { HibernateInfraestructuraTestConfig.class })
public class RepositorioPedidoTest {

    private RepositorioPedido repositorioPedido;

    @Autowired
    private SessionFactory sessionFactory;

    @BeforeEach
    public void init() {
        repositorioPedido = new RepositorioPedidoImpl(sessionFactory);
    }

    @Test
    @Transactional
    @Rollback
    public void deberiaObtenerLaFechaDeEntregaDeUnPedido_PorCodigo() {
        // Given
        LocalDate fechaEntregaEsperada = LocalDate.now().plusDays(3);
        Usuario usuario = givenTengoUnUsuario("test@test.com");
        Cliente cliente = givenTengoUnCliente("Flor", "Martin", usuario);
        givenTengoUnPedido("P001", fechaEntregaEsperada, cliente);
        // When
        LocalDate fechaEntrega = repositorioPedido.obtenerFechaEntregaPorCodigo("P001");
        // Then
        assertThat(fechaEntrega, is(equalTo(fechaEntregaEsperada)));
    }

    private Pedido givenTengoUnPedido(String codigo, LocalDate fechaEntrega, Cliente cliente) {
        Pedido pedido = new Pedido();
        pedido.setCodigo(codigo);
        pedido.setFechaEntrega(fechaEntrega);
        pedido.setCliente(cliente);
        sessionFactory.getCurrentSession().persist(pedido);
        return pedido;
    }

    @Test
    @Transactional
    @Rollback
    public void deberiaObtenerNombreCompletoDelCliente_PorCodigoPedido() {
        // Given
        Usuario usuario = givenTengoUnUsuario("test@test.com");
        Cliente cliente = givenTengoUnCliente("Juan", "Perez", usuario);
        givenTengoUnPedido("P002", LocalDate.now().plusDays(5), cliente);
        // When
        repositorioPedido.obtenerNombreCompletoClientePorCodigo("P002");
        // Then
        assertThat(cliente.getNombre() + " " + cliente.getApellido(), is(equalTo("Juan Perez")));

    }

    private Cliente givenTengoUnCliente(String nombre, String apellido, Usuario usuario) {
        Cliente cliente = new Cliente();
        cliente.setNombre(nombre);
        cliente.setApellido(apellido);
        cliente.setUsuario(usuario);
        sessionFactory.getCurrentSession().persist(cliente);
        return cliente;
    }

    @Test
    @Transactional
    @Rollback
    public void deberiaObtenerUnaListaDePedidos_PorEmailDeUsuario_EnOrdenDesc_PorFechaEntrega() {
        Usuario usuarioAna = givenTengoUnUsuario("ana@gmail.com");
        Usuario usuarioLuis = givenTengoUnUsuario("luis@gmail.com");

        Cliente clienteAna = givenTengoUnCliente("Ana", "Gomez", usuarioAna);
        Cliente clienteLuis = givenTengoUnCliente("Luis", "Martinez", usuarioLuis);

        givenTengoUnPedido("P001", LocalDate.now().plusDays(2), clienteAna);
        givenTengoUnPedido("P002", LocalDate.now().plusDays(1), clienteAna);
        givenTengoUnPedido("P003", LocalDate.now().plusDays(3), clienteAna);
        givenTengoUnPedido("P004", LocalDate.now().plusDays(2), clienteLuis);

        // When
        List<Pedido> pedidosDeUsuario = repositorioPedido.listarPedidosDeUsuario("ana@gmail.com");

        // Then
        assertThat(pedidosDeUsuario.size(), is(equalTo(3)));
        assertThat(pedidosDeUsuario.get(0).getCodigo(), is(equalTo("P003")));
        assertThat(pedidosDeUsuario.get(1).getCodigo(), is(equalTo("P001")));
        assertThat(pedidosDeUsuario.get(2).getCodigo(), is(equalTo("P002")));
    }

    private Usuario givenTengoUnUsuario(String email) {
        Usuario usuario = new Usuario();
        usuario.setEmail(email);
        sessionFactory.getCurrentSession().persist(usuario);
        return usuario;
    }
}
