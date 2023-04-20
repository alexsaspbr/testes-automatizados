package br.com.ada.testeautomatizado.util;

import br.com.ada.testeautomatizado.exception.MaiorIdadeInvalidaException;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Component
public class ValidacaoMaiorIdade {

    public void isMaiorIdade(LocalDate dataNascimento) {
        if(ChronoUnit.YEARS.between(dataNascimento, LocalDate.now()) < 18L)
            throw new MaiorIdadeInvalidaException();
    }


}
