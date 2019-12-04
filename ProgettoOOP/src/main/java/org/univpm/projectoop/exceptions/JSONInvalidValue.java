package org.univpm.projectoop.exceptions;

import org.json.simple.JSONObject;

public class JSONInvalidValue extends JSONInvalidFilter {
	
	@Override
	public String getMessage() {
		return "Errore, valore associato ad una chiave non valido.";
	}

	public JSONObject getJSONError() {
		JSONObject error = new JSONObject();

		error.put("Errore", true);
		error.put("MessaggioErrore", this.getMessage());
		
		return error;
	}
	
}
