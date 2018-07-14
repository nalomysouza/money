package com.example.moneyapi.service;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.example.moneyapi.exception.PessoaInexistenteOuInativaException;
import com.example.moneyapi.model.Lancamento;
import com.example.moneyapi.model.Pessoa;
import com.example.moneyapi.repository.LancamentoRepository;
import com.example.moneyapi.repository.PessoaRepository;

@Service
public class LancamentoService {
	@Autowired
	private LancamentoRepository repository;
	@Autowired
	private PessoaRepository pessoaRepository;

	public Lancamento cadastrar(Lancamento lancamento) {
		Optional<Pessoa> pessoa = pessoaRepository.findById(lancamento.getPessoa().getId());

		if (!pessoa.isPresent() || pessoa.get().isInativo()) {
			throw new PessoaInexistenteOuInativaException();
		}
		return repository.save(lancamento);
	}

	public Lancamento atualizar(Long id, Lancamento lancamento) {
		Lancamento objeto = buscarPeloId(id);
		BeanUtils.copyProperties(lancamento, objeto, "id");
		return repository.save(objeto);
	}

	private Lancamento buscarPeloId(Long id) {
		Lancamento objeto = repository.getOne(id);
		if (objeto == null) {
			throw new EmptyResultDataAccessException(1);
		}
		return objeto;
	}
}
