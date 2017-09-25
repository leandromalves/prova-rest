package br.com.ecad.prova.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import br.com.ecad.prova.modelo.Palavra;

@Stateless
public class PalavraDAO {
	
	@PersistenceContext
	EntityManager manager;
	
	
	private static final String MAIS_LONGA_QUERY = "SELECT palavra FROM prova.Palavra WHERE"
			+ " LENGTH(palavra) = (SELECT MAX(LENGTH(palavra)) FROM prova.Palavra) ORDER BY palavra ASC;";
	
	private static final String MAIS_COMUM_QUERY = "SELECT p.palavra FROM prova.tweet_palavra tp"
			+ " JOIN prova.Palavra p ON (tp.palavra_id = p.id)"
			+ " GROUP BY p.id having sum(tp.repeticoes) = "
			+ "(select sum(repeticoes) as rep from prova.tweet_palavra group by palavra_id order by rep desc limit 1)"
			+ " ORDER BY p.palavra ASC";
	
	
	private static final String EXATAMENTE_EM_QUERY = "SELECT p.palavra FROM prova.tweet_palavra tp"
			+ " join prova.Palavra p ON (p.id = tp.palavra_id)"
			+ " GROUP BY palavra_id HAVING COUNT(palavra_id) = :repeticoes ORDER BY palavra_id ASC";
	
	
	
	public void salvar(Palavra palavra) {
		manager.persist(palavra);
	}
	


	public Palavra buscar(String palavra) {
		try {
			TypedQuery<Palavra> query = manager.createQuery("select p from Palavra p where p.palavra = :palavra", Palavra.class);
			query.setParameter("palavra", palavra);
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	
	@SuppressWarnings("unchecked")
	public List<String> buscarMaisComum() {
		return manager.createNativeQuery(MAIS_COMUM_QUERY).getResultList();
	}
	
	
	@SuppressWarnings("unchecked")
	public List<String> buscarMaisLonga() {
		return manager.createNativeQuery(MAIS_LONGA_QUERY).getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<String> buscarPalavraComQuantidadeDeRepeticoes(Integer repeticoes) {
		return manager.createNativeQuery(EXATAMENTE_EM_QUERY).setParameter("repeticoes", repeticoes).getResultList();
	}
	

}
