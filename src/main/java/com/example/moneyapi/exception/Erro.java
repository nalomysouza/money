package com.example.moneyapi.exception;

import lombok.Getter;

/**
 * Classe interna responsável por mapear a mensagem e o problema que ocasionou o
 * erro.
 * 
 */

public final class Erro {
	@Getter
	private String mensagem;
	@Getter
	private String problema;

	public Erro(String mensagem, String problema) {
		this.mensagem = mensagem;
		this.problema = problema;
	}

}
