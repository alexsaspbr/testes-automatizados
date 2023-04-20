package br.com.ada.testeautomatizado.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ValidacaoMaiorIdadeTest {

    @Spy
    @InjectMocks
    ValidacaoMaiorIdade validacaoMaiorIdade;

    @ParameterizedTest
    @ValueSource(strings = {"1987-10-19", "1999-01-01"})
    void isMaiorIdadeSucesso(String dataNascimento) {

        LocalDate dataNascimentoLD = LocalDate.parse(dataNascimento);
        validacaoMaiorIdade.isMaiorIdade(dataNascimentoLD);
        Mockito.verify(validacaoMaiorIdade).isMaiorIdade(dataNascimentoLD);

    }
}