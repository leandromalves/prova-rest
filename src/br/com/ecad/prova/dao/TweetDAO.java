package br.com.ecad.prova.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import br.com.ecad.prova.modelo.Tweet;

@Stateless
public class TweetDAO {
	
	@PersistenceContext
	EntityManager manager;
	
	private static final String PROCURAR_QUERY = new StringBuilder("SELECT t.id, t.texto ")
			.append(" FROM prova.Palavra p")
			.append(" inner join prova.tweet_palavra tp")
			.append(" ON (tp.palavra_id = p.id)")
			.append(" inner join prova.Tweet t")
			.append(" ON (tp.tweet_id = t.id)")
			.append(" where p.palavra = :param").toString();
	
	
	private static final String NUM_PALAVRAS_QUERY = new StringBuilder("SELECT SUM(tp.repeticoes)")
			.append(" FROM prova.tweet_palavra tp")
			.append(" inner join prova.Palavra p")
			.append(" ON (tp.palavra_id = p.id)")
			.append(" where tweet_id = :tweetId").toString(); 

	private static final String TAM_PALAVRA_CURTA_QUERY = new StringBuilder("SELECT length(p.palavra) as tamanho")
			.append(" FROM prova.tweet_palavra tp")
			.append(" INNER JOIN prova.Palavra p")
			.append(" ON (tp.palavra_id = p.id)")
			.append(" WHERE tweet_id = :tweetId")
			.append(" ORDER BY tamanho ASC")
			.append(" LIMIT 1").toString(); 


	private static final String TAM_PALAVRA_LONGA_QUERY = new StringBuilder("SELECT length(p.palavra) as tamanho")
			.append(" FROM prova.tweet_palavra tp")
			.append(" inner join prova.Palavra p")
			.append(" 	ON (tp.palavra_id = p.id)")
			.append(" where tweet_id = :tweetId")
			.append(" order by tamanho desc")
			.append(" limit 1").toString(); 


	private static final String TAM_TOTAL_PALAVRAS_QUERY = new StringBuilder("SELECT SUM(length(p.palavra) * tp.repeticoes) as total")
			.append(" FROM prova.tweet_palavra tp")
			.append(" inner join prova.Palavra p")
			.append(" ON (tp.palavra_id = p.id)")
			.append(" where tweet_id = :tweetId").toString();
	
	
	public void salvar(Tweet tweet) {
		manager.persist(tweet);
	}

	
	@SuppressWarnings("unchecked")
	public List<Tweet> procurar(String busca) {
		return manager.createNativeQuery(PROCURAR_QUERY).setParameter("param", busca).getResultList();
	}
	
	public Double numeroDePalavrasNoTweet(Long tweetId) {
		return executaQuery(tweetId, NUM_PALAVRAS_QUERY);
	}
	
	
	public Double obterTamanhoMenorPalavraTweet(Long tweetId) {
		return executaQuery(tweetId, TAM_PALAVRA_CURTA_QUERY);
	}
	
	
	public Double obterTamanhoMaiorPalavraTweet(Long tweetId) {
		return executaQuery(tweetId, TAM_PALAVRA_LONGA_QUERY);
	}
	
	/**
	 * Retorna o tamanho total de caracteres de todas as palavras relevantes do Tweet. 
	 * Retorna null se não existe.
	 * @param tweetId Id do Tweet
	 * @return
	 */
	public Double obterTamanhoTotalDasPalavrasTweet(Long tweetId) {
		return executaQuery(tweetId, TAM_TOTAL_PALAVRAS_QUERY);
	}
	
	private Double executaQuery(Long tweetId, String query) {
		try {
			Number resultado = (Number) manager.createNativeQuery(query).setParameter("tweetId", tweetId).getSingleResult();
			return resultado != null ? resultado.doubleValue() : null;
		} catch (NoResultException e) {
		    return null;
		}
	}
	
}
