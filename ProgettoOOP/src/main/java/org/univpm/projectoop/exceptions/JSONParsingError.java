package org.univpm.projectoop.exceptions;

import org.json.simple.JSONObject;

/**
 * Sottoclasse della classe Exception
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
