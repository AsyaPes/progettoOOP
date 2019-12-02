package org.univpm.projectoop.controller;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class MainController {

	@RequestMapping(value = "/", produces="application/json")
	public JSONObject index()
	{
		JSONObject json = new JSONObject();
		
		return json;
	}
	
	//gli passo un filtro con richiesta post
	@RequestMapping( value = "/getFilteredData", method = RequestMethod.POST, produces="application/json" )
	public JSONObject getFilteredData( @RequestBody(required = false) String filter )
	{
		
		//parsing filtro in oggetto json
		//verficare se filtro corretto(se non corretto errore)
		//ciclare per ogni persona del dataset appicare filtro e aggiungere all'arrayLIst se è valido per il filtro
		
		
		return null;
	}
	
}
