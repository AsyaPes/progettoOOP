package org.univpm.projectoop.exceptions;

import org.json.simple.JSONObject;

/**
 * Sottoclasse della classe Exception che gestisce gli errori di parsing
 * @author Asya Pesaresi e Lorenzo Vagnini
 *
 */
public class JSONParsingError extends Exception {
	
	/**
	 * Metodo che restituisce un messaggio di errore di parsing
	 */
	@Override
	public String getMessage() {
		return "Errore nel parsing del JSON";
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
