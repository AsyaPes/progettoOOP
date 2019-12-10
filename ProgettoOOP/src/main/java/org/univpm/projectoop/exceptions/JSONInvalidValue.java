package org.univpm.projectoop.exceptions;

import org.json.simple.JSONObject;

/**
 * Sottoclasse della superclase JSONInvalidFilter che gestisce gli errori sui valori annessi
 * @author Asya Pesaresi e Lorenzo Vagnini
 *
 */
public class JSONInvalidValue extends JSONInvalidFilter {
	
	/**
	 * Metodo che restituisce un messaggio di errore 
	 */
	@Override
	public String getMessage() {
		return "Errore, valore associato ad una chiave non valido.";
	}

	/**
	 * Metodo che restituisce un JSONObject contenente l'errore
	 */
	public JSONObject getJSONError() {
		JSONObject error = new JSONObject();

		error.put("Errore", true);
		error.put("MessaggioErrore", this.getMessage());
		
		return error;
	}
	
}
