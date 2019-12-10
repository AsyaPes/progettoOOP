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
import org.univpm.projectoop.dataset.StatisticData;
import org.univpm.projectoop.dataset.Stock;
import org.univpm.projectoop.exceptions.JSONInvalidFilter;
import org.univpm.projectoop.exceptions.JSONParsingError;
import org.univpm.projectoop.utils.ParserTSV;
import org.univpm.projectoop.utils.Utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.jsonSchema.JsonSchema;
import com.fasterxml.jackson.module.jsonSchema.JsonSchemaGenerator;

/**
 * Classe che restituisce i risultati delle operazioni delle altre classi
 * @author Asya Pesaresi e Lorenzo Vagnini
 *
 */
@RestController
public class MainController {

	/**
	 * Metodo che restituisce l'insieme dei dati
	 * @return json JSONObject per la risposta HTTP
	 */
	@RequestMapping(value = "/", produces="application/json")
	public JSONObject index()
	{
		List<Stock> dataset = ParserTSV.getData();
		
		JSONObject json = new JSONObject();
		json.put("Dati", dataset);
		json.put("NumeroElementi", dataset.size());
		json.put("Error", false);
		return json;
	}
	

	@RequestMapping(value = "/getMetadata", produces="application/json")
	public JSONObject getMetadata()
	{
		ObjectMapper mapper = new ObjectMapper();
		JsonSchemaGenerator jsonSchemaGenerator = new JsonSchemaGenerator(mapper);
		JsonSchema jsonSchema;
		try {
			jsonSchema = jsonSchemaGenerator.generateSchema(Stock.class);
			return Utils.parseJSONString( mapper.writeValueAsString(jsonSchema) );
		} catch (JsonMappingException e) {
			JSONObject obj = new JSONObject();
			obj.put("Errore", e.getMessage());
			return obj;
		} catch (JsonProcessingException e) {
			JSONObject obj = new JSONObject();
			obj.put("Errore", e.getMessage());
			return obj;
		} catch (JSONParsingError e) {
			return e.getJSONError();
		}	
	}
	
	/**
	 * Metodo che restituisce le analisi sui dati dell'intero dataset
	 * @return JSONObject contenente le analisi sui dati
	 */
	@RequestMapping(value = "/getFilteredData", produces="application/json")
	public JSONObject getFilteredDataGET()
	{
		return StatisticData.statsMethod( ParserTSV.getData() );
	}

	//gli passo un filtro con richiesta post
	/**
	 * Metodo che restituisce le analisi sui dati filtrati 
	 * @param filter Filtro in formato JSON
	 * @return JSONObject contenente le analisi sui dati
	 */
	@RequestMapping( value = "/getFilteredData", method = RequestMethod.POST, produces="application/json" )
	public JSONObject getFilteredDataPOST( @RequestBody(required = false) String filter )
	{
		
		JSONParser p = new JSONParser();
		JSONObject filteredData;
		JSONObject filterJSON;
		
		if(filter != null) {
			
			try {
		
				List<Stock> data = new ArrayList<Stock>();
				
				filterJSON = Utils.parseJSONString(filter); // (JSONObject) p.parse(filter);
				
				Utils.checkFilterJSON(filterJSON);
				
				for( Stock stock : ParserTSV.getData() )
				{
					if( stock.Filter( filterJSON ) )
					{
						data.add( stock );						
					}
				}
				
				return StatisticData.statsMethod( data );
				
			}catch(JSONParsingError e) {
				return e.getJSONError();
				
			}catch(JSONInvalidFilter e) {
				return e.getJSONError();
			}
		}else {
			
			return StatisticData.statsMethod( ParserTSV.getData() );
		}
		
	}
		
}
