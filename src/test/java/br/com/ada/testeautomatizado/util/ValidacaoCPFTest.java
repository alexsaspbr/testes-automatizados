package br.com.ada.testeautomatizado.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ValidacaoCPFTest {

    @Mock
    private ValidacaoCPF validacaoCPF;

    @ParameterizedTest
    @ValueSource(strings = {"123.456.789-78", "123.456.789-89", "123.456.789-51"})
    public void deveriaRetornarCPFValidadoComSucesso(String cpf) {
        Mockito.when(validacaoCPF.isValid(Mockito.anyString())).thenCallRealMethod();
        Assertions.assertTrue(validacaoCPF.isValid(cpf));

    }

    @Test
    public void deveriaRetornarValidacaoCPFNull(){
        Mockito.when(validacaoCPF.isValid(Mockito.nullable(String.class))).thenCallRealMethod();
        String cpf = null;
        Assertions.assertFalse(validacaoCPF.isValid(cpf));
    }

    @ParameterizedTest
    @ValueSource(strings = {"12345678978", "12345678989", "12345678951"})
    public void deveriaRetornarMascaraCPFInvalido(String cpf) {
        Mockito.when(validacaoCPF.isValid(Mockito.anyString())).thenCallRealMethod();
        Assertions.assertFalse(validacaoCPF.isValid(cpf));
    }

}