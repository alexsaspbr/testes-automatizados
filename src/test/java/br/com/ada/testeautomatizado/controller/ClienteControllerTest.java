package br.com.ada.testeautomatizado.controller;


import br.com.ada.testeautomatizado.model.Cliente;
import br.com.ada.testeautomatizado.service.ClienteService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
class ClienteControllerTest {

    @InjectMocks
    private ClienteController clienteController;

    @Mock
    private ClienteService clienteService;

    @Test
    void cadastrarSucesso() {

        Cliente cliente = new Cliente();
        cliente.setCpf("123456789");
        cliente.setNome("alex");

        Mockito.when(clienteService.cadastrar(Mockito.any(Cliente.class))).thenReturn("SUCESSO");

        Assertions.assertEquals(
                ResponseEntity.ok("SUCESSO"),
                this.clienteController.cadastrar(cliente));

    }


    @Test
    void cadastrarErro() {

        Cliente cliente = new Cliente();
        cliente.setCpf("123456789");
        cliente.setNome("alex");

        Mockito.when(clienteService.cadastrar(Mockito.any(Cliente.class))).thenThrow(new RuntimeException());

        Assertions.assertEquals(
                ResponseEntity.status(HttpStatus.BAD_REQUEST).body("deu ruim"),
                this.clienteController.cadastrar(cliente));

    }

}