package br.com.ada.testeautomatizado;

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
