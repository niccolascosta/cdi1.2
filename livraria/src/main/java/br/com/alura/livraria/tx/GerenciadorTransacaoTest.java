package br.com.alura.livraria.tx;

import javax.enterprise.inject.Specializes;
import javax.interceptor.InvocationContext;

import br.com.dao.dao_lib.tx.TransacaoPadrao;

/*@Specializes*/
public class GerenciadorTransacaoTest extends TransacaoPadrao{

	
	private static final long serialVersionUID = -7009690262568125998L;

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
