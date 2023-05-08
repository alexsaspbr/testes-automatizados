package br.com.ada.testeautomatizado.controller;


import br.com.ada.testeautomatizado.dto.ClienteDTO;
import br.com.ada.testeautomatizado.dto.ResponseDTO;
import br.com.ada.testeautomatizado.service.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @Operation(summary = "Criar cliente", tags = "cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "422", description = "Bad Request",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = ResponseDTO.class))
                    }
            )

    })
    @PostMapping("/cadastrar")
    public ResponseEntity<ResponseDTO<ClienteDTO>> cadastrar(@RequestHeader("correlation-id") String correlationId, @RequestBody ClienteDTO clienteDTO) {
        log.debug("Executando cadastrar no ClienteController");
        return this.clienteService.cadastrar(correlationId, clienteDTO);
    }

    @Operation(summary = "Remover cliente", tags = "cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "422", description = "Bad Request",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = String.class))
                    }
            )

    })
    @DeleteMapping("/deletarClientePor/{cpf}")
    public ResponseEntity<String> deletarClientePorCPF(@PathVariable("cpf") String cpf) {
        boolean isDelete = this.clienteService.deletarClientePorCPF(cpf);
        if(isDelete)
            return ResponseEntity.ok("SUCESSO");
        else
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(summary = "Atualizar cliente", tags = "cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "422", description = "Bad Request",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = ResponseDTO.class))
                    }
            )

    })
    @PutMapping("/atualizar")
    public ResponseEntity<ResponseDTO<ClienteDTO>> atualizar(@RequestBody ClienteDTO clienteDTO) {
        return this.clienteService.atualizar(clienteDTO);
    }

    @Operation(summary = "Listar todos clientes", tags = "cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "422", description = "Bad Request",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = ResponseDTO.class))
                    }
            )

    })
    @GetMapping("/listarTodos")
    public ResponseEntity<ResponseDTO<List<ClienteDTO>>> todos(){
        return this.clienteService.todos();
    }

}
