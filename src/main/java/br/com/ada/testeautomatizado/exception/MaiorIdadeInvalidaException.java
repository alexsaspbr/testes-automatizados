package br.com.ada.testeautomatizado.exception;

public class MaiorIdadeInvalidaException extends RuntimeException {

    public MaiorIdadeInvalidaException(){
        super("Não tem maior idade");
    }

}
