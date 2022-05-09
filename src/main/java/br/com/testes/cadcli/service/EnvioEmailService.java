package br.com.testes.cadcli.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EnvioEmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    public void enviaEmail(String email, String assunto, String mensagem) {
        if (email == null) {
            throw new IllegalArgumentException("Email é obrigatorio");
        }
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(email);
        simpleMailMessage.setSubject(assunto);
        simpleMailMessage.setText(mensagem);
        javaMailSender.send(simpleMailMessage);
    }

}
