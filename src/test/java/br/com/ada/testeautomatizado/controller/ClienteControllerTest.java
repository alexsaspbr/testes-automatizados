package br.com.ada.testeautomatizado.controller;


import br.com.ada.testeautomatizado.model.Cliente;
import br.com.ada.testeautomatizado.service.ClienteService;
import br.com.ada.testeautomatizado.util.ValidacaoCPF;
import br.com.ada.testeautomatizado.util.ValidacaoMaiorIdade;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.GsonJsonParser;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockRequestDispatcher;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
class ClienteControllerTest {

    @MockBean(answer = Answers.CALLS_REAL_METHODS)
    private ClienteService clienteService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Test
    void cadastrarSucesso() throws Exception {

        Cliente cliente = new Cliente();
        cliente.setCpf("123.456.789-12");
        cliente.setNome("alex");
        cliente.setDataNascimento("2000-01-01");

        String clienteString = mapper.writeValueAsString(cliente);

        Mockito.when(clienteService.cadastrar(Mockito.any())).thenCallRealMethod();

        mockMvc.perform(post("/cadastrar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(clienteString)
                )
                .andExpect(status().isOk())
                //.andExpect("SUCESSO")
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