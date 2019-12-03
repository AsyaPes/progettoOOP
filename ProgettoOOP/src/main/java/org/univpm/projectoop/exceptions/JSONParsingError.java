package org.univpm.projectoop.exceptions;

import org.json.simple.JSONObject;

public class JSONParsingError extends Exception {
	
	@Override
	public String getMessage() {
		return "Errore nel parsing del JSON";
	}

	public JSONObject getJSONError() {
		JSONObject error = new JSONObject();

		error.put("Errore", true);
		error.put("MessaggioErrore", this.getMessage());
		
		return error;
	}

}
