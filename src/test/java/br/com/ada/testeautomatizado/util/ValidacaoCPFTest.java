package br.com.ada.testeautomatizado.util;

import br.com.ada.testeautomatizado.exception.CPFValidationException;
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
        Mockito.doCallRealMethod().when(validacaoCPF).isValid(Mockito.anyString());
        validacaoCPF.isValid(cpf);
        Mockito.verify(validacaoCPF).isValid(cpf);
    }

    @Test
    public void deveriaRetornarValidacaoCPFNull(){
        Mockito.doCallRealMethod().when(validacaoCPF).isValid(Mockito.nullable(String.class));
        String cpf = null;
        Assertions.assertThrows(CPFValidationException.class,  () -> {
            validacaoCPF.isValid(cpf);
        });
    }

    @ParameterizedTest
    @ValueSource(strings = {"12345678978", "12345678989", "12345678951"})
    public void deveriaRetornarMascaraCPFInvalido(String cpf) {
        Mockito.doCallRealMethod().when(validacaoCPF).isValid(Mockito.anyString());
        Assertions.assertThrows(CPFValidationException.class,  () -> {
            validacaoCPF.isValid(cpf);
        });
    }

}