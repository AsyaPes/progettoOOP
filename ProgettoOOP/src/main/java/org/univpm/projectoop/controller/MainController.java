package org.univpm.projectoop.controller;

import java.util.ArrayList;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.univpm.projectoop.dataset.Stock;
import org.univpm.projectoop.dataset.StockCollection;
import org.univpm.projectoop.exceptions.JSONInvalidFilter;
import org.univpm.projectoop.exceptions.JSONParsingError;
import org.univpm.projectoop.utils.ParserTSV;
import org.univpm.projectoop.utils.Utils;

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
		
		List<Stock> data = new ArrayList<Stock>();
		JSONParser p = new JSONParser();
		JSONObject filteredData;
		JSONObject filterJSON;
		
		if(filter != null) {
			
			try {
				filterJSON = Utils.parseJSONString(filter); // (JSONObject) p.parse(filter);
				
				Utils.checkFilterJSON(filterJSON);
				
				
			}catch(JSONParsingError e) {
				return e.getJSONError();
				
			}catch(JSONInvalidFilter e) {
				return e.getJSONError();
			}
		}
		
		
		
		
		//parsing filtro in oggetto json
		//verficare se filtro corretto(se non corretto errore)
		//ciclare per ogni persona del dataset appicare filtro e aggiungere all'arrayLIst se è valido per il filtro
		
		
		return null;
	}
		
}

