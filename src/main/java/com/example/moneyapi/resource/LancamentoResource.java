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
import com.example.moneyapi.model.Lancamento;
import com.example.moneyapi.repository.LancamentoRepository;
import com.example.moneyapi.service.LancamentoService;

@RestController
@RequestMapping("/lancamentos")
public class LancamentoResource {
	@Autowired
	private LancamentoRepository lancamentoRepository;
	@Autowired
	private LancamentoService lancamentoService;
	@Autowired
	private ApplicationEventPublisher publisher;

	@GetMapping
	public List<Lancamento> listar() {
		return lancamentoRepository.findAll();
	}

	@GetMapping("/{id}")
	public Optional<Lancamento> buscarPeloId(@PathVariable Long id) {
		Optional<Lancamento> objetoBanco = lancamentoRepository.findById(id);
		if (!objetoBanco.isPresent()) {
			throw new EmptyResultDataAccessException(1);
		}
		return objetoBanco;
	}

	@PostMapping
	public ResponseEntity<Lancamento> cadastrar(@Valid @RequestBody Lancamento lancamento,
			HttpServletResponse response) {
		Lancamento objetoCadastrado = lancamentoService.cadastrar(lancamento);
		publisher.publishEvent(new RecursoCadastrarEvent(this, response, objetoCadastrado.getId()));
		return ResponseEntity.status(HttpStatus.CREATED).body(objetoCadastrado);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Lancamento> atualizar(@PathVariable Long id, @Valid @RequestBody Lancamento lancamento) {
		Lancamento objeto = lancamentoService.atualizar(id, lancamento);
		return ResponseEntity.ok(objeto);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id) {
		lancamentoRepository.deleteById(id);
	}
}
