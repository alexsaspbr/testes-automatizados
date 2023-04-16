package br.com.ada.testeautomatizado.service;

import br.com.ada.testeautomatizado.exception.CPFValidationException;
import br.com.ada.testeautomatizado.model.Cliente;
import br.com.ada.testeautomatizado.util.ValidacaoCPF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {

    @Autowired
    private ValidacaoCPF validacaoCPF;

    public String cadastrar(Cliente cliente) {
        try {
            this.validacaoCPF.isValid(cliente.getCpf());
            return "SUCESSO";
        } catch (Exception e) {
            throw new CPFValidationException();
        }
    }

}
