package org.univpm.projectoop.dataset;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.univpm.projectoop.exceptions.JSONInvalidValue;
import org.univpm.projectoop.utils.FilterNumber;
import org.univpm.projectoop.utils.ParserTSV;
import org.univpm.projectoop.utils.Utils;

public class StatisticData {
	
	List<Stock> stocks = new ArrayList<Stock>(); 
	public static JSONObject statsMethod(List<Stock> stocks) throws JSONInvalidValue
	{
		if( stocks.size() == 0 )
		{
			throw new JSONInvalidValue();
		}
		
		FilterNumber[] data = new FilterNumber[Utils.getNumericFields().size() - 1]; //Con il -1 escludo Product
		Map<String,Integer> Unit = new HashMap<String,Integer>();
		Map<String,Integer> Indic_nrg = new HashMap<String,Integer>();
		Map<String,Integer> Geo = new HashMap<String,Integer>();
		
		for(int i=0;i<data.length;i++) 
		{
			data[i]=new FilterNumber();
		}
		
		JSONObject stats = new JSONObject();
		JSONArray statsData= new JSONArray();
		
		for (Stock s : stocks) 
		{
			analyzeUniqueStrings("Unit", Unit);
			analyzeUniqueStrings("Indic_nrg", Indic_nrg);
			analyzeUniqueStrings("Geo", Geo);
			
			
		}
		
		return stats;
	}
	
	private static Map<String,Integer> analyzeUniqueStrings(String value,Map<String,Integer> h)
	{
		if(h.containsKey(value))
		{
			int newCount = h.get(value) + 1;
			h.remove(value);
			h.put(value, newCount);
		}
		else 
		{
			h.put(value, 1);
		}
		return h;
	}
}
