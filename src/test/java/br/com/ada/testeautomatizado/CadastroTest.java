package br.com.ada.testeautomatizado;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CadastroTest {

    @Test
    void cadastrarSucesso() {
        Cliente cliente = new Cliente();
        cliente.setCpf("12345612345");

        ClienteService cadastro = new ClienteService();
        Assertions.assertEquals(cadastro.cadastrar(cliente), "SUCESSO");
    }

    @Test
    void cadastrarErro() {
        Assertions.assertThrows(RuntimeException.class, () -> {
            ClienteService cadastro = new ClienteService();
            Cliente cliente = new Cliente();
            cliente.setCpf("123546");
            cadastro.cadastrar(cliente);
        });
    }
}