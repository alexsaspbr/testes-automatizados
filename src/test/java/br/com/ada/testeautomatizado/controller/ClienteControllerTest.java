package br.com.ada.testeautomatizado.controller;


import br.com.ada.testeautomatizado.model.Cliente;
import br.com.ada.testeautomatizado.service.ClienteService;
import br.com.ada.testeautomatizado.util.ValidacaoCPF;
import br.com.ada.testeautomatizado.util.ValidacaoMaiorIdade;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class ClienteControllerTest {

    @SpyBean
    private ClienteService clienteService;

    @MockBean
    private ValidacaoCPF validacaoCPF;

    @MockBean
    private ValidacaoMaiorIdade validacaoMaiorIdade;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Test
    void cadastrarSucesso() throws Exception {

        Cliente cliente = new Cliente();
        cliente.setCpf("123.456.789-12");
        cliente.setNome("alex");
        cliente.setDataNascimento(LocalDate.parse("2022-01-01"));

        String clienteString = mapper.writeValueAsString(cliente);

        Mockito.doCallRealMethod().when(validacaoCPF).isValid(Mockito.anyString());
        Mockito.doCallRealMethod().when(validacaoMaiorIdade).isMaiorIdade(Mockito.any(LocalDate.class));

        mockMvc.perform(post("/cadastrar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(clienteString)
                )
                .andExpect(status().isOk())
                .andExpect(content().string("SUCESSO"))
                .andDo(print());

    }


    /*@Test
    void cadastrarErro() {

        Cliente cliente = new Cliente();
        cliente.setCpf("123456789");
        cliente.setNome("alex");

        Mockito.when(clienteService.cadastrar(Mockito.any(Cliente.class))).thenThrow(new RuntimeException());

        Assertions.assertEquals(
                //ResponseEntity.status(HttpStatus.BAD_REQUEST).body("deu ruim"),
                //this.clienteController.cadastrar(cliente));

    }*/

}