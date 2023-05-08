package br.com.ada.testeautomatizado.service;

import br.com.ada.testeautomatizado.dto.ClienteDTO;
import br.com.ada.testeautomatizado.dto.ResponseDTO;
import br.com.ada.testeautomatizado.exception.CPFValidationException;
import br.com.ada.testeautomatizado.exception.MaiorIdadeInvalidaException;
import br.com.ada.testeautomatizado.repository.ClienteRepository;
import br.com.ada.testeautomatizado.util.ValidacaoCPF;
import br.com.ada.testeautomatizado.util.ValidacaoMaiorIdade;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClienteServiceTest {

    @Spy
    @InjectMocks
    private ClienteService clienteService;

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private ValidacaoCPF validacaoCPF;

    @Mock
    private ValidacaoMaiorIdade validacaoMaiorIdade;

    @Test
    void cadastrarSucesso() {
        ClienteDTO clienteDTO = new ClienteDTO();
        clienteDTO.setCpf("123.123.123-12");
        clienteDTO.setDataNascimento(LocalDate.parse("2001-01-01"));
        doCallRealMethod().when(validacaoCPF).isValid(anyString());
        doCallRealMethod().when(validacaoMaiorIdade).isMaiorIdade(any(LocalDate.class));
        ResponseEntity<ResponseDTO<ClienteDTO>> response = clienteService.cadastrar("", clienteDTO);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(validacaoCPF, times(1)).isValid(clienteDTO.getCpf());
    }

    @Test
    void cadastrarCPFInvalido() {

        doCallRealMethod().when(validacaoCPF).isValid(anyString());

        ClienteDTO clienteDTO = new ClienteDTO();
        clienteDTO.setCpf("12312312310");
        clienteDTO.setDataNascimento(null);
        ResponseEntity<ResponseDTO<ClienteDTO>> response = clienteService.cadastrar("", clienteDTO);
        ResponseDTO<ClienteDTO> responseExpected = new ResponseDTO<>(new CPFValidationException().getMessage(), null);
        ResponseDTO<ClienteDTO> body = response.getBody();
        Assertions.assertEquals(responseExpected.getMessage(), body.getMessage());
        Assertions.assertEquals(responseExpected.getDetail(), body.getDetail());

    }

    @Test
    void cadastrarErroMaiorIdade() {

        doCallRealMethod().when(validacaoCPF).isValid(anyString());
        doCallRealMethod().when(validacaoMaiorIdade).isMaiorIdade(nullable(LocalDate.class));

        ClienteDTO clienteDTO = new ClienteDTO();
        clienteDTO.setCpf("123.123.123-10");
        clienteDTO.setDataNascimento(LocalDate.parse("2020-01-01"));
        ResponseEntity<ResponseDTO<ClienteDTO>> response = clienteService.cadastrar("", clienteDTO);
        ResponseDTO<ClienteDTO> responseExpected = new ResponseDTO<>(new MaiorIdadeInvalidaException().getMessage(), null);
        ResponseDTO<ClienteDTO> body = response.getBody();
        Assertions.assertEquals(responseExpected.getMessage(), body.getMessage());
        Assertions.assertEquals(responseExpected.getDetail(), body.getDetail());
    }
}