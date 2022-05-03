package br.com.testes.cadcli.service;

import br.com.testes.cadcli.model.Cliente;
import br.com.testes.cadcli.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public Cliente salva(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    public List<Cliente> lista() {
        return clienteRepository.findAll();
    }

}
