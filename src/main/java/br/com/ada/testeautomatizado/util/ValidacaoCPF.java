package br.com.ada.testeautomatizado.util;

import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class ValidacaoCPF {

    public boolean isValid(String cpf) {
        return nonNull(cpf) && regex(cpf);
    }

    private boolean nonNull(String cpf) {
        System.out.println("Passei nonNull");
        return Objects.nonNull(cpf);
    }

    private boolean regex(String cpf) {
        System.out.println("Passei regex");
        return cpf.matches("(\\d{3})(\\.)(\\d{3})(\\.)(\\d{3})(\\-)(\\d{2})");
    }


}
