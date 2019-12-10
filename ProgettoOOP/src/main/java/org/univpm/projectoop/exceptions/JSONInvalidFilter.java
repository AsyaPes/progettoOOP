package org.univpm.projectoop.exceptions;

import org.json.simple.JSONObject;

/**
 * Classe di errore Filtro non valido che estende la classe di errore generico Exception
 * @author Asya Pesaresi e Lorenzo Vagnini
 *
 */
public class JSONInvalidFilter extends Exception {
	
	/**
	 * Metodo che restituisce un messaggio di errore 
	 */
	@Override
	public String getMessage() {
		return "Errore,filtro non valido";
	}

	/**
	 * Metodo che restituisce un JSONObject contenente l'errore
	 * @return oggetto JSON error
	 */
	public JSONObject getJSONError() {
		JSONObject error = new JSONObject();

		error.put("Errore", true);
		error.put("MessaggioErrore", this.getMessage());
		
		return error;
	}
	
}
