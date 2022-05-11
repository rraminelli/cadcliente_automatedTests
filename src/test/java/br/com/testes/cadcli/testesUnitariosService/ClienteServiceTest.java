package br.com.testes.cadcli.testesUnitariosService;

import br.com.testes.cadcli.model.Cliente;
import br.com.testes.cadcli.repository.ClienteRepository;
import br.com.testes.cadcli.service.ClienteService;
import br.com.testes.cadcli.service.EnvioEmailService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class ClienteServiceTest {

    @InjectMocks
    private ClienteService clienteService;
    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private EnvioEmailService envioEmailService;

    @Test
    void salvarClienteTeste() {
        Cliente clienteSalvar = new Cliente();
        clienteSalvar.setNome("Teste");
        clienteSalvar.setCpf("1234");
        clienteSalvar.setEmail("teste@test.com");

        Cliente clienteRetorno = new Cliente();
        clienteRetorno.setId(1234L);
        clienteRetorno.setNome("Teste");
        clienteRetorno.setCpf("1234");
        clienteRetorno.setEmail("teste@test.com");

        Mockito.when(clienteRepository.save(clienteSalvar))
                .thenReturn(clienteRetorno);

        clienteRetorno = clienteService.salva(clienteSalvar);

        Mockito.verify(envioEmailService)
                        .enviaEmail(
                                clienteSalvar.getEmail(),
                                "Bem vindo",
                                "Bem vindo ao sistema"
                        );

        Assertions.assertNotNull(clienteRetorno);
        Assertions.assertNotNull(clienteRetorno.getId());
        Assertions.assertEquals(1234L, clienteRetorno.getId());
        Assertions.assertEquals(clienteSalvar.getNome(), clienteRetorno.getNome());
        Assertions.assertEquals(clienteSalvar.getCpf(), clienteRetorno.getCpf());
    }


    @Test
    void listaClienteTeste() {

        List<Cliente> clientesList = new ArrayList<>();
        clientesList.add(new Cliente(1L, "Cliente 1", "123", "cliente1@test.com"));
        clientesList.add(new Cliente(2L, "Cliente 2", "456", "cliente2@test.com"));
        clientesList.add(new Cliente(3L, "Cliente 3", "54656", "cliente3@test.com"));

        Mockito.when(clienteRepository.findAll())
                .thenReturn(clientesList);

        List<Cliente> clientes = clienteService.lista();

        Assertions.assertNotNull(clientes);
        Assertions.assertFalse(clientes.isEmpty());
        Assertions.assertEquals(3, clientesList.size());

    }


}
