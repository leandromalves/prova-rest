package br.com.ecad.prova.business;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.Stateful;
import javax.inject.Inject;

import br.com.ecad.prova.dao.TweetDAO;
import br.com.ecad.prova.modelo.Tweet;

@Stateful
public class TweetBusiness {
	
	private static final String TEXTO_ESPACO = " ";
	
	private static Set<String> STOPWORDS; 
	
	{
		List<String> a = Arrays.asList("de","do","da","dos","das","a","o", "u", "e", "i", "na","no","em");
		STOPWORDS = new HashSet<>(a);
	}
			
	
	
	@Inject
	private TweetDAO dao;
	
	public void salvarTweetEPalavras(Tweet tweet) {
		dao.salvar(tweet);
		
		String textoTweet = tweet.getTexto();
		String[] palavras = textoTweet.split(TEXTO_ESPACO);
		for (String palavra : palavras) {
			if(palavra.length() > 1 && !STOPWORDS.contains(palavra)) {
//				dao.salvarPalavra(); /// ou deixar tudo na estrutura e salvar uma vez só? 
			}
			
		}
	}
	

}
