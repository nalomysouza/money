package com.example.moneyapi.model;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class IdentificadorAutorizacao implements Serializable {
	private static final long serialVersionUID = 5764680109372820279L;
	@Getter
	@Column(name = "authn_id")
	private String valor;

	IdentificadorAutorizacao() {
	}

	public IdentificadorAutorizacao(String valor) {
		this.valor = valor;
	}

}
