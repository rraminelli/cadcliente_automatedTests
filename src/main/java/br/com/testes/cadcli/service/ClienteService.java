package br.com.testes.cadcli.service;

import br.com.testes.cadcli.model.Cliente;
import br.com.testes.cadcli.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private EnvioEmailService envioEmailService;

    public Cliente salva(Cliente cliente) {
        cliente = clienteRepository.save(cliente);
        this.enviaEmailBoasVindas(cliente);
        return cliente;
    }

    public List<Cliente> lista() {
        return clienteRepository.findAll();
    }

    private void enviaEmailBoasVindas(Cliente cliente) {
        envioEmailService.enviaEmail(
                cliente.getEmail(),
                "Bem vindo",
                "Bem vindo ao sistema"
        );
    }
}
