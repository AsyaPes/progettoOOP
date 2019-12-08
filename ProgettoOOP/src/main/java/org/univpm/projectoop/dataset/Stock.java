package org.univpm.projectoop.dataset;

import java.security.InvalidKeyException;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.config.TypedStringValue;
import org.univpm.projectoop.exceptions.JSONInvalidKey;
import org.univpm.projectoop.exceptions.JSONInvalidValue;
import org.univpm.projectoop.utils.ParserTSV;
import org.univpm.projectoop.utils.Utils;

/**
 * 
 * @author Asya Pesaresi e Lorenzo Vagnini
 *
 */
public class Stock {

	public String unit;
	public int product;
	public String indic_nrg;
	public String geo;
	public List <Integer> time;
	
	/**
	 * Costruttore della classe Stock
	 * @param unit
	 * @param product
	 * @param indic_nrg
	 * @param geo
	 * @param time
	 */
	public Stock(String unit,int product,String indic_nrg,String geo,List <Integer> time) {
		
		this.unit = unit;
		this.product = product;
		this.indic_nrg = indic_nrg;
		this.geo = geo;
		this.time = time;
	}

	/**
	 * Costruttore della classe Stock
	 */
	public Stock() {
		
	}
	
	/**
	 * Metodo che restituisce unit
	 * @return unit
	 */
	public String getUnit() {
		return unit;
	}
	/**
	 * Metodo che restituisce product
	 * @return product
	 */
	public int getProduct() {
		return product;
	}

	/**
	 * Metodo che restituisce Indic_nrg
	 * @return Indic_nrg
	 */
	public String getIndic_nrg() {
		return indic_nrg;
	}

	/**
	 * Metodo che restituisce Geo
	 * @return Geo
	 */
	public String getGeo() {
		return geo;
	}

	/**
	 * Metodo che restituisce Time
	 * @return Time
	 */
	public List<Integer> getTime() {
		return time;
	}

	/**
	 * Metodo che imposta il valore di unit
	 * @param unit
	 */
	public void setUnit(String unit) {
		this.unit = unit;
	}

	/**
	 * Metodo che imposta il valore di Product
	 * @param product
	 */
	public void setProduct(int product) {
		this.product = product;
	}

	/**
	 * Metodo che imposta il valore di indic_nrg
	 * @param indic_nrg
	 */
	public void setIndic_nrg(String indic_nrg) {
		this.indic_nrg = indic_nrg;
	}

	/**
	 * Metodo che imposta il valore di geo
	 * @param geo
	 */
	public void setGeo(String geo) {
		this.geo = geo;
	}

	/**
	 * Metodo che imposta il valore di time
	 * @param time
	 */
	public void setTime(List<Integer> time) {
		this.time = time;
	}
	
	/**
	 * Metodo che restituisce il valore di una variabile passandogli il suo nome
	 * @param value 
	 * @return null
	 */
	public Object getValueByName(String value)  
	{
		switch(value)
		{
			case "Unit":
				return this.getUnit();
			
			case "Product":
				return this.getProduct();
			
			case "Indic_nrg":
				return this.getIndic_nrg();
			
			case "Geo":
				return this.getGeo();
			
		}
		
		if(ParserTSV.getHeader().contains(value))
		{
			int index = ParserTSV.getHeader().indexOf(value);
			return this.getTime().get(index+4);
		}
		
		return null;
	}
	
	/**
	 * Metodo che applica il filtro passatogli cone argomento
	 * @param JSONFilters
	 * @return true se l'operazione riesce
	 */
	public boolean Filter(JSONObject JSONFilters)
	{
		
		//Scorre le chiavi di tutti i filtri JSON
		for(Object ObjKeyField : JSONFilters.keySet())
		{
			boolean ok = false;
			
			String field = (String) ObjKeyField;
			
			if( (field.equals("$and")) || (field.equals("$or")) )
			{
				//BLOCCO AND e OR
				
				switch(field)
				{
					case "$and":
					{
						ok = true;
						JSONArray filters = (JSONArray)JSONFilters.get(field);
						
						for(Object insideFieldObj : filters) // PER OGNI ELEMENTO DEL JSONArray
						{
							JSONObject insideField = (JSONObject)insideFieldObj;
							
							for(Object insideKey : insideField.keySet())
							{
								String insideFieldKey = (String) insideKey;
								String valore = (String)insideField.get(insideFieldKey);
								
								ok = equalNameControl(insideFieldKey, valore);
								
								if( !ok ) return false;
								
							}
							
						}
	
						break;
					}
						
					case "$or":
					{
						ok = false;
						JSONArray filters = (JSONArray)JSONFilters.get(field);
						
						for(Object insideFieldObj : filters) // PER OGNI ELEMENTO DEL JSONArray
						{
							JSONObject insideField = (JSONObject)insideFieldObj;
							
							for(Object insideKey : insideField.keySet())
							{
								String insideFieldKey = (String) insideKey;
								String valore = (String)insideField.get(insideFieldKey);
								
								ok = equalNameControl(insideFieldKey, valore);
								
								if( ok ) break;
								
							}
							

							if( ok ) break;
	
						}

						if( !ok ) return false;
	
						break;
					}
				
				}
				
				// CICLO PER OGNI CHIAVE -> CONTROLLIAMO I VALUE DEL FILTRO CON I VALORI DELL'OGGETTO CORRENTE
				
			}else{
				
				JSONObject innerObject = (JSONObject)JSONFilters.get(field);
				
				// BLOCCO CAMPI
				if( Utils.getStringFields().contains(field) )
				{
					
					// FOR SULLE KEY di innerObject
					String newfilter;
					
					for(Object filter : innerObject.keySet() )
      				{ 
						newfilter = (String)filter;
					
						// FILTRI SULLE STRINGHE ALL'INTERNO DEL FOR
						switch(newfilter)
						{
							case "$not":
							{
								String stringFilter;
								
								ok = true;
								stringFilter = (String)innerObject.get(newfilter);
								ok = !(this.equalNameControl((String)field, stringFilter));
							
							   if( !ok ) return false;
							}
		
							break;
							
								
							case "$in": 
							{
								String[] stringFilterArr;
								stringFilterArr = (String[])( (JSONArray)innerObject.get(newfilter) ).toArray();
								try {
									ok = this.ContainsNameControl((String)field, stringFilterArr);
								} catch (JSONInvalidValue e) {}
								
								
								if( !ok ) return false;
								break;
							}
		
								
							case "$nin": 
							{
								String[] stringFilterArr;
								try {
										stringFilterArr = (String[])( (JSONArray)innerObject.get(newfilter) ).toArray();
										ok = !(this.ContainsNameControl((String)field, stringFilterArr));
										
								    }catch(JSONInvalidValue e){}
								
								if( !ok ) return false;
								break;
							}
								
							
						}
					}
				}else {
					
					for(Object filter : innerObject.keySet())
					{
						// FILTRI SUI NUMERI ALL'INTERNO DEL FOR
						String newfilter = (String)filter;
						Integer numberFilter;
						
						switch(newfilter)
						{
							case "$lt": 
							{
								numberFilter = (Integer)innerObject.get(newfilter);
								ok = this.LowerNumberControl(field, numberFilter);
								
								if( !ok ) return false;
								break;
							}
								
							case "$lte": 
							{
								numberFilter = (Integer)innerObject.get(newfilter);
								ok = this.LowerNumberControl(field, numberFilter);
								if( !ok ) return false;
								ok = ok || this.equalNameControl(field, numberFilter);
								if(!ok) return false;
								
								break;
							}
								
							case "$gt":
								
							{
								numberFilter = (Integer)innerObject.get(newfilter);
								ok = this.GreaterNumberControl(field, numberFilter);
								
								if( !ok ) return false;
								break;
							}
								
							case "$gte":
							{
								numberFilter = (Integer)innerObject.get(newfilter);
								ok = this.GreaterNumberControl(field, numberFilter);
								if( !ok ) return false;
								ok = ok || this.equalNameControl(field, numberFilter);
								if(!ok) return false;
							
								break;
							}
								
							case "$bt": 
							{ 
								JSONArray numbervalues;
								Object[] arrayObj;
								Integer[] filterNumberRange;
								
								numbervalues = (JSONArray)innerObject.get(newfilter);
								arrayObj = numbervalues.toArray();
								filterNumberRange = Arrays.copyOf(arrayObj, arrayObj.length, Integer[].class);
								
								ok = BetweenNumberControl(field,filterNumberRange[0],filterNumberRange[1]);
								
								if( !ok ) return false;

								break;
								
				             }
					
						}
						
					}
					
						
				}
			}
			
		}
		
		return true;
}
	
	/**
	 * Metodo che controlla che un numero sia compreso tra 2 valori dati
	 * @param field
	 * @param number_1
	 * @param number_2
	 * @return true se la condizione è soddisfatta
	 */
	private boolean BetweenNumberControl(String field, Object number_1, Object number_2)
	{
	
		Integer min, max;

		min = Math.min((Integer)number_1, (Integer)number_2);
		max = Math.max((Integer)number_1, (Integer)number_2);
		
		return this.GreaterNumberControl(field, min)
			&& this.LowerNumberControl(field, max)
			|| this.equalNameControl(field, min)
			|| this.equalNameControl(field, max);
	}
	
	/**
	 * Metodo che controlla che il numero passato sia maggiore di quello dato
	 * @param variable
	 * @param valueToCheck
	 * @return true se la condizione è soddisfatta
	 */
	private boolean GreaterNumberControl(String variable, Object valueToCheck)
	{
		List<String> header = Utils.getNumericFields();
		
		List<Integer> timeList = this.getTime();
		
		for(String timeName : header)
		{
			if(timeName.equals(variable))
			{
				Integer tmp = header.indexOf(variable);
				return (timeList.get(tmp)>(Integer)valueToCheck);
			}
		}
		return false;
	}

	/**
	 * Metodo che controlla che il numero passato sia minore di quello dato
	 * @param variable
	 * @param valueToCheck
	 * @return true se la condizione è soddisfatta
	 */
	private boolean LowerNumberControl(String variable, Object valueToCheck)
	{

		List<String> header = Utils.getNumericFields();
		
		List<Integer> timeList = this.getTime();
		
		for(String timeName : header)
		{
			if(timeName.equals(variable))
			{
				Integer tmp = header.indexOf(variable);
				return (timeList.get(tmp)<(Integer)valueToCheck);
			}
		}
		return false;
	}

	/**
	 *
	 * @param variable
	 * @param valueToCheck
	 * @return
	 * @throws JSONInvalidValue
	 */
	private boolean ContainsNameControl(String variable, Object valueToCheck) throws JSONInvalidValue {
	
		List<String> validFields = Utils.getStringFields();
		
		if(!(validFields.contains(valueToCheck)))
		{
			throw new JSONInvalidValue();
		}
		
		List<String> str = Arrays.asList((String[])valueToCheck);
		switch(variable)
		{
		
			case "Unit": return (valueToCheck instanceof String[] && (str.contains(this.getUnit())));
			
			case "Indic_nrg": return(valueToCheck instanceof String[] && (str.contains(this.getUnit())));
			
			case "Geo": return(valueToCheck instanceof String[] && (str.contains(this.getUnit())));
		
		}
		return false;
	}

	/**
	 * 
	 * @param variable
	 * @param valueToCheck
	 * @return
	 */
	private boolean equalNameControl(String variable, Object valueToCheck)  {
		
		List<String> validFields = Utils.getValidFields();
		if(!(validFields.contains(variable)))
		{
			return false;
		}
		
		switch(variable)
		{
			
			case "Unit": return (valueToCheck instanceof String && ((String)valueToCheck).equals(this.getUnit()));
			
			case "Indic_nrg": return(valueToCheck instanceof String && ((String)valueToCheck).equals(this.getUnit()));
			
			case "Geo": return(valueToCheck instanceof String && ((String)valueToCheck).equals(this.getUnit()));
		
		}
		
		if(!(valueToCheck instanceof Integer))
		{
			return false;
		}
		
		List<String> header = ParserTSV.getHeader();
		List<Integer> timeList = this.getTime();
		
		for(String timeName : header)
		{
			if(timeName.equals(variable))
			{
				Integer tmp = header.indexOf(variable);
				return (timeList.get(tmp)==(Integer)valueToCheck);
			}
		}
			return false;
	}
}
