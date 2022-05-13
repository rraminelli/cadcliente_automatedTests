package br.com.testes.cadcli.testesIntegracaoService;

import br.com.testes.cadcli.model.Cliente;
import br.com.testes.cadcli.service.ClienteService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import static org.assertj.core.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ClienteServiceIntegTest {

    @Autowired
    ClienteService clienteService;

    Cliente clienteTeste;

    @BeforeAll
    static void iniciaTestesAll() {
        System.out.println("iniciaTestesAll");
    }

    @BeforeEach
    void iniciaTestesEach() {
        System.out.println("iniciaTestesEach");
        clienteTeste = new Cliente();
        clienteTeste.setNome("Cliente teste 7");
        clienteTeste.setEmail("teste@teste.com");
        clienteTeste.setCpf("56565");
    }

    @Test
    @Transactional
    void salvarClienteTesteInteg() {
        Cliente clienteSalvo = clienteService.salva(clienteTeste);

        assertNotNull(clienteSalvo.getId());
        assertEquals(clienteTeste.getNome(), clienteSalvo.getNome());
    }

    @Test
    void emailObrigatorioTeste() {
        clienteTeste.setEmail(null);
        try {
            Cliente clienteSalvo = clienteService.salva(clienteTeste);
            fail("Deveria dar erro");
        } catch (Exception e) {
            assertEquals("Email é obrigatorio", e.getMessage());
        }
    }

    @Test
    void listarClienteTeste() {

        List<Cliente> clientes = clienteService.lista();

        assertNotNull(clientes);
        assertTrue(clientes.size() > 0);

    }

    @Test
    void salvarClienteEListar() {

        Cliente clienteRetorno = clienteService.salva(clienteTeste);

        List<Cliente> clientesLista = clienteService.lista();

        assertTrue(clientesLista.contains(clienteRetorno));

        AtomicReference<Cliente> clienteRetornoLista = new AtomicReference<>();
        clientesLista.forEach(cliente -> {
            if (cliente.equals(clienteRetorno)) {
                clienteRetornoLista.set(cliente);
            }
        });

        assertNotNull(clienteRetornoLista.get());
        assertEquals(clienteRetorno.getNome(), clienteRetornoLista.get().getNome());

    }

}
