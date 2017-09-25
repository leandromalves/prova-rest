package br.com.ecad.prova.modelo;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Tweet {

	@Id
	private Long id;
	
	private String texto;
	
	@OneToMany(mappedBy = "tweet", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<TweetPalavra> tweetPalavras;
	
	public Tweet() {
		this.tweetPalavras = new HashSet<>();
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTexto() {
		return texto;
	}
	public void setTexto(String texto) {
		this.texto = texto;
	}
	
	public Set<TweetPalavra> getTweetPalavras() {
		return tweetPalavras;
	}
	public void setTweetPalavras(Set<TweetPalavra> tweetPalavras) {
		this.tweetPalavras = tweetPalavras;
	}
	
	
	public boolean adicionarPalavra(TweetPalavra tp) {
		return this.tweetPalavras.add(tp);
	}

	@Override
	public String toString() {
		return "Tweet [id=" + id + ", texto=" + texto + ", tweetPalavras=" + tweetPalavras + "]";
	}
	
	
	
}
