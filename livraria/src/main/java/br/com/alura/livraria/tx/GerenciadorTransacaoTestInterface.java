package br.com.alura.livraria.tx;

import javax.annotation.Priority;
import javax.enterprise.inject.Alternative;
import javax.enterprise.inject.Typed;
import javax.inject.Inject;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.persistence.EntityManager;

import br.com.dao.dao_lib.tx.Transacao;

@Alternative
@Priority(Interceptor.Priority.APPLICATION)
@Typed(Transacao.class)
public class GerenciadorTransacaoTestInterface implements Transacao{

	
	private static final long serialVersionUID = -7009690262568125998L;
	
	@Inject
	private EntityManager em;

	@Override
	public Object executaComTransacao(InvocationContext context) {
		System.out.println("Abrindo uma transação");
		
		em.getTransaction().begin();
		
		try {
			System.out.println("Antes de executar o método interceptado");
			Object resultado = context.proceed();
			
			System.out.println("Antes de commitar a transação");
			em.getTransaction().commit();
			
			return resultado;
		} catch (Exception e) {
			
			System.out.println("Antes de efetuar o rollback da transação");
			em.getTransaction().rollback();
			
			throw new RuntimeException(e);
		}
	}
}
