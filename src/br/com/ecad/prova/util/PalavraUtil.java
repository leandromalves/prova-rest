package br.com.ecad.prova.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class PalavraUtil {
	
	
	private static final Set<String> STOPWORDS = new HashSet<>(Arrays.asList("de","do","da","dos","das","a","o", "u", "e", "i", "na","no","em"));
	
	public static boolean isPalavraRelevante(String palavra) {
		if(STOPWORDS.contains(palavra)) {
			return false;
		}
		return true;
	}
	
	public static boolean terminaComPontuacao(String palavra) {
		if(palavra.endsWith(",") 
				|| palavra.endsWith(".")
				|| palavra.endsWith("?")
				|| palavra.endsWith("!")
				|| palavra.endsWith(":")
				|| palavra.endsWith(";")) {
			return true;
		}
		return false;
	}
	
	
	
	public static boolean iniciaComPontuacao(String palavra) {
		if(palavra.startsWith(",") 
				|| palavra.startsWith(".")
				|| palavra.startsWith("?")
				|| palavra.startsWith("!")
				|| palavra.startsWith(":")
				|| palavra.startsWith(";")) {
			return true;
		}
		return false;
	}
}
