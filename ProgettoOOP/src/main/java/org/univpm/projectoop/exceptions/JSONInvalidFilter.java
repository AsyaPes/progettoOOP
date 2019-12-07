package org.univpm.projectoop.exceptions;

import org.json.simple.JSONObject;

/**
 * CLasse di errore Filtro non valido
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
	 * Metodo che assegna il messaggio all'oggetto error di tipo JSON
	 * @return oggetto JSON error
	 */
	public JSONObject getJSONError() {
		JSONObject error = new JSONObject();

		error.put("Errore", true);
		error.put("MessaggioErrore", this.getMessage());
		
		return error;
	}
	
}
