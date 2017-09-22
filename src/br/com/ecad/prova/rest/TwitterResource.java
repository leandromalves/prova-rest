package br.com.ecad.prova.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/tweet")
public class TwitterResource {

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response captar(@PathParam("param") String busca) {
		
		return Response.status(200).entity(busca).build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
    public Response getMsg() {
		System.out.println("oiii");
        String output = "Get:Jersey say : ";
        return Response.status(200).entity(output).build();
    }
	
	
	@POST
	@Path("/procurar")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response procurar(RequestBodyProcura request) {
        return Response.status(200).entity(request.busca + " teste").build();
		
	}
	
	
	/**
	 * @api {post} 
	 * 
	 * @param id do tweet a ser verificada a estatistica.
	 */
	@GET
	@Path("{id}/estatisticas")
	@Produces(MediaType.APPLICATION_JSON)
	public void obterEstatisticas(@PathParam("id") Integer id) {
		
	}
	
	
	
	@GET
	@Path("{id}/mais_comum")
	@Produces(MediaType.APPLICATION_JSON)
	public void obterPalavraMaisComum() {
		
	}
	
	
	
	@GET
	@Path("/mais_longa")
	@Produces(MediaType.APPLICATION_JSON)
	public void obterPalavraMaisLonga() {
		
	}
	
}
