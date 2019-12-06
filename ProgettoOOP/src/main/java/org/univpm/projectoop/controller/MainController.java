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
import org.univpm.projectoop.dataset.StockCollection;
import org.univpm.projectoop.exceptions.JSONInvalidFilter;
import org.univpm.projectoop.exceptions.JSONParsingError;
import org.univpm.projectoop.utils.ParserTSV;
import org.univpm.projectoop.utils.Utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * @author Asya Pesaresi e Lorenzo Vagnini
 *
 */
@RestController
public class MainController {

	/**
	 * 
	 * @return
	 */
	@RequestMapping(value = "/", produces="application/json")
	public JSONObject index()
	{
		JSONObject json = new JSONObject();
		json.put("ABC", ParserTSV.getData());
		return json;
	}
	
	/**
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getFilteredData", produces="application/json")
	public JSONObject getFilteredDataGET()
	{
		return StatisticData.statsMethod( ParserTSV.getData() );
	}
	
	/**
	 * 
	 * @param filter
	 * @return
	 */
	//gli passo un filtro con richiesta post
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
