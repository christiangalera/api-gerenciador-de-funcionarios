package com.gerenciafuncionarios.apirest.controllers;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.validation.Valid;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.gerenciafuncionarios.apirest.models.Funcionario;
import com.gerenciafuncionarios.apirest.services.FuncionarioService;



@RestController
@RequestMapping("/api")
@Api(value = "API REST Funcionarios")
@CrossOrigin(origins = "*", maxAge = 3600)
public class FuncionarioController {

	final FuncionarioService funcionarioService;

	public FuncionarioController(FuncionarioService funcionarioService) {
		this.funcionarioService = funcionarioService;
	}

	@PostMapping("/funcionario")
	@ApiOperation(value = "Salva um funcionario")
	public ResponseEntity<Object> saveFuncionario(@RequestBody @Valid Funcionario funcionario) {
		return ResponseEntity.status(HttpStatus.CREATED).body(funcionarioService.save(funcionario));
	}

	@GetMapping("/funcionarios")
	@ApiOperation(value = "Retorna todos funcionarios")
	public ResponseEntity<Page<Funcionario>> getAllFuncionarios(
			@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
		return ResponseEntity.status(HttpStatus.OK).body(funcionarioService.findAll(pageable));
	}

	@GetMapping("/funcionario/{id}")
	@ApiOperation(value = "Retorna um funcionario por id")
	public ResponseEntity<Object> getOneFuncionario(@PathVariable(value = "id") UUID id) {
		Optional<Funcionario> funcionarioOptional = funcionarioService.findById(id);
		        if (!funcionarioOptional.isPresent()) {
			            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Funcionario não encontrado.");
			        }
		        return ResponseEntity.status(HttpStatus.OK).body(funcionarioOptional.get());
	}

	@GetMapping("/funcionarios/cep/{cep}")
	@ApiOperation(value = "Retorna todos os funcionarios por cep")
	public ResponseEntity<Object> getAllFuncionariosByCep(@PathVariable(value = "cep") String cep) {
		List<Funcionario> funcionarioCepList = funcionarioService.findByCep(cep);
		        if (funcionarioCepList.isEmpty()) {
			            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("CEP do funcionario não encontrado.");
			        }
		        return ResponseEntity.status(HttpStatus.OK).body(funcionarioCepList);
	}

	@DeleteMapping("/funcionario/{id}")
	@ApiOperation(value = "Deleta um funcionario")
	public ResponseEntity<Object> deleteFuncionario(@PathVariable(value = "id") UUID id) {
		Optional<Funcionario> funcionarioOptional = funcionarioService.findById(id);
		        if (!funcionarioOptional.isPresent()) {
			            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Funcionario não encontrado.");
			        }
		        funcionarioService.delete(funcionarioOptional.get());
		        return ResponseEntity.status(HttpStatus.OK).body("Funcionario deletado com sucesso.");
	}

	@PutMapping("/funcionario/{id}")
	@ApiOperation(value = "Atualiza um funcionario")
	public ResponseEntity<Object> updateFuncionario(@PathVariable(value = "id") UUID id,
			@RequestBody @Valid Funcionario funcionario) {
		Optional<Funcionario> funcionarioOptional = funcionarioService.findById(id);
		        if (!funcionarioOptional.isPresent()) {
			            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Funcionario não encontrado.");
			        }
		        funcionario.setId(funcionarioOptional.get().getId());
		        return ResponseEntity.status(HttpStatus.OK).body(funcionarioService.save(funcionario));
	}

	@PatchMapping("/funcionario/{id}")
	@ApiOperation(value = "Atualiza um funcionario parcialmente")
   	 public ResponseEntity<Object> updateFuncionarioPartially(@PathVariable(value = "id") UUID id,
                                                    @RequestBody Funcionario funcionario){
		Optional<Funcionario> funcionarioOptional = funcionarioService.findById(id);
		if (!funcionarioOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Funcionario não encontrado.");
		}
		Funcionario funcionarioAtualizado = funcionarioOptional.get();
		funcionarioAtualizado = funcionarioService.updateFuncionarioPartially(funcionarioAtualizado, funcionario);
		return ResponseEntity.status(HttpStatus.OK).body(funcionarioAtualizado);
	}

}
