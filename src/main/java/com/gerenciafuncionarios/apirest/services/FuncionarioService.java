package com.gerenciafuncionarios.apirest.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.client.WebClient;

import com.gerenciafuncionarios.apirest.dtos.CepDto;
import com.gerenciafuncionarios.apirest.models.Funcionario;
import com.gerenciafuncionarios.apirest.repositories.FuncionarioRepository;

import reactor.core.publisher.Mono;

@Service
public class FuncionarioService {

	@Autowired
	WebClient webClientCep;

	FuncionarioRepository funcionarioRepository;

	public FuncionarioService(FuncionarioRepository funcionarioRepository) {
		this.funcionarioRepository = funcionarioRepository;
	}

	public CepDto getInformationByCep(String cep) {
		Mono<CepDto> monoCep = this.webClientCep.method(HttpMethod.GET).uri("https://viacep.com.br/ws/{cep}/json", cep)
				.retrieve().bodyToMono(CepDto.class);
		CepDto cepDto = monoCep.block();
		return cepDto;
	}

	public Funcionario FillInformationByCep(Funcionario funcionario) {
		CepDto cepdto = getInformationByCep(funcionario.getCep());

		funcionario.setEndereco(cepdto.getLogradouro());
		funcionario.setBairro(cepdto.getBairro());
		funcionario.setCidade(cepdto.getLocalidade());
		funcionario.setEstado(cepdto.getUf());

		return funcionario;
	}

	@Transactional
	public Funcionario save(Funcionario funcionario) {
		return funcionarioRepository.save(funcionario);
	}

	public Page<Funcionario> findAll(Pageable pageable) {
		 return funcionarioRepository.findAll(pageable);
	}


	public Optional<Funcionario> findById(UUID id) {
		return funcionarioRepository.findById(id);
	}

	public List<Funcionario> findByCep(String cep) {
		return funcionarioRepository.findByCep(cep);
	}

	@Transactional
	public void delete(Funcionario funcionario) {
		funcionarioRepository.delete(funcionario);
	}

	@Transactional
	public Funcionario updateFuncionarioPartially(Funcionario funcionarioAtualizado, Funcionario funcionario) {

		if (StringUtils.hasLength(funcionario.getNome())) {
			funcionarioAtualizado.setNome(funcionario.getNome());
		}

		if (funcionario.getIdade() > 0) {
			funcionarioAtualizado.setIdade(funcionario.getIdade());
		}

		if (StringUtils.hasLength(funcionario.getCep())) {
			funcionarioAtualizado.setCep(funcionario.getCep());
			funcionarioAtualizado = FillInformationByCep(funcionarioAtualizado);
		}

		if (StringUtils.hasLength(funcionario.getSexo())) {
			funcionarioAtualizado.setSexo(funcionario.getSexo());
		}

		if (StringUtils.hasLength(funcionario.getEndereco())) {
			funcionarioAtualizado.setEndereco(funcionario.getEndereco());
		}

		if (StringUtils.hasLength(funcionario.getBairro())) {
			funcionarioAtualizado.setBairro(funcionario.getBairro());
		}

		if (StringUtils.hasLength(funcionario.getCidade())) {
			funcionarioAtualizado.setCidade(funcionario.getCidade());
		}

		if (StringUtils.hasLength(funcionario.getEstado())) {
			funcionarioAtualizado.setEstado(funcionario.getEstado());
		}

		return funcionarioAtualizado;
	}

}
