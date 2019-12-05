package org.univpm.projectoop.dataset;

import java.security.InvalidKeyException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.config.TypedStringValue;
import org.univpm.projectoop.exceptions.JSONInvalidKey;
import org.univpm.projectoop.exceptions.JSONInvalidValue;
import org.univpm.projectoop.utils.ParserTSV;
import org.univpm.projectoop.utils.Utils;

public class Stock {

	public String unit;
	public int product;
	public String indic_nrg;
	public String geo;
	public List <Integer> time;
	
	public Stock(String unit,int product,String indic_nrg,String geo,List <Integer> time) {
		
		this.unit = unit;
		this.product = product;
		this.indic_nrg = indic_nrg;
		this.geo = geo;
		this.time = time;
	}

	public Stock() {

	}

	public String getUnit() {
		return unit;
	}

	public int getProduct() {
		return product;
	}

	public String getIndic_nrg() {
		return indic_nrg;
	}

	public String getGeo() {
		return geo;
	}

	public List<Integer> getTime() {
		return time;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public void setProduct(int product) {
		this.product = product;
	}

	public void setIndic_nrg(String indic_nrg) {
		this.indic_nrg = indic_nrg;
	}

	public void setGeo(String geo) {
		this.geo = geo;
	}

	public void setTime(List<Integer> time) {
		this.time = time;
	}
	
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
	
	public boolean Filter(JSONObject JSONFilters)
	{
		//Scorre le chiavi di tutti i filtri JSON
		for(Object ObjKeyField : JSONFilters.keySet())
		{
			String field=(String) ObjKeyField;
			List<String> validFields= Utils.getValidFields();
			
			if( (field.equals("$and")) || (field.equals("$or")) )
			{
				//BLOCCO AND e OR
				
				switch(field)
				{
					case "$and":
						
						boolean ok = true;
						// for() // PER OGNI ELEMENTO DEL JSONArray //Controllare che Status sia uguale a G
						{
							
							
							try {
								ok = equalNameControl(nomeVariabile, valore);
							}catch(InvalidKeyException e){}
							
							if( !ok ) return false;
						}
	
						break;
						
					case "$or":
						
						boolean ok = false;
						// for() // PER OGNI ELEMENTO DEL JSONArray
						{

							try {
								ok = equalNameControl(nomeVariabile, valore);
							}catch(InvalidKeyException e){}
							
							if( ok ) break;
	
						}

						if( !ok ) return false;
	
						break;
				
				}
				
				// CICLO PER OGNI CHIAVE -> CONTROLLIAMO I VALUE DEL FILTRO CON I VALORI DELL'OGGETTO CORRENTE
				
			}else {
				
				JSONObject innerObject = (JSONObject)JSONFilters.get(field);
				
				// BLOCCO CAMPI
				if( field.equals("Unit") ||
					field.equals("Indic_nrg") ||
					field.equals("Geo"))
				{
					
					// FOR SULLE KEY di innerObject
					//for()
					{
						// FILTRI SULLE STRINGHE ALL'INTERNO DEL FOR
						//switch()
						{
							case "$not": 
								//
								break;
								
							case "$in": 
								//
								break;
								
							case "$nin": 
								//
								break;
								
							
						}
						
					}
					
				}else {
					
					// FOR SULLE KEY di innerObject
					//for()
					{
						// FILTRI SUI NUMERI ALL'INTERNO DEL FOR
						//switch()
						{
							case "$lt": 
								//
								break;
								
							case "$lte": 
								//
								break;
								
							case "$gt": 
								//
								break;
								
							case "$gte": 
								//
								break;
								
							case "$bt": 
								//
								break;
								
							
						}
						
					}
					
						
				}
			}
			
		}
		
		return true;
	}
	
	
	

	private boolean equalNameControl(String variable, Object valueToCheck) throws JSONInvalidValue {
		
		List<String> validFields = new ArrayList<String>();
		validFields = Utils.getValidFields();
		if(!(validFields.contains(variable)))
		{
			throw new JSONInvalidValue();
		}
		
		switch(variable)
		{
		
		case "Unit": return (valueToCheck instanceof String && ((String)valueToCheck).equals(this.getUnit()));
		
		case "Product": return (valueToCheck instanceof String && ((String)valueToCheck).equals(this.getProduct()));
	
		case "Indic_nrg": return(valueToCheck instanceof String && ((String)valueToCheck).equals(this.getUnit()));
		
		case "Geo": return(valueToCheck instanceof String && ((String)valueToCheck).equals(this.getUnit()));
		
		}
		
		if(!(valueToCheck instanceof Integer))
		{
			throw new JSONInvalidValue();
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

