package org.univpm.projectoop.exceptions;

import org.json.simple.JSONObject;

/**
 * Sottoclasse della superclasse JSONInvalidFilter
 * @author Asya Pesaresi e Lorenzo Vagnini
 *
 */
public class JSONInvalidKey extends JSONInvalidFilter {
	
	/**
	 * Metodo che restituisce un messaggio di errore 
	 */
	@Override
	public String getMessage() {
		return "Errore, chiave del filtro non valida.";
	}

	/**
	 * Metodo che assegna il messaggio all'oggetto error di tipo JSON
	 */
	public JSONObject getJSONError() {
		JSONObject error = new JSONObject();

		error.put("Errore", true);
		error.put("MessaggioErrore", this.getMessage());
		
		return error;
	}
	
}


