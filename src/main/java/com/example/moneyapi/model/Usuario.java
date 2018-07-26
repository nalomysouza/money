package com.example.moneyapi.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import lombok.Getter;

@Entity
public class Usuario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Getter
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(updatable = false, nullable = false)
	private Integer id;

	@Getter
	private String nome;

	@Getter
	private String email;

	@Getter
	@OneToOne(mappedBy = "usuario", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private AutenticacaoOpenid autenticacaoOpenid;

	@Deprecated
	Usuario() {
		this.nome = "Anonimo";
	}

	public Usuario(String nome, String email) {
		this.nome = nome;
		this.email = email;
	}

	public void alterarNome(String nome) {
		this.nome = nome;
	}

	public void autenticar(AutenticacaoOpenid autenticacaoOpenid) {
		this.autenticacaoOpenid = autenticacaoOpenid;
	}
}
