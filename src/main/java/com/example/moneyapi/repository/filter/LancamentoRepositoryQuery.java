package com.example.moneyapi.repository.filter;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.moneyapi.model.Lancamento;

public interface LancamentoRepositoryQuery {

	public Page<Lancamento> filtrar(LancamentoFilter lancamentoFilter, Pageable pageable);

}