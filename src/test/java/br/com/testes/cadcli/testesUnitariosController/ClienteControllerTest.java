package br.com.testes.cadcli.testesUnitariosController;

import br.com.testes.cadcli.controller.ClienteController;
import br.com.testes.cadcli.model.Cliente;
import br.com.testes.cadcli.service.ClienteService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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

@ContextConfiguration
@ExtendWith(SpringExtension.class)
@WebMvcTest(value = ClienteController.class)
public class ClienteControllerTest {

    @MockBean
    private ClienteService clienteService;

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

}
