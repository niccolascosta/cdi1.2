package br.com.alura.livraria.dao;

import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import br.com.alura.livraria.modelo.Usuario;
import br.com.dao.dao_lib.jpa.annotation.Query;

public class UsuarioDao {
	
	@Inject
	@Query("SELECT u FROM Usuario u WHERE u.email = :pEmail AND u.senha = :pSenha")
	private TypedQuery<Usuario> query;

	public boolean existe(Usuario usuario) {
		query.setParameter("pEmail", usuario.getEmail());
		query.setParameter("pSenha", usuario.getSenha());
		try {
			query.getSingleResult();
		} catch (NoResultException ex) {
			return false;
		}
		return true;
	}

}
