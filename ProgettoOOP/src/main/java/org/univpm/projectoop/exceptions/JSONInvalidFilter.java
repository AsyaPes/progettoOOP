package org.univpm.projectoop.exceptions;

import org.json.simple.JSONObject;

public class JSONInvalidFilter extends Exception {
	
	@Override
	public String getMessage() {
		return "Errore,filtro non valido";
	}

	public JSONObject getJSONError() {
		JSONObject error = new JSONObject();

		error.put("Errore", true);
		error.put("MessaggioErrore", this.getMessage());
		
		return error;
	}
	
}
