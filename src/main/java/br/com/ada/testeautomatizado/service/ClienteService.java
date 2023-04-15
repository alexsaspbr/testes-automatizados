package br.com.ada.testeautomatizado.service;

import br.com.ada.testeautomatizado.model.Cliente;
import br.com.ada.testeautomatizado.util.ValidacaoCPF;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {

    private ValidacaoCPF validacaoCPF;

    public ClienteService(){
        this.validacaoCPF = new ValidacaoCPF();
    }

    public String cadastrar(Cliente cliente) {
        try {
            this.validacaoCPF.isValid(cliente.getCpf());
            return "SUCESSO";
        } catch (Exception e) {
            throw new RuntimeException("Cliente não existente!");
        }
    }

}
