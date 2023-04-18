package br.com.ada.testeautomatizado.service;

import br.com.ada.testeautomatizado.exception.CPFValidationException;
import br.com.ada.testeautomatizado.model.Cliente;
import br.com.ada.testeautomatizado.util.ValidacaoCPF;
import br.com.ada.testeautomatizado.util.ValidacaoMaiorIdade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {

    @Autowired
    private ValidacaoCPF validacaoCPF;

    @Autowired
    private ValidacaoMaiorIdade validacaoMaiorIdade;

    public String cadastrar(Cliente cliente) {
        try {
            this.validacaoCPF.isValid(cliente.getCpf());
            this.validacaoMaiorIdade.isMaiorIdade(cliente.getDataNascimento());
            return "SUCESSO";
        } catch (Exception e) {
            throw new CPFValidationException();
        }
    }

}
