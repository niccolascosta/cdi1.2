package br.com.alura.livraria.bean;

import java.io.Serializable;
import java.util.Map;

import javax.enterprise.inject.Model;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;

import br.com.alura.livraria.dao.UsuarioDao;
import br.com.alura.livraria.modelo.Usuario;
import br.com.dao.dao_lib.helper.MessageHelper;
import br.com.dao.dao_lib.jsf.annotation.ScopeMap;
import br.com.dao.dao_lib.jsf.annotation.ScopeMap.Scope;

@Model
public class LoginBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private final Usuario usuario = new Usuario();

	private final UsuarioDao usuarioDao;

	private final MessageHelper helper;

	private final Map<String, Object> sessionMap;

	@Inject
	public LoginBean(UsuarioDao usuarioDao, @ScopeMap(Scope.SESSION) Map<String, Object> sessionMap,
			MessageHelper helper) {
		this.usuarioDao = usuarioDao;
		this.sessionMap = sessionMap;
		this.helper = helper;
	}

	public String deslogar() {
		this.sessionMap.remove("usuarioLogado");
		return "login?faces-redirect=true";
	}

	public String efetuaLogin() {
		System.out.println("fazendo login do usuario " + this.usuario.getEmail());

		boolean existe = this.usuarioDao.existe(this.usuario);
		if (existe) {
			this.sessionMap.put("usuarioLogado", this.usuario);
			return "livro?faces-redirect=true";
		}
		this.helper.onFlash().addMessage(new FacesMessage("Usuário não encontrado"));
		return "login?faces-redirect=true";
	}

	public Usuario getUsuario() {
		return this.usuario;
	}
}
