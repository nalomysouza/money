package com.example.moneyapi.resource;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.moneyapi.event.RecursoCadastrarEvent;
import com.example.moneyapi.model.Pessoa;
import com.example.moneyapi.repository.PessoaRepository;
import com.example.moneyapi.service.PessoaService;

@RestController
@RequestMapping("/pessoas")
public class PessoaResource {
	@Autowired
	private PessoaRepository pessoaRepository;
	@Autowired
	private PessoaService pessoaService;
	@Autowired
	private ApplicationEventPublisher publisher;

	@GetMapping
	public List<Pessoa> listar() {
		return pessoaRepository.findAll();
	}

	@GetMapping("/{id}")
	public Optional<Pessoa> buscarPeloId(@PathVariable Long id) {
		Optional<Pessoa> objetoBanco = pessoaRepository.findById(id);
		if (!objetoBanco.isPresent()) {
			throw new EmptyResultDataAccessException(1);
		}
		return objetoBanco;
	}

	@PostMapping
	public ResponseEntity<Pessoa> cadastrar(@Valid @RequestBody Pessoa pessoa, HttpServletResponse response) {
		Pessoa objetoCadastrado = pessoaRepository.save(pessoa);
		publisher.publishEvent(new RecursoCadastrarEvent(this, response, objetoCadastrado.getId()));
		return ResponseEntity.status(HttpStatus.CREATED).body(objetoCadastrado);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Pessoa> atualizar(@PathVariable Long id, @Valid @RequestBody Pessoa pessoa) {
		Pessoa objetoAtualizado = pessoaService.atualizar(id, pessoa);
		return ResponseEntity.ok(objetoAtualizado);
	}

	@PutMapping("/{id}/ativo")
	public ResponseEntity<Pessoa> atualizarPropriedadeAtivo(@PathVariable Long id, @Valid @RequestBody Boolean ativo) {
		Pessoa objetoAtualizado = pessoaService.atualizarPropriedadeAtivo(id, ativo);
		return ResponseEntity.ok(objetoAtualizado);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id) {
		pessoaRepository.deleteById(id);
	}

}
