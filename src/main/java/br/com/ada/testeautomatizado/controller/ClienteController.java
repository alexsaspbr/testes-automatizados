package br.com.ada.testeautomatizado.controller;

import br.com.ada.testeautomatizado.Cliente;
import br.com.ada.testeautomatizado.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @PostMapping("/cadastrar")
    public ResponseEntity<String> cadastrar(@RequestBody Cliente cliente) {
        this.clienteService.cadastrar(cliente);
        return ResponseEntity.ok("Cadastrado com sucesso!");
    }

}
