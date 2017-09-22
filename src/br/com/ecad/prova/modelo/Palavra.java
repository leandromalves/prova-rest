package br.com.ecad.prova.modelo;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Palavra {

	@Id
	private String palavra;
	
	// Tweets em que a palavra está presente.
	@ManyToMany
	private List<Tweet> tweetsPresente;
	
	
	public String getPalavra() {
		return palavra;
	}
	
	public void setPalavra(String palavra) {
		this.palavra = palavra;
	}
}
