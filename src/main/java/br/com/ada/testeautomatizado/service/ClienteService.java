package br.com.ada.testeautomatizado.service;

import br.com.ada.testeautomatizado.dto.ClienteDTO;
import br.com.ada.testeautomatizado.exception.CPFValidationException;
import br.com.ada.testeautomatizado.exception.ClienteNotFoundException;
import br.com.ada.testeautomatizado.exception.MaiorIdadeInvalidaException;
import br.com.ada.testeautomatizado.model.Cliente;
import br.com.ada.testeautomatizado.repository.ClienteRepository;
import br.com.ada.testeautomatizado.util.ClienteDTOConverter;
import br.com.ada.testeautomatizado.util.Response;
import br.com.ada.testeautomatizado.util.ValidacaoCPF;
import br.com.ada.testeautomatizado.util.ValidacaoMaiorIdade;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
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

    public ResponseEntity<Response<ClienteDTO>> cadastrar(String correlationId, ClienteDTO clienteDTO) {
        log.debug("Executando cadastrar no ClienteService {}", correlationId);
        try {
            log.trace("Dados clienteDTO {}", clienteDTO.toString());
            this.validacaoCPF.isValid(clienteDTO.getCpf());
            this.validacaoMaiorIdade.isMaiorIdade(clienteDTO.getDataNascimento());
            Cliente cliente = new Cliente();
            cliente.setCpf(clienteDTO.getCpf());
            cliente.setNome(clienteDTO.getNome());
            cliente.setDataNascimento(clienteDTO.getDataNascimento());
            this.clienteRepository.save(cliente);
            log.debug("Cadastrou cliente com sucesso");
            return ResponseEntity.ok(new Response<ClienteDTO>("Sucesso", clienteDTO));
        } catch (CPFValidationException | MaiorIdadeInvalidaException e) {
            log.debug("Validacoes cadastrar no ClienteService");
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                    .body(new Response<ClienteDTO>(e.getMessage(), null));
        } catch (Exception e) {
            log.error("Erro no cadastrar do ClienteService {}", e.getMessage());
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

    public ResponseEntity<Response<ClienteDTO>> atualizar(ClienteDTO clienteDTO) {

        try {

            this.validacaoCPF.isValid(clienteDTO.getCpf());
            this.validacaoMaiorIdade.isMaiorIdade(clienteDTO.getDataNascimento());

            Optional<Cliente> optionalCliente = this.clienteRepository.findByCpf(clienteDTO.getCpf());
            if (optionalCliente.isPresent()) {

                Cliente cliente = new Cliente();
                cliente.setId(optionalCliente.get().getId());
                cliente.setCpf(clienteDTO.getCpf());
                cliente.setNome(clienteDTO.getNome());
                cliente.setDataNascimento(clienteDTO.getDataNascimento());

                Cliente clienteAtualizadoBD = this.clienteRepository.save(cliente);

                ClienteDTO clienteDTOAtualizado = this.clienteDTOConverter.convertFrom(clienteAtualizadoBD);

                Response<ClienteDTO> response = new Response<>("Sucesso", clienteDTOAtualizado);

                return ResponseEntity.ok(response);

            } else {
                throw new ClienteNotFoundException();
            }

        } catch (ClienteNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (CPFValidationException | MaiorIdadeInvalidaException e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                                 .body(new Response<>(e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }

    }
}

