package br.com.ada.testeautomatizado;

import org.junit.jupiter.api.*;

public class Testes2 {

    private Cliente cliente;

    @BeforeAll
    public static void inicio(){
        System.out.println("Start");
    }

    @BeforeEach
    public void init(){
        System.out.println("Iniciando...");
        cliente = new Cliente();
        cliente.setCpf("12345678945");
        cliente.setNome("Alex Araujo");
    }

    @AfterEach
    public void end(){
        System.out.println("Executou!");
    }

    @AfterAll
    public static void ending(){
        System.out.println("Ending");
    }

    @Test
    public void test1(){
        System.out.println("Teste 1");
        cliente = null;
        Assertions.assertNull(cliente);
    }

    @Test
    public void test2(){
        System.out.println("Teste 2");
        Assertions.assertNotNull(cliente.getCpf());
        Assertions.assertNotNull(cliente.getNome());
    }

}
