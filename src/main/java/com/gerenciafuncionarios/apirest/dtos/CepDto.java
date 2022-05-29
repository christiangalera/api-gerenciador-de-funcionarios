package com.gerenciafuncionarios.apirest.dtos;

import lombok.Data;

@Data
public class CepDto {
	private String logradouro;

	private String bairro;

	private String localidade;

	private String uf;
}