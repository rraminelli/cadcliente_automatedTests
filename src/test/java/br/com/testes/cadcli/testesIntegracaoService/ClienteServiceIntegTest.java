package br.com.testes.cadcli.testesIntegracaoService;

import br.com.testes.cadcli.model.Cliente;
import br.com.testes.cadcli.service.ClienteService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ClienteServiceIntegTest {

    @Autowired
    ClienteService clienteService;

    @Test
    void salvarClienteTesteInteg() {
        Cliente cliente = new Cliente();
        cliente.setNome("Cliente teste 6");
        cliente.setEmail("teste@teste.com");
        cliente.setCpf("56565");

        Cliente clienteSalvo = clienteService.salva(cliente);

        Assertions.assertNotNull(clienteSalvo.getId());
        Assertions.assertEquals(cliente.getNome(), clienteSalvo.getNome());
    }

    @Test
    void emailObrigatorioTeste() {
        Cliente cliente = new Cliente();
        cliente.setNome("Cliente teste 6");
        cliente.setCpf("56565");
        try {
            Cliente clienteSalvo = clienteService.salva(cliente);
            Assertions.fail("Deveria dar erro");
        } catch (Exception e) {
            Assertions.assertEquals("Email é obrigatorio", e.getMessage());
        }
    }


}
