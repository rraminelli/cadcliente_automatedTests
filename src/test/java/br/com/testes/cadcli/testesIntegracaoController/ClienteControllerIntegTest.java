package br.com.testes.cadcli.testesIntegracaoController;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ClienteControllerIntegTest {

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

        MultiValueMap<String, Object> clienteForm = new LinkedMultiValueMap<>();
        clienteForm.add("nome", "Nome cliente teste");
        clienteForm.add("cpf", "1265656");
        clienteForm.add("email", "teste@email.com");

        HttpHeaders httpHeaders = new HttpHeaders();
        HttpEntity httpEntity = new HttpEntity(clienteForm, httpHeaders);

        ResponseEntity<String> response = this.restTemplate
                .withBasicAuth(username, password)
                .exchange("/cliente/salva", HttpMethod.POST, httpEntity, String.class);

        Assertions.assertEquals(302, response.getStatusCodeValue());

    }

    @Test
    void listarClientesControllerIntegTeste() {

        HttpHeaders httpHeaders = new HttpHeaders();
        HttpEntity httpEntity = new HttpEntity(httpHeaders);

        ResponseEntity<String> response = this.restTemplate
                .withBasicAuth(username, password)
                .exchange("/cliente", HttpMethod.GET, httpEntity, String.class);

        String responseString = response.toString();

        Assertions.assertEquals(200, response.getStatusCodeValue());
        Assertions.assertTrue(responseString.contains("Lista clientes"));

    }

    @Test
    void clienteEditarTeste() {

        HttpHeaders httpHeaders = new HttpHeaders();
        HttpEntity httpEntity = new HttpEntity(httpHeaders);

        ResponseEntity<String> response = this.restTemplate
                .withBasicAuth(username, password)
                .exchange("/cliente/novo", HttpMethod.GET, httpEntity, String.class);

        String responseString = response.toString();

        Assertions.assertEquals(200, response.getStatusCodeValue());
        Assertions.assertTrue(responseString.contains("Editar cliente"));

    }
}
