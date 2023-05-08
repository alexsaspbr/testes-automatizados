package br.com.ada.testeautomatizado.util;

import br.com.ada.testeautomatizado.exception.CPFValidationException;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class ValidacaoCPF {

    public void isValid(String cpf) {
        if (isNull(cpf) || noMatches(cpf))
            throw new CPFValidationException();
    }

    private boolean isNull(String cpf) {
        //System.out.println("Passei nonNull");
        return Objects.isNull(cpf);
    }

    private boolean noMatches(String cpf) {
        //System.out.println("Passei regex");
        return !cpf.matches("(\\d{3})(\\.)(\\d{3})(\\.)(\\d{3})(\\-)(\\d{2})");
    }

}
