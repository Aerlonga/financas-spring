package dev.financas.FinancasSpring;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class Controller {

    @GetMapping("/primeirarota")
    public String primeiraRota(){
        return "Funcionando!";
    }
}
