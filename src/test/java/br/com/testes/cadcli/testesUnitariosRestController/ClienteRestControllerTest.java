package br.com.testes.cadcli.testesUnitariosRestController;

import br.com.testes.cadcli.controller.ClienteRestController;
import br.com.testes.cadcli.dto.ClienteDto;
import br.com.testes.cadcli.model.Cliente;
import br.com.testes.cadcli.service.ClienteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ContextConfiguration
@ExtendWith(SpringExtension.class)
@WebMvcTest(value = ClienteRestController.class)
public class ClienteRestControllerTest {

    @MockBean
    private ClienteService clienteService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser
    void listarTodosClientesControllerTest() throws Exception {

        List<Cliente> clientesList = new ArrayList<>();
        clientesList.add(new Cliente(1L, "Cliente 1", "123", "cliente1@test.com"));
        clientesList.add(new Cliente(2L, "Cliente 2", "456", "cliente2@test.com"));
        clientesList.add(new Cliente(3L, "Rodrigo", "54656", "cliente3@test.com"));

        Mockito.when(clienteService.lista())
                .thenReturn(clientesList);

        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/cliente-rest")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(clientesList.size())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].id", Matchers.is(clientesList.get(0).getId()), Long.class));
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

        this.mockMvc.perform(MockMvcRequestBuilders
                        .post("/cliente-rest/salva")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(clienteDto))
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());

        Assertions.assertEquals(clienteDto.getNome(), cliente.getNome());
        Assertions.assertEquals(clienteDto.getEmail(), cliente.getEmail());
    }

}
