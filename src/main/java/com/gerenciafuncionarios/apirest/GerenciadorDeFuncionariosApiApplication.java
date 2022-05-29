package com.gerenciafuncionarios.apirest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class GerenciadorDeFuncionariosApiApplication {
	
	@Bean
	public WebClient webClientCep(WebClient.Builder builder) {
		return builder
				.baseUrl("https://viacep.com.br/ws/{cep}/json")
				.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
				.build();
	}

	public static void main(String[] args) {
		SpringApplication.run(GerenciadorDeFuncionariosApiApplication.class, args);
	}

}




