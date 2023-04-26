package br.com.ada.testeautomatizado.util;

import br.com.ada.testeautomatizado.dto.ClienteDTO;
import br.com.ada.testeautomatizado.model.Cliente;
import org.springframework.stereotype.Component;

@Component
public class ClienteDTOConverter {

    public ClienteDTO convertFrom(Cliente cliente) {
        return ClienteDTO.builder()
                .cpf(cliente.getCpf())
                .nome(cliente.getNome())
                .dataNascimento(cliente.getDataNascimento())
                .build();
    }

}
