package br.com.ada.testeautomatizado.exception;

public class ClienteNotFoundException extends RuntimeException {

    public ClienteNotFoundException() {
        super("Cliente não encontrado");
    }

}
