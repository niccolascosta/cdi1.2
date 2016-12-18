package br.com.alura.livraria.bean;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;

import br.com.alura.livraria.modelo.Autor;
import br.com.alura.livraria.modelo.Livro;
import br.com.dao.dao_lib.dao.DAO;
import br.com.dao.dao_lib.jsf.annotation.ViewModel;
import br.com.dao.dao_lib.tx.annotation.Transacional;

@ViewModel
public class LivroBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Livro livro = new Livro();

	private Integer autorId;

	private List<Livro> livros;

	private final DAO<Autor, Integer> autorDao;

	private final DAO<Livro, Integer> livroDao;

	@Inject
	public LivroBean(DAO<Livro, Integer> livroDao, DAO<Autor, Integer> autorDao) {
		this.livroDao = livroDao;
		this.autorDao = autorDao;

	}

	public void carregar(Livro livro) {
		System.out.println("Carregando livro");
		this.livro = livro;
	}

	public void carregarLivroPelaId() {
		this.livro = this.livroDao.buscaPorId(this.livro.getId());
	}

	public void comecaComDigitoUm(FacesContext fc, UIComponent component, Object value) throws ValidatorException {

		String valor = value.toString();
		if (!valor.startsWith("1")) {
			throw new ValidatorException(new FacesMessage("ISBN deveria começar com 1"));
		}

	}

	public String formAutor() {
		System.out.println("Chamanda do formulário do Autor.");
		return "autor?faces-redirect=true";
	}

	public List<Autor> getAutores() {
		return this.autorDao.listaTodos();
	}

	public List<Autor> getAutoresDoLivro() {
		return this.livro.getAutores();
	}

	public Integer getAutorId() {
		return this.autorId;
	}

	public Livro getLivro() {
		return this.livro;
	}

	public List<Livro> getLivros() {
		if (this.livros == null) {
			this.livros = this.livroDao.listaTodos();
		}
		return this.livros;
	}

	@Transacional
	public void gravar() {
		System.out.println("Gravando livro " + this.livro.getTitulo());

		if (this.livro.getAutores().isEmpty()) {
			FacesContext.getCurrentInstance().addMessage("autor",
					new FacesMessage("Livro deve ter pelo menos um Autor."));
			return;
		}

		if (this.livro.getId() == null) {
			this.livroDao.adiciona(this.livro);
			this.livros = this.livroDao.listaTodos();
		} else {
			this.livroDao.atualiza(this.livro);
		}

		this.livro = new Livro();
	}

	public void gravarAutor() {
		Autor autor = this.autorDao.buscaPorId(this.autorId);
		this.livro.adicionaAutor(autor);
		System.out.println("Escrito por: " + autor.getNome());
	}

	@Transacional
	public void remover(Livro livro) {
		System.out.println("Removendo livro");
		this.livroDao.remove(livro);
		this.livros = this.livroDao.listaTodos();
	}

	public void removerAutorDoLivro(Autor autor) {
		this.livro.removeAutor(autor);
	}

	public void setAutorId(Integer autorId) {
		this.autorId = autorId;
	}
}
