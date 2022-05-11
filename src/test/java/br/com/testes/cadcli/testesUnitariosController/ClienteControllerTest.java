package br.com.testes.cadcli.testesUnitariosController;

import br.com.testes.cadcli.controller.ClienteController;
import br.com.testes.cadcli.dto.ClienteDto;
import br.com.testes.cadcli.model.Cliente;
import br.com.testes.cadcli.service.ClienteService;
import br.com.testes.cadcli.service.EnvioEmailService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.internal.matchers.Matches;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.result.StatusResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ContextConfiguration
@ExtendWith(SpringExtension.class)
@WebMvcTest(value = ClienteController.class)
class ClienteControllerTest {

    @MockBean
    private ClienteService clienteService;

    @MockBean
    private EnvioEmailService envioEmailService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser
    void listarTodosClientesControllerTest() throws Exception {

        List<Cliente> clientesList = new ArrayList<>();
        clientesList.add(new Cliente(1L, "Cliente 1", "123", "cliente1@test.com"));
        clientesList.add(new Cliente(2L, "Cliente 2", "456", "cliente2@test.com"));
        clientesList.add(new Cliente(3L, "Rodrigo", "54656", "cliente3@test.com"));

        Mockito.when(clienteService.lista())
                .thenReturn(clientesList);

        this.mockMvc.perform(
                    MockMvcRequestBuilders.get("/cliente")
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.model().attributeExists("clientes"))
                .andExpect(MockMvcResultMatchers.view().name("cliente-list"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("<td>Rodrigo</td>")));
    }

    @Test
    @WithMockUser
    void salvarClienteTestController() throws Exception {

        ClienteDto clienteDto = new ClienteDto();
        clienteDto.setNome("Cliente Teste");
        clienteDto.setEmail("teste@teste.com");
        clienteDto.setCpf("2656565");

        Cliente cliente = new Cliente();
        cliente.setNome(clienteDto.getNome());
        cliente.setCpf(clienteDto.getCpf());
        cliente.setEmail(clienteDto.getEmail());

        Mockito.when(clienteService.salva(cliente))
                .thenReturn(cliente);

        this.mockMvc.perform(
                MockMvcRequestBuilders.post("/cliente/salva")
                        .flashAttr("clienteDto", clienteDto)
        )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/cliente"))
        ;

        Assertions.assertEquals(clienteDto.getEmail(), cliente.getEmail());
    }

}
