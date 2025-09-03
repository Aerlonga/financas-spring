package dev.financas.FinancasSpring.rest.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class UsuarioController {

    @GetMapping("/usuario")
    public String primeiraRota() {
        return "Testando rota usuário!";
    }
}