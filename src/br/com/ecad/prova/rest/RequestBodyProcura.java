package br.com.ecad.prova.rest;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RequestBodyProcura {
	
	@XmlElement String busca;
	
}
