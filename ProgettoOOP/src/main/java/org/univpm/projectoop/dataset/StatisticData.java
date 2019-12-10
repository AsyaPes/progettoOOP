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

/**
 * Classe che applica le stats sui dati
 * @author Asya Pesaresi e Lorenzo Vagnini
 *
 */
public class StatisticData {
	
	List<Stock> stocks = new ArrayList<Stock>();
	
	/**
	 * Metodo che applica le stats a stocks
	 * @param stocks Lista di oggeti di tipo Stock
	 * @return stats JSONObject contenente i dati analizzati
	 */
	@SuppressWarnings("unchecked")
	public static JSONObject statsMethod(List<Stock> stocks)
	{
		if( stocks.size() == 0 )
		{
			JSONObject jsonVoid = new JSONObject();
			
			jsonVoid.put("Dati", new String[0] );
			
			return jsonVoid;
		}
		
		FilterNumber[] data = new FilterNumber[Utils.getNumericFields().size() - 1]; //Con il -1 escludo Product
		FilterNumber Product = new FilterNumber();
		Map<String,Integer> Unit = new HashMap<String,Integer>();
		Map<String,Integer> Indic_nrg = new HashMap<String,Integer>();
		Map<String,Integer> Geo = new HashMap<String,Integer>();
		
		for(int i=0;i<data.length;i++) 
		{
			data[i]=new FilterNumber();
		}
		
		JSONObject stats = new JSONObject();
		JSONArray statsData = new JSONArray();
		
		for (Stock s : stocks) 
		{
			analyzeUniqueStrings(s.getUnit(), Unit);
			analyzeUniqueStrings(s.getIndic_nrg(), Indic_nrg);
			analyzeUniqueStrings(s.getGeo(), Geo);
			Product.insertValue(s.getProduct());
			
			List<Integer> times = s.getTime();
			for(int i = 0; i < times.size(); i++)
			{
				Integer time = times.get(i);
				data[i].insertValue(time);
			}
		}
				
		JSONObject analyticsUnit = Utils.getJSONAnalyticsString( Unit, "Unit" );
		JSONObject analyticsGeo = Utils.getJSONAnalyticsString( Geo, "Geo" );
		JSONObject analyticsIndic_nrg = Utils.getJSONAnalyticsString( Indic_nrg, "Indic_nrg" );
			
		List<String> header = ParserTSV.getHeader();
				
		statsData.add(analyticsUnit);
		statsData.add(Product.getJSONAnalyticsNumeric("Product"));
		statsData.add(analyticsIndic_nrg);
		statsData.add(analyticsGeo);

		for(int i=0;i<data.length;i++) 
		{
			statsData.add( data[i].getJSONAnalyticsNumeric( header.get(4+i) ) );	
		}			
		
		
		stats.put("Dati", statsData);
		
		return stats;
	}
	
	/**
	 * Metodo che conta le ripetizioni delle singole stringhe
	 * @param value
	 * @param h hashmap contenente la stringa come chiave e il numero di ripetizioni come valore
	 * @return h hashmap
	 */
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
