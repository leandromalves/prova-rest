package br.com.ecad.prova.modelo;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "tweet_palavra")
public class TweetPalavra implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@ManyToOne
    @JoinColumn(name = "tweet_id")
	private Tweet tweet;
	
	@Id
	@ManyToOne
    @JoinColumn(name = "palavra_id")
	private Palavra palavra;
	
	private Integer repeticoes;
	
	public TweetPalavra() {	}

	
	public TweetPalavra(Tweet tweet, Palavra palavra, Integer repeticoes) {
		super();
		this.tweet = tweet;
		this.palavra = palavra;
		this.repeticoes = repeticoes;
	}


	public Tweet getTweet() {
		return tweet;
	}

	public void setTweet(Tweet tweet) {
		this.tweet = tweet;
	}

	public Palavra getPalavra() {
		return palavra;
	}

	public void setPalavra(Palavra palavra) {
		this.palavra = palavra;
	}

	public Integer getRepeticoes() {
		return repeticoes;
	}

	public void setRepeticoes(Integer repeticoes) {
		this.repeticoes = repeticoes;
	}

}
