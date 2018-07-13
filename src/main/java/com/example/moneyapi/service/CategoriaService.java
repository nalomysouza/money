package com.example.moneyapi.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.example.moneyapi.model.Categoria;
import com.example.moneyapi.repository.CategoriaRepository;

@Service
public class CategoriaService {
	@Autowired
	private CategoriaRepository repository;

	public Categoria atualizar(Long id, Categoria categoria) {
		Categoria objeto = buscarPeloId(id);
		BeanUtils.copyProperties(categoria, objeto, "id");
		return repository.save(objeto);
	}

	private Categoria buscarPeloId(Long id) {
		Categoria objeto = repository.getOne(id);
		if (objeto == null) {
			throw new EmptyResultDataAccessException(1);
		}
		return objeto;
	}

}
