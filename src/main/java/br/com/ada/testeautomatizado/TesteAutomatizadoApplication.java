package br.com.ada.testeautomatizado;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Cliente API"))
public class TesteAutomatizadoApplication {

	public static void main(String[] args) {
		SpringApplication.run(TesteAutomatizadoApplication.class, args);
	}

}
