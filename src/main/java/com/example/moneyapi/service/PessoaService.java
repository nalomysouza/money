package com.example.moneyapi.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.example.moneyapi.model.Pessoa;
import com.example.moneyapi.repository.PessoaRepository;

@Service
public class PessoaService {
	@Autowired
	private PessoaRepository repository;

	public Pessoa atualizar(Long id, Pessoa pessoa) {
		Pessoa objeto = buscarPeloId(id);
		BeanUtils.copyProperties(pessoa, objeto, "id");
		return repository.save(objeto);
	}

	public Pessoa atualizarPropriedadeAtivo(Long id, Boolean ativo) {
		Pessoa objeto = buscarPeloId(id);
		objeto.setAtivo(ativo);
		return repository.save(objeto);
	}

	private Pessoa buscarPeloId(Long id) {
		Pessoa objeto = repository.getOne(id);
		if (objeto == null) {
			throw new EmptyResultDataAccessException(1);
		}
		return objeto;
	}

}
