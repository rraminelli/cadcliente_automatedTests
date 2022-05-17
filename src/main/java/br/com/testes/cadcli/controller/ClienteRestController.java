package br.com.testes.cadcli.controller;

import br.com.testes.cadcli.dto.ClienteDto;
import br.com.testes.cadcli.model.Cliente;
import br.com.testes.cadcli.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/cliente-rest")
@RestController
public class ClienteRestController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping
    public ResponseEntity<List<Cliente>> listaTodos() {
        return ResponseEntity.ok(clienteService.lista());
    }

    @PostMapping("/salva")
    public void salva(@RequestBody @Valid ClienteDto clienteDto) {

        Cliente cliente = new Cliente();
        cliente.setNome(clienteDto.getNome());
        cliente.setCpf(clienteDto.getCpf());
        cliente.setEmail(clienteDto.getEmail());

        clienteService.salva(cliente);

    }


}
