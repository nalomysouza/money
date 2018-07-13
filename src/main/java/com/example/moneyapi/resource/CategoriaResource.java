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
import com.example.moneyapi.model.Categoria;
import com.example.moneyapi.repository.CategoriaRepository;
import com.example.moneyapi.service.CategoriaService;

@RestController
@RequestMapping("/categorias")
public class CategoriaResource {
	@Autowired
	private CategoriaRepository categoriaRepository;
	@Autowired
	private CategoriaService categoriaService;
	@Autowired
	private ApplicationEventPublisher publisher;

	@GetMapping
	public List<Categoria> listar() {
		return categoriaRepository.findAll();
	}

	@GetMapping("/{id}")
	public Optional<Categoria> buscarPeloId(@PathVariable Long id) {
		Optional<Categoria> objetoBanco = categoriaRepository.findById(id);
		if (!objetoBanco.isPresent()) {
			throw new EmptyResultDataAccessException(1);
		}
		return objetoBanco;
	}

	@PostMapping
	public ResponseEntity<Categoria> cadastrar(@Valid @RequestBody Categoria categoria, HttpServletResponse response) {
		Categoria objetoCadastrado = categoriaRepository.save(categoria);
		publisher.publishEvent(new RecursoCadastrarEvent(this, response, objetoCadastrado.getId()));
		return ResponseEntity.status(HttpStatus.CREATED).body(objetoCadastrado);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Categoria> atualizar(@PathVariable Long id, @Valid @RequestBody Categoria categoria) {
		Categoria objetoAtualizado = categoriaService.atualizar(id, categoria);
		return ResponseEntity.ok(objetoAtualizado);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id) {
		categoriaRepository.deleteById(id);
	}
}
