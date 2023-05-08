package br.com.ada.testeautomatizado.controller;


import br.com.ada.testeautomatizado.dto.ClienteDTO;
import br.com.ada.testeautomatizado.dto.ResponseDTO;
import br.com.ada.testeautomatizado.exception.CPFValidationException;
import br.com.ada.testeautomatizado.model.Cliente;
import br.com.ada.testeautomatizado.repository.ClienteRepository;
import br.com.ada.testeautomatizado.service.ClienteService;
import br.com.ada.testeautomatizado.util.ClienteDTOConverter;
import br.com.ada.testeautomatizado.util.ValidacaoCPF;
import br.com.ada.testeautomatizado.util.ValidacaoMaiorIdade;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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

    @MockBean
    private ClienteDTOConverter clienteDTOConverter;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;


    @Test
    @DisplayName("Retorna a lista com todos os clientes")
    public void deveriaRetornarTodosClientes() throws Exception {

        Mockito.when(this.clienteRepository.findAll()).thenReturn(List.of(clienteBD()));

        MvcResult mvcResult = mockMvc.perform(get("/listarTodos")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        ClienteDTO clienteDTO = clienteDTO();
        clienteDTO.setDataNascimento(clienteBD().getDataNascimento());
        ResponseDTO<List<ClienteDTO>> listResponseDTO = new ResponseDTO<>("Sucesso", List.of(clienteDTO));
        String responseExpected = mapper.writeValueAsString(listResponseDTO);

        Assertions.assertEquals(responseExpected, mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8));

    }

    @Test
    @DisplayName("Cadastrar um cliente com sucesso")
    void deveriaCadastrarClienteSucesso() throws Exception {

        String clienteString = mapper.writeValueAsString(clienteDTO());

        Mockito.doCallRealMethod().when(validacaoCPF).isValid(Mockito.anyString());
        Mockito.doCallRealMethod().when(validacaoMaiorIdade).isMaiorIdade(Mockito.any(LocalDate.class));

        MvcResult mvcResult = mockMvc.perform(post("/cadastrar")
                        .header("correlation-id", UUID.randomUUID().toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(clienteString)
                )
                .andExpect(status().isCreated())
                .andDo(print())
                .andReturn();

        String resultActual = mvcResult.getResponse().getContentAsString();
        ResponseDTO<ClienteDTO> responseDTO = new ResponseDTO<>("Sucesso", clienteDTO());
        String responseString = mapper.writeValueAsString(responseDTO);

        Assertions.assertEquals(responseString, resultActual);

    }


    @Test
    @DisplayName("Retorna erro de CPF invalido")
    void deveriaRetornarErroCPFInvalido() throws Exception {

        ClienteDTO clienteDTO = clienteDTO();
        clienteDTO.setCpf("12345678912");
        String clienteString = mapper.writeValueAsString(clienteDTO);

        Mockito.doCallRealMethod().when(validacaoCPF).isValid(Mockito.anyString());

        MvcResult mvcResult = mockMvc.perform(post("/cadastrar")
                        .header("correlation-id", UUID.randomUUID().toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(clienteString)
                )
                .andExpect(status().isUnprocessableEntity())
                .andDo(print())
                .andReturn();

        String responseExpected = mapper.writeValueAsString(new ResponseDTO<ClienteDTO>(new CPFValidationException().getMessage(), null));

        Assertions.assertEquals(responseExpected, mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8));

    }

    @Test
    @DisplayName("Retorna erro de maior idade")
    void deveriaRetornarErroMenorDeIdade() throws Exception {

        ClienteDTO clienteDTO = clienteDTO();
        clienteDTO.setDataNascimento(LocalDate.parse("2022-10-19"));
        String clienteString = mapper.writeValueAsString(clienteDTO);

        Mockito.doCallRealMethod().when(validacaoCPF).isValid(Mockito.anyString());
        Mockito.doCallRealMethod().when(validacaoMaiorIdade).isMaiorIdade(Mockito.any(LocalDate.class));

        MvcResult mvcResult = mockMvc.perform(post("/cadastrar")
                        .header("correlation-id", UUID.randomUUID().toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(clienteString)
                )
                .andExpect(status().isUnprocessableEntity())
                .andDo(print())
                .andReturn();

        String responseExpected = mapper.writeValueAsString(new ResponseDTO<ClienteDTO>("Não tem maior idade", null));

        Assertions.assertEquals(responseExpected, mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8));

    }

    @Test
    @DisplayName("Retorna o erro interno de sistema")
    void deveriaRetornarErroInternoDoSistema() throws Exception {

        String clienteString = mapper.writeValueAsString(clienteDTO());

        Mockito.doCallRealMethod().when(validacaoCPF).isValid(Mockito.anyString());
        Mockito.doCallRealMethod().when(validacaoMaiorIdade).isMaiorIdade(Mockito.any(LocalDate.class));

        Mockito.when(this.clienteRepository.save(Mockito.any(Cliente.class))).thenThrow(RuntimeException.class);

        mockMvc.perform(post("/cadastrar")
                        .header("correlation-id", UUID.randomUUID().toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(clienteString)
                )
                .andExpect(status().isInternalServerError())
                .andDo(print());

    }

    @Test
    @DisplayName("Deletar cliente pelo cpf")
    void deleteClienteSucesso() throws Exception {

        Mockito.when(clienteRepository.findByCpf(Mockito.anyString())).thenReturn(Optional.of(clienteBD()));

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

    @Test
    @DisplayName("Retorna atualizar um cliente")
    public void deveriaAtualizarClienteSucesso() throws Exception {

        String clienteString = mapper.writeValueAsString(clienteDTO());

        Mockito.doCallRealMethod().when(validacaoCPF).isValid(Mockito.anyString());
        Mockito.doCallRealMethod().when(validacaoMaiorIdade).isMaiorIdade(Mockito.any(LocalDate.class));
        Mockito.when(clienteRepository.findByCpf(Mockito.anyString())).thenReturn(Optional.of(clienteBD()));
        Cliente clienteAtualizadoBD = clienteBD();
        clienteAtualizadoBD.setNome("Alex Araujo");
        Mockito.when(clienteRepository.save(Mockito.any(Cliente.class))).thenReturn(clienteAtualizadoBD);
        Mockito.when(clienteDTOConverter.convertFrom(Mockito.any(Cliente.class))).thenReturn(clienteDTOAtualizado());

        MvcResult mvcResult = mockMvc.perform(put("/atualizar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(clienteString))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        String result = mvcResult.getResponse().getContentAsString();

        ResponseDTO<ClienteDTO> responseDTO = new ResponseDTO<ClienteDTO>("Sucesso", clienteDTOAtualizado());
        String resultExpect = mapper.writeValueAsString(responseDTO);

        Assertions.assertEquals(resultExpect, result);

    }

    @Test
    @DisplayName("Retorna No Content ao atualizar um cliente que não existe na base de dados")
    public void deveriaRetornarNoContentAoAtualizarCliente() throws Exception {

        ClienteDTO clienteDTO = clienteDTO();
        clienteDTO.setCpf("456.123.789-98");
        String body = mapper.writeValueAsString(clienteDTO);

        mockMvc.perform(put("/atualizar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isNoContent())
                .andDo(print());

    }


    private static ClienteDTO clienteDTO(){
        ClienteDTO clienteDTO = new ClienteDTO();
        clienteDTO.setCpf("123.456.789-12");
        clienteDTO.setNome("Alex Gomes");
        clienteDTO.setDataNascimento(LocalDate.parse("2001-01-01"));
        return clienteDTO;
    }

    private static ClienteDTO clienteDTOAtualizado(){
        ClienteDTO clienteDTO = new ClienteDTO();
        clienteDTO.setCpf("123.456.789-12");
        clienteDTO.setNome("Alex Araujo");
        clienteDTO.setDataNascimento(LocalDate.parse("2022-01-01"));
        return clienteDTO;
    }

    private static Cliente clienteBD() {
        Cliente cliente = new Cliente();
        cliente.setId(1L);
        cliente.setCpf("123.456.789-12");
        cliente.setNome("Alex Gomes");
        cliente.setDataNascimento(LocalDate.parse("2022-01-01"));
        return cliente;
    }

}