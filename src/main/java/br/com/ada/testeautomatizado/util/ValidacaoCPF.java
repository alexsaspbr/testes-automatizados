package br.com.ada.testeautomatizado.util;

import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class ValidacaoCPF {

    public boolean isValid(String cpf) {
        return Objects.nonNull(cpf) && cpf.length() >= 11;
    }

}
