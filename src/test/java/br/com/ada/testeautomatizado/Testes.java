package br.com.ada.testeautomatizado;

import br.com.ada.testeautomatizado.model.Cliente;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.internal.matchers.Null;

public class Testes {

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

    @Test
    public void test3(){
        System.out.println("Teste 3");
        cliente = new Cliente();
        NullPointerException exception = Assertions.assertThrows(NullPointerException.class, () -> {
            System.out.println(cliente.getCpf().toUpperCase());
        });
        Assertions.assertEquals(exception.getMessage(), "Cannot invoke \"String.toUpperCase()\" because the return value of \"br.com.ada.testeautomatizado.model.Cliente.getCpf()\" is null");
    }

    @ParameterizedTest
    @ValueSource(ints = {3, 5, 7, 9, 11})
    public void deveriaSerImpar(int num) {
        Numero numero = new Numero();
        Assertions.assertTrue(numero.isImpar(num));
    }

}

class Numero {

    public boolean isImpar(Integer numero) {
        return numero % 2 != 0;
    }

}
