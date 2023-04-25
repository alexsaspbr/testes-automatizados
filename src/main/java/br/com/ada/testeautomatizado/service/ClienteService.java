package br.com.ada.testeautomatizado.service;

import br.com.ada.testeautomatizado.model.Cliente;
import br.com.ada.testeautomatizado.repository.ClienteRepository;
import br.com.ada.testeautomatizado.util.ValidacaoCPF;
import br.com.ada.testeautomatizado.util.ValidacaoMaiorIdade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

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
            throw e;
        }
    }

    public boolean deletarClientePorCPF(String cpf) {
        try {
            findClienteByCpf(cpf).ifPresent(this.clienteRepository::delete);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private Optional<Cliente> findClienteByCpf(String cpf) {
        return this.clienteRepository.findByCpf(cpf);
    }
}
