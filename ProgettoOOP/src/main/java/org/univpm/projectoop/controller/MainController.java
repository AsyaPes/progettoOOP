package org.univpm.projectoop.controller;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class MainController {

	@RequestMapping(value = "/", produces="application/json")
	public JSONObject index()
	{
		JSONObject j1 = new JSONObject();
		JSONObject j2 = new JSONObject();
		JSONArray j3 = new JSONArray();

		j2.put("nome","mario");
		j2.put("cognome","rossi");

		j3.add(j2);
		j3.add(j2);
		j3.add(j2);
		j3.add(j2);
		j3.add(j2);

		j1.put("lista", j3);
		
		return j1;
	}
	
}
