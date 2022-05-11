package br.com.testes.cadcli.controller;

import br.com.testes.cadcli.dto.ClienteDto;
import br.com.testes.cadcli.model.Cliente;
import br.com.testes.cadcli.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@RequestMapping("/cliente")
@Controller
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping
    public String listaTodos(Model model) {
        model.addAttribute("clientes", clienteService.lista());
        return "cliente-list";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("cliente", new Cliente());
        return "cliente-edit";
    }

    @PostMapping("/salva")
    public String salva(@ModelAttribute @Valid ClienteDto clienteDto, BindingResult errors) {

        if (errors.hasErrors()) {
            return "cliente-edit";
        }

        Cliente cliente = new Cliente();
        cliente.setNome(clienteDto.getNome());
        cliente.setCpf(clienteDto.getCpf());
        cliente.setEmail(clienteDto.getEmail());

        cliente = clienteService.salva(cliente);
        return "redirect:/cliente";
    }


}
