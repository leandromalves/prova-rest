package br.com.ecad.prova.business;

import static br.com.ecad.prova.util.PalavraUtil.iniciaComPontuacao;
import static br.com.ecad.prova.util.PalavraUtil.isPalavraRelevante;
import static br.com.ecad.prova.util.PalavraUtil.terminaComPontuacao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateful;
import javax.inject.Inject;

import br.com.ecad.prova.dao.PalavraDAO;
import br.com.ecad.prova.dao.TweetDAO;
import br.com.ecad.prova.modelo.Estatistica;
import br.com.ecad.prova.modelo.Palavra;
import br.com.ecad.prova.modelo.Tweet;
import br.com.ecad.prova.modelo.TweetPalavra;


@Stateful
public class TweetBusiness {
	
	private static final String TEXTO_ESPACO = " ";
	
	@Inject
	private TweetDAO tweetDao;

	@Inject
	private PalavraDAO palavraDao;

	
	public void salvarTweetEPalavras(Tweet tweet) {
		
		Map<String, Integer> palavrasMap = agrupaRepetidas(tweet.getTexto());
		
		for (String palavra : palavrasMap.keySet()) {
			Palavra p = palavraDao.buscar(palavra);
			if(p == null) {
				p = new Palavra();
				p.setPalavra(palavra);
				palavraDao.salvar(p);
			}
			TweetPalavra tweetPalavra = new TweetPalavra(tweet, p, palavrasMap.get(palavra));
			tweet.adicionarPalavra(tweetPalavra);
			
		}
		
		tweetDao.salvar(tweet);
	}
	
	private Map<String, Integer> agrupaRepetidas(String textoTweet) {
		Map<String, Integer> palavrasMap = new HashMap<>();
		String[] palavras = textoTweet.split(TEXTO_ESPACO);
		for (String palavra : palavras) {
			palavra = tratarPalavra(palavra);
			if(palavra != null) {
				if(palavrasMap.get(palavra) == null) {
					palavrasMap.put(palavra, 1);
				} else {
					palavrasMap.put(palavra, palavrasMap.get(palavra) + 1);
				}
			}
		}
		return palavrasMap;
	}

	
	
	private String tratarPalavra(String palavra) {
		
		if(!isPalavraRelevante(palavra)) {
			return null;
		}
		
		while (terminaComPontuacao(palavra) && palavra.length() > 1) {
			palavra = palavra.substring(0, palavra.length() - 1);
		}
		
		while (iniciaComPontuacao(palavra) && palavra.length() > 1) {
			palavra = palavra.substring(1, palavra.length());
		}
		
		if(palavra.length() <= 1) {
			return null;
		}
		return palavra;
	}

	public List<Tweet> procurar(String busca) {
		return tweetDao.procurar(busca);
	}
	
	
	public List<String> buscarMaisComum() {
		return palavraDao.buscarMaisComum();
	}
	
	
	public List<String> buscarMaisLonga() {
		return palavraDao.buscarMaisLonga();
	}
	
	/**
	 * 
	 * @param repeticoes
	 * @return
	 */
	public List<String> buscarPalavraComQuantidadeDeRepeticoes(Integer repeticoes) {
		return palavraDao.buscarPalavraComQuantidadeDeRepeticoes(repeticoes);
	}
	
	/**
	 * Gera estatistica do Tweet enviado. Com:
	 * seu Total de Palavras.
	 * Tamanho da Menor palavra do Tweet
	 * Tamanho da Maior palavra do Tweet
	 * Media dos tamanhos das palavras
	 * @param tweetId Id do Tweet
	 * @return Estatistica completa
	 */
	public Estatistica geraEstatisticaDoTweet(Long tweetId) {
		
		Double numeroTotalPalavras = tweetDao.numeroDePalavrasNoTweet(tweetId);
		Double tamanhoMaiorPalavra = tweetDao.obterTamanhoMaiorPalavraTweet(tweetId);
		Double tamanhoMenorPalavra = tweetDao.obterTamanhoMenorPalavraTweet(tweetId);
		
		Double tamanhoTotalTodasPalavras = tweetDao.obterTamanhoTotalDasPalavrasTweet(tweetId);
		
		if(numeroTotalPalavras == null || tamanhoMenorPalavra == null || 
				tamanhoMaiorPalavra == null || tamanhoTotalTodasPalavras == null) {
			return null;
		}
		
		double media = tamanhoTotalTodasPalavras / numeroTotalPalavras;
		
		return new Estatistica(tweetId, numeroTotalPalavras, tamanhoMenorPalavra, tamanhoMaiorPalavra, media);
	}

}
