package br.com.ada.testeautomatizado.exception;

public class CPFValidationException extends RuntimeException {

    public CPFValidationException(){
        super("CPF inválido!");
    }

}
