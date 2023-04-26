package br.com.ada.testeautomatizado.service;

import br.com.ada.testeautomatizado.dto.ClienteDTO;
import br.com.ada.testeautomatizado.model.Cliente;
import br.com.ada.testeautomatizado.repository.ClienteRepository;
import br.com.ada.testeautomatizado.util.ClienteDTOConverter;
import br.com.ada.testeautomatizado.util.ValidacaoCPF;
import br.com.ada.testeautomatizado.util.ValidacaoMaiorIdade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ValidacaoCPF validacaoCPF;

    @Autowired
    private ValidacaoMaiorIdade validacaoMaiorIdade;

    @Autowired
    private ClienteDTOConverter clienteDTOConverter;

    public String cadastrar(ClienteDTO clienteDTO) {
        try {
            this.validacaoCPF.isValid(clienteDTO.getCpf());
            this.validacaoMaiorIdade.isMaiorIdade(clienteDTO.getDataNascimento());
            Cliente cliente = new Cliente();
            cliente.setCpf(clienteDTO.getCpf());
            cliente.setNome(clienteDTO.getNome());
            cliente.setDataNascimento(clienteDTO.getDataNascimento());
            this.clienteRepository.save(cliente);
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

    public ResponseEntity<ClienteDTO> atualizar(ClienteDTO clienteDTO) {

        try {

            Optional<Cliente> optionalCliente = this.clienteRepository.findByCpf(clienteDTO.getCpf());
            if(optionalCliente.isPresent()) {

                Cliente cliente = new Cliente();
                cliente.setId(optionalCliente.get().getId());
                cliente.setCpf(clienteDTO.getCpf());
                cliente.setNome(clienteDTO.getNome());
                cliente.setDataNascimento(clienteDTO.getDataNascimento());

                Cliente clienteAtualizadoBD = this.clienteRepository.save(cliente);

                ClienteDTO clienteDTOAtualizado = this.clienteDTOConverter.convertFrom(clienteAtualizadoBD);

                return ResponseEntity.ok(clienteDTOAtualizado);

            } else {

                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

            }

        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }

    }
}
