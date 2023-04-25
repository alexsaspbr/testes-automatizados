package br.com.ada.testeautomatizado.controller;


import br.com.ada.testeautomatizado.dto.ClienteDTO;
import br.com.ada.testeautomatizado.model.Cliente;
import br.com.ada.testeautomatizado.repository.ClienteRepository;
import br.com.ada.testeautomatizado.service.ClienteService;
import br.com.ada.testeautomatizado.util.ValidacaoCPF;
import br.com.ada.testeautomatizado.util.ValidacaoMaiorIdade;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
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


    @MockBean
    private ClienteRepository clienteRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Test
    void cadastrarSucesso() throws Exception {

        ClienteDTO clienteDTO = new ClienteDTO();
        clienteDTO.setCpf("123.456.789-12");
        clienteDTO.setNome("alex");
        clienteDTO.setDataNascimento(LocalDate.parse("2022-01-01"));

        String clienteString = mapper.writeValueAsString(clienteDTO);

        Mockito.doCallRealMethod().when(validacaoCPF).isValid(Mockito.anyString());
        Mockito.doCallRealMethod().when(validacaoMaiorIdade).isMaiorIdade(Mockito.any(LocalDate.class));

        MvcResult mvcResult = mockMvc.perform(post("/cadastrar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(clienteString)
                )
                .andExpect(status().isOk())
                //.andExpect(content().string("SUCESSO"))
                .andDo(print())
                .andReturn();

        //mvcResult.getResponse().getContentAsString("SUCESSO")

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


    @Test
    @DisplayName("Deletar cliente pelo cpf")
    void deleteClienteSucesso() throws Exception {

        Cliente cliente = new Cliente();
        cliente.setId(1L);
        cliente.setCpf("123.456.789-12");
        cliente.setNome("alex");
        cliente.setDataNascimento(LocalDate.parse("2022-01-01"));

        Mockito.when(clienteRepository.findByCpf(Mockito.anyString())).thenReturn(Optional.of(cliente));

        mockMvc.perform(delete("/deletarClientePor/{cpf}", "12345678948")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                //.andExpect(content().string("SUCESSO"))
                .andDo(print());

    }

    @Test
    @DisplayName("Retorna todos os clientes")
    public void deveriaListarClientesSucesso(){

    }

}