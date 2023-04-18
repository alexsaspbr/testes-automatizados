package br.com.ada.testeautomatizado.service;

import br.com.ada.testeautomatizado.exception.CPFValidationException;
import br.com.ada.testeautomatizado.model.Cliente;
import br.com.ada.testeautomatizado.util.ValidacaoCPF;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ClienteServiceTest {

    @InjectMocks
    private ClienteService clienteService;

    @Mock
    private ValidacaoCPF validacaoCPF;

    @Test
    void cadastrarSucesso() {
        Cliente cliente = new Cliente();
        cliente.setCpf("12345612345");
        Mockito.when(validacaoCPF.isValid(Mockito.anyString())).thenCallRealMethod();
        Assertions.assertEquals(clienteService.cadastrar(cliente), "SUCESSO");
    }

    @Test
    void cadastrarErro() {

        Mockito.when(validacaoCPF.isValid(Mockito.anyString())).thenThrow(CPFValidationException.class);

        Assertions.assertThrows(CPFValidationException.class, () -> {
            Cliente cliente = new Cliente();
            cliente.setCpf("123546");
            clienteService.cadastrar(cliente);
        });
    }
}