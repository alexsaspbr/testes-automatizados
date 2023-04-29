package br.com.ada.testeautomatizado.service;

import br.com.ada.testeautomatizado.dto.ClienteDTO;
import br.com.ada.testeautomatizado.exception.CPFValidationException;
import br.com.ada.testeautomatizado.exception.MaiorIdadeInvalidaException;
import br.com.ada.testeautomatizado.model.Cliente;
import br.com.ada.testeautomatizado.util.ValidacaoCPF;
import br.com.ada.testeautomatizado.util.ValidacaoMaiorIdade;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClienteServiceTest {

    @InjectMocks
    private ClienteService clienteService;

    @Mock
    private ValidacaoCPF validacaoCPF;

    @Mock
    private ValidacaoMaiorIdade validacaoMaiorIdade;

    @Test
    void cadastrarSucesso() {
        ClienteDTO clienteDTO = new ClienteDTO();
        clienteDTO.setCpf("123.123.123-12");
        //cliente.setDataNascimento(LocalDate.parse("2001-01-01"));
        doCallRealMethod().when(validacaoCPF).isValid(anyString());
        doCallRealMethod().when(validacaoMaiorIdade).isMaiorIdade(any(LocalDate.class));
        assertEquals(clienteService.cadastrar("", clienteDTO), null);
        verify(validacaoCPF, times(1)).isValid(clienteDTO.getCpf());
    }

    @Test
    void cadastrarCPFInvalido() {

        doCallRealMethod().when(validacaoCPF).isValid(anyString());
        //Mockito.doNothing().when(validacaoMaiorIdade).isMaiorIdade(Mockito.nullable(LocalDate.class));

        assertThrows(CPFValidationException.class, () -> {
            ClienteDTO clienteDTO = new ClienteDTO();
            clienteDTO.setCpf("12312312310");
            clienteDTO.setDataNascimento(null);
            clienteService.cadastrar("", clienteDTO);
        });
    }

    @Test
    void cadastrarErroMaiorIdade() {

        doCallRealMethod().when(validacaoCPF).isValid(anyString());
        doCallRealMethod().when(validacaoMaiorIdade).isMaiorIdade(nullable(LocalDate.class));

        assertThrows(MaiorIdadeInvalidaException.class, () -> {
            ClienteDTO clienteDTO = new ClienteDTO();
            clienteDTO.setCpf("123.123.123-10");
            //cliente.setDataNascimento(LocalDate.parse("2020-01-01"));
            clienteService.cadastrar("", clienteDTO);
        });
    }
}