package br.com.testes.cadcli.testesIntegracaoRestController;

import br.com.testes.cadcli.dto.ClienteDto;
import br.com.testes.cadcli.model.Cliente;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ClienteRestControllerIntegTest {

    @Autowired
    TestRestTemplate restTemplate;
    static String username;
    static String password;

    @BeforeAll
    static void iniciaTestes() {
        username = "admin";
        password = "1234";
    }

    @Test
    void salvarClienteControllerIntTeste() {

        ClienteDto clienteDto = new ClienteDto();
        clienteDto.setNome("Nome cliente teste");
        clienteDto.setCpf("1265656");
        clienteDto.setEmail("teste@email.com");

        HttpHeaders httpHeaders = new HttpHeaders();
        HttpEntity httpEntity = new HttpEntity(clienteDto, httpHeaders);

        ResponseEntity response = this.restTemplate
                .withBasicAuth(username, password)
                .exchange("/cliente/salva", HttpMethod.POST, httpEntity, Void.class);

        Assertions.assertEquals(302, response.getStatusCodeValue());

    }

    @Test
    void listarClientesControllerIntegTeste() {

        HttpHeaders httpHeaders = new HttpHeaders();
        HttpEntity httpEntity = new HttpEntity(httpHeaders);

        ResponseEntity<Cliente[]> response = this.restTemplate
                .withBasicAuth(username, password)
                .exchange("/cliente-rest", HttpMethod.GET, httpEntity, Cliente[].class);

        Assertions.assertEquals(200, response.getStatusCodeValue());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertNotEquals(0, response.getBody().length);
        Assertions.assertNotNull(response.getBody()[0].getNome());

    }

}
