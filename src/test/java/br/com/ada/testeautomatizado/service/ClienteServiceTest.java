package br.com.ada.testeautomatizado.service;

import br.com.ada.testeautomatizado.model.Cliente;
import br.com.ada.testeautomatizado.util.ValidacaoCPF;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;

class ClienteServiceTest {

    private ClienteService clienteService;

    @Mock
    private ValidacaoCPF validacaoCPF;

    @Test
    void cadastrarSucesso() {
        Cliente cliente = new Cliente();
        cliente.setCpf("12345612345");
        Assertions.assertEquals(clienteService.cadastrar(cliente), "SUCESSO");
    }

    @Test
    void cadastrarErro() {
        Assertions.assertThrows(RuntimeException.class, () -> {
            Cliente cliente = new Cliente();
            cliente.setCpf("123546");
            clienteService.cadastrar(cliente);
        });
    }
}