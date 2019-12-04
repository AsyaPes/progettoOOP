package org.univpm.projectoop.exceptions;

import org.json.simple.JSONObject;

public class JSONInvalidKey extends JSONInvalidFilter {
	
	@Override
	public String getMessage() {
		return "Errore, chiave del filtro non valida.";
	}

	public JSONObject getJSONError() {
		JSONObject error = new JSONObject();

		error.put("Errore", true);
		error.put("MessaggioErrore", this.getMessage());
		
		return error;
	}
	
}


