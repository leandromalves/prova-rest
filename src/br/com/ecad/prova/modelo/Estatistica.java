package br.com.ecad.prova.modelo;

public class Estatistica {
	
	private double tweetId;
	private double numeroTotalPalavras;
	private double tamanhoMenorPalavra;
	private double tamanhoMaiorPalavra;
	
	private double media;

	public Estatistica(double tweetId, double numeroTotalPalavras, double tamanhoMenorPalavra,
			double tamanhoMaiorPalavra, double media) {
		super();
		this.tweetId = tweetId;
		this.numeroTotalPalavras = numeroTotalPalavras;
		this.tamanhoMenorPalavra = tamanhoMenorPalavra;
		this.tamanhoMaiorPalavra = tamanhoMaiorPalavra;
		this.media = media;
	}

	public double getTweetId() {
		return tweetId;
	}

	public void setTweetId(double tweetId) {
		this.tweetId = tweetId;
	}

	public double getNumeroTotalPalavras() {
		return numeroTotalPalavras;
	}

	public void setNumeroTotalPalavras(double numeroTotalPalavras) {
		this.numeroTotalPalavras = numeroTotalPalavras;
	}

	public double getTamanhoMenorPalavra() {
		return tamanhoMenorPalavra;
	}

	public void setTamanhoMenorPalavra(double tamanhoMenorPalavra) {
		this.tamanhoMenorPalavra = tamanhoMenorPalavra;
	}

	public double getTamanhoMaiorPalavra() {
		return tamanhoMaiorPalavra;
	}

	public void setTamanhoMaiorPalavra(double tamanhoMaiorPalavra) {
		this.tamanhoMaiorPalavra = tamanhoMaiorPalavra;
	}

	public double getMedia() {
		return media;
	}

	public void setMedia(double media) {
		this.media = media;
	}

}
