package br.com.ada.testeautomatizado.util;

import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class ValidacaoMaiorIdade {

    public Boolean isMaiorIdade(LocalDate dataNascimento){
        return LocalDate.now().minusYears(dataNascimento.getYear()).getYear() >= 18L;
    }


}
