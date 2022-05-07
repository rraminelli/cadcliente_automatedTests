package br.com.testes.cadcli.testesUnitariosService;

import br.com.testes.cadcli.model.Cliente;
import br.com.testes.cadcli.repository.ClienteRepository;
import br.com.testes.cadcli.service.ClienteService;
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
public class ClienteServiceTeste {

    @InjectMocks
    private ClienteService clienteService;
    @Mock
    private ClienteRepository clienteRepository;

    @Test
    void salvarClienteTeste() {
        Cliente clienteSalvar = new Cliente();
        clienteSalvar.setNome("Teste");
        clienteSalvar.setCpf("1234");

        Cliente clienteRetorno = new Cliente();
        clienteRetorno.setId(1234L);
        clienteRetorno.setNome("Teste");
        clienteRetorno.setCpf("1234");

        Mockito.when(clienteRepository.save(clienteSalvar))
                .thenReturn(clienteRetorno);

        clienteRetorno = clienteService.salva(clienteSalvar);

        Assertions.assertNotNull(clienteRetorno);
        Assertions.assertNotNull(clienteRetorno.getId());
        Assertions.assertEquals(1234L, clienteRetorno.getId());
        Assertions.assertEquals(clienteSalvar.getNome(), clienteRetorno.getNome());
        Assertions.assertEquals(clienteSalvar.getCpf(), clienteRetorno.getCpf());
    }


    @Test
    void listaClienteTeste() {

        List<Cliente> clientesList = new ArrayList<>();
        clientesList.add(new Cliente(1L, "Cliente 1", "123"));
        clientesList.add(new Cliente(2L, "Cliente 2", "456"));
        clientesList.add(new Cliente(3L, "Cliente 3", "54656"));

        Mockito.when(clienteRepository.findAll())
                .thenReturn(clientesList);

        List<Cliente> clientes = clienteService.lista();

        Assertions.assertNotNull(clientes);
        Assertions.assertFalse(clientes.isEmpty());
        Assertions.assertEquals(3, clientesList.size());

    }


}
