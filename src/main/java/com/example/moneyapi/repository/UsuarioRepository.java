package com.example.moneyapi.repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.example.moneyapi.model.IdentificadorAutorizacao;
import com.example.moneyapi.model.Usuario;

import java.util.Optional;

@Repository
@Transactional
public class UsuarioRepository {

	@PersistenceContext
	private EntityManager em;

	public Usuario registrar(Usuario usuario) {
		em.persist(usuario);

		return usuario;
	}

	public Optional<Usuario> buscarUsuarioAutenticado(IdentificadorAutorizacao identificadorDeAutorizacao) {

		TypedQuery<Usuario> query = em.createQuery(
				"select u from Usuario u join u.autenticacaoOpenid a where a.id.valor = :authn_id", Usuario.class);

		query.setParameter("authn_id", identificadorDeAutorizacao.getValor());

		try {
			return Optional.of(query.getSingleResult());
		} catch (NoResultException e) {
			return Optional.empty();
		}

	}

}
