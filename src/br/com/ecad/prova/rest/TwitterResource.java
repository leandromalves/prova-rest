package br.com.ecad.prova.rest;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.com.ecad.prova.business.TweetBusiness;
import br.com.ecad.prova.modelo.Estatistica;
import br.com.ecad.prova.modelo.Tweet;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

import static br.com.ecad.prova.util.Constantes.*;

@Path("/tweet")
public class TwitterResource {
	
	@Inject
	private TweetBusiness service;
	
//	@Inject
//	private Logger log;
//
	@POST
	@Path("/captar")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response captar(RequestBodyProcura request) {

		try {

			ConfigurationBuilder cb = new ConfigurationBuilder();
			cb.setDebugEnabled(true)
			  .setOAuthConsumerKey(CONSUMER_KEY)
			  .setOAuthConsumerSecret(CONSUMER_SECRET)
			  .setOAuthAccessToken(ACCESS_TOKEN)
			  .setOAuthAccessTokenSecret(TOKEN_SECRET);
			TwitterFactory tf = new TwitterFactory(cb.build());
			Twitter twitter = tf.getInstance();
			
			Query query = new Query(request.busca);
			query.setLang(LANG);
			query.setCount(COUNT_QUERY_TWITTER);
			
			Tweet tweet = null;
	//		log.info("captar - busca = " + request.busca);
			try {
				QueryResult result = twitter.search(query);
				List<Status> tweets = result.getTweets();
				for (Status status : tweets) {
					tweet = new Tweet();
					tweet.setId(status.getId());
					tweet.setTexto(status.getText());
					System.out.println("TWEET:=> " + tweet.toString());
					service.salvarTweetEPalavras(tweet );
				}
			} catch (TwitterException e) {
	//			log.error(e.getMessage());
			}
			
			
			
			return Response.ok().entity(request.busca).build();
		} catch (Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	
	@POST
	@Path("/procurar")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response procurar(RequestBodyProcura request) {
		try {
			List<Tweet> tweetsEncontrados = service.procurar(request.busca);
			return Response.ok().entity(tweetsEncontrados).build();
		} catch (Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	
	/**
	 * @api {post} 
	 * 
	 * @param id do tweet a ser verificada a estatistica.
	 */
	@GET
	@Path("{idTweet}/estatisticas")
	@Produces(MediaType.APPLICATION_JSON)
	public Response obterEstatisticas(@PathParam("idTweet") Long idTweet) {
		Estatistica estatistica = service.geraEstatisticaDoTweet(idTweet);
		
		if(estatistica == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
			
		}
		return Response.ok().entity(estatistica).build();
	}
	
	
	/**
	 * Busca a palavra mais comum dentre os tweets, ou seja, com maior numero de
	 * ocorrências. Em caso de empate, retorna estas ordenadas.
	 * @return palavra(s) mais comum(ns).
	 */
	@GET
	@Path("/mais_comum")
	@Produces(MediaType.APPLICATION_JSON)
	public Response obterPalavraMaisComum() {
		try {
			List<String> palavraMaisComumOrdenadaList = service.buscarMaisComum();
			return Response.ok().entity(palavraMaisComumOrdenadaList).build();
		} catch (Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
		
	}
	
	
	/**
	 * Busca a palavra que possui maior tamanho em todos os Tweets. Se existirem 
	 * mais que uma palavra com maior tamanho, retorna estas ordenadas.
	 * @return palavra(s) mais longa(s).
	 */
	@GET
	@Path("/mais_longa")
	@Produces(MediaType.APPLICATION_JSON)
	public Response obterPalavraMaisLonga() {
		try {
			List<String> palavraMaisLongaOrdenadaList = service.buscarMaisLonga();
			return Response.ok().entity(palavraMaisLongaOrdenadaList).build();
		} catch (Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	
	@GET
	@Path("/exatamente_em/{numeroRepeticoes}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response palavraEmNumeroDeTweets(@PathParam("numeroRepeticoes") Integer repeticoes) {
		try {
			List<String> repetidas = service.buscarPalavraComQuantidadeDeRepeticoes(repeticoes);
			return Response.ok().entity(repetidas).build();
		} catch (Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	
}
