package br.com.ecad.prova.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.ecad.prova.modelo.Tweet;

@Stateless
public class TweetDAO {
	
	@PersistenceContext
	EntityManager manager;
	
	
	public void salvar(Tweet tweet) {
		manager.persist(tweet);
	}

}
