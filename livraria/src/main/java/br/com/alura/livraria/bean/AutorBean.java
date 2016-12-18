package br.com.alura.livraria.bean;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.inject.Model;
import javax.inject.Inject;

import br.com.alura.livraria.modelo.Autor;
import br.com.dao.dao_lib.dao.DAO;
import br.com.dao.dao_lib.tx.annotation.Transacional;

@Model
public class AutorBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Autor autor = new Autor();

	private Integer autorId;

	private final DAO<Autor, Integer> autorDao;

	@Inject
	public AutorBean(DAO<Autor, Integer> dao) {
		this.autorDao = dao;
	}

	public void carregarAutorPelaId() {
		this.autor = this.autorDao.buscaPorId(this.autorId);
	}

	public Autor getAutor() {
		return this.autor;
	}

	public List<Autor> getAutores() {
		return this.autorDao.listaTodos();
	}

	public Integer getAutorId() {
		return this.autorId;
	}

	@Transacional
	public String gravar() {
		System.out.println("Gravando autor " + this.autor.getNome());

		if (this.autor.getId() == null) {
			this.autorDao.adiciona(this.autor);
		} else {
			this.autorDao.atualiza(this.autor);
		}

		this.autor = new Autor();

		return "livro?faces-redirect=true";
	}

	@Transacional
	public void remover(Autor autor) {
		System.out.println("Removendo autor " + autor.getNome());
		this.autorDao.remove(autor);
	}

	public void setAutor(Autor autor) {
		this.autor = autor;
	}

	public void setAutorId(Integer autorId) {
		this.autorId = autorId;
	}
}
