package br.com.testes.cadcli.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Setter
@Getter
public class ClienteDto {

    @NotEmpty(message = "Nome é obrigatorio")
    private String nome;

    @NotEmpty(message = "CPF é obrigatorio")
    private String cpf;

    @NotEmpty(message = "Email é obrigatorio")
    @Email
    private String email;

}
