package com.gerenciafuncionarios.apirest.models;

import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

@Entity
@Data
@Table(name = "TB_FUNCIONARIO")
public class Funcionario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;

	@NotBlank(message = "O nome deve ser informado")
	@Column(nullable = false, length = 130)
	private String nome;

	@NotNull(message = "O salario deve ser informado")
	@Range(min = 1)
	@Column(nullable = false)
	private int idade;

	@NotBlank(message = "O CEP deve ser informado")
	@Length(min = 8, max = 8)
	@Column(nullable = false)
	private String cep;

	@Column
	private String sexo;

	@Column(length = 100)
	private String endereco;

	@Column(length = 100)
	private String bairro;

	@Column(length = 100)
	private String cidade;

	@Column(length = 100)
	private String estado;

	public void setSexo(String sexo) {
		switch (sexo) {
		case "Masculino":
			this.sexo = sexo;
			break;
		case "masculino":
			this.sexo = sexo;
			break;
		case "Feminino":
			this.sexo = sexo;
			break;
		case "feminino":
			this.sexo = sexo;
			break;
		default:
			this.sexo = "Masculino";
			break;
		}
	}
}
