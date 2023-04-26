package br.com.ada.testeautomatizado.controller;


import br.com.ada.testeautomatizado.dto.ClienteDTO;
import br.com.ada.testeautomatizado.model.Cliente;
import br.com.ada.testeautomatizado.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @PostMapping("/cadastrar")
    public ResponseEntity<String> cadastrar(@RequestBody ClienteDTO clienteDTO) {
        try {
            return ResponseEntity.ok(this.clienteService.cadastrar(clienteDTO));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("deu ruim");
        }
    }

    @DeleteMapping("/deletarClientePor/{cpf}")
    public ResponseEntity<String> deletarClientePorCPF(@PathVariable("cpf") String cpf) {
        boolean isDelete = this.clienteService.deletarClientePorCPF(cpf);
        if(isDelete)
            return ResponseEntity.ok("SUCESSO");
        else
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/atualizar")
    public ResponseEntity<ClienteDTO> atualizar(@RequestBody ClienteDTO clienteDTO) {
        return this.clienteService.atualizar(clienteDTO);
    }

}
