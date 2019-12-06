package org.univpm.projectoop.utils;
import org.json.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.univpm.projectoop.exceptions.JSONInvalidKey;
import org.univpm.projectoop.exceptions.JSONInvalidValue;
import org.univpm.projectoop.exceptions.JSONInvalidFilter;
import org.univpm.projectoop.exceptions.JSONParsingError;

import java.awt.List;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.KeyException;
import java.util.ArrayList;
import java.util.Map;

/**
 * 
 * @author Asya Pesaresi e Lorenzo Vagnini
 *
 */
public class Utils {
	
	public static URLConnection openConnection;
	public static InputStream in;
	
	/**
	 * 
	 * @throws IOException
	 */
    public static void openConnection() throws IOException
    {
		openConnection = new URL("https://ec.europa.eu/eurostat/estat-navtree-portlet-prod/BulkDownloadListing?file=data/nrg_ind_342m.tsv.gz&unzip=true").openConnection();
		in = openConnection.getInputStream();
    }
 
    /**
     * 
     * @throws IOException
     */
	public static void closeConnection() throws IOException
	{
		in.close();
	}

	/**
	 * 
	 * @param url
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 */
    public static JSONObject getJSONFromURL(String url) throws IOException, ParseException {
        StringBuilder sb = new StringBuilder();
        int currentChar;
        try(InputStream is = new URL(url).openStream()) {
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            //ReadLine
            while ((currentChar = br.read()) != -1){
                sb.append((char) currentChar);
            }
            
            JSONParser parser = new JSONParser();
            JSONObject json = (JSONObject) parser.parse(sb.toString());

            
            return json;
        }
    }

    /**
     * 
     * @param json
     */
    public static void downloadTSVfromJSON(JSONObject json){
        JSONObject result = (JSONObject) json.get("result");
        JSONArray data = (JSONArray) result.get("resources");
        for(Object resource : data){
            if(resource instanceof JSONObject){
                JSONObject jsonResource = (JSONObject) resource;
                if(((String)jsonResource.get("format")).contains("TSV")){
                    try {
                        downloadTSVFromURL(((String)jsonResource.get("url")));
                    } catch (IOException e) {
                        System.out.println(e.toString());
                    }
                }
            }
        }
    }

    /**
     * 
     * @param url
     * @throws IOException
     */
    private static void downloadTSVFromURL(String url) throws IOException{
        if(!Files.exists(Paths.get("data.tsv"))){
        	
        	URL urlObject = new URL(url);
        	HttpURLConnection connection = (HttpURLConnection)urlObject.openConnection();
        	
        	url = connection.getHeaderField("Location");
        	urlObject = new URL(url);
        	connection = (HttpURLConnection)urlObject.openConnection();
        	
        	InputStream is = connection.getInputStream();
            
            Files.copy(is, Paths.get("data.tsv"), StandardCopyOption.REPLACE_EXISTING);
            System.out.println("FILE SCARICATO");
        } else {
            System.out.println("Il file TSV è già stato scaricato!");
        }
    }

    /**
     * 
     * @param stringa
     * @return
     * @throws JSONParsingError
     */
	public static JSONObject parseJSONString(String stringa) throws JSONParsingError
	{
		JSONParser p = new JSONParser();
		JSONObject parsedJSON = new JSONObject();
		
		if(stringa != null)
		{
			try {
				parsedJSON = (JSONObject) p.parse(stringa);
			} catch (ParseException e) {
				throw new JSONParsingError();
			}

			return parsedJSON;
		}

		return null;
	}
	
	/**
	 * 
	 * @return
	 */
	//lista campi numerici
	public static ArrayList<String> getNumericFields()
	{	
		ArrayList<String> validFields = new ArrayList<String>();
		validFields.add("Product");
		for(int n1 = 4; n1 < ParserTSV.getHeader().size() ; n1++)
		{
			validFields.add( String.valueOf(ParserTSV.getHeader().get(n1)) );	
		}
		return validFields;
	}
	
	/**
	 * 
	 * @return
	 */
	//lista campi stringhe
	public static ArrayList<String> getStringFields()
	{	
		ArrayList<String> validFields = new ArrayList<String>();
		validFields.add("Unit");
		validFields.add("Indic_nrg");
		validFields.add("Geo");
		return validFields;
	}
	
	/**
	 * 
	 * @return
	 */
	//lista campi validi
	public static ArrayList<String> getValidFields()
	{	
		ArrayList<String> validFields = new ArrayList<String>();
		validFields.add("Unit");
		validFields.add("Product");
		validFields.add("Indic_nrg");
		validFields.add("Geo");
		for(int n1 = 4; n1 < ParserTSV.getHeader().size() ; n1++)
		{
			validFields.add( String.valueOf(ParserTSV.getHeader().get(n1)) );	
		}
		return validFields;
	}
	
	/**
	 * 
	 * @param filterJSON
	 * @throws JSONInvalidKey
	 * @throws JSONInvalidValue
	 */
	public static void checkFilterJSON(JSONObject filterJSON) throws JSONInvalidKey, JSONInvalidValue
	{
		ArrayList<String> validFields = Utils.getValidFields();
		JSONObject dataFilteredJSON = new JSONObject();
		Object[] keys = filterJSON.keySet().toArray();
	
		for(int i=0; i< keys.length;i++)
		{
			Object k = keys[i];
			
			if(k instanceof String && validFields.contains(((String)k)))
			{
				// Chiave valida, controllo il valore
					
					Object filterValueObj = filterJSON.get(k);
					
					if(!(filterValueObj instanceof JSONObject))
						throw new JSONInvalidValue();
					
					JSONObject filterValue = (JSONObject)( filterValueObj );

					Object[] keys2 = filterValue.keySet().toArray();
					
					for( int j = 0; j < keys2.length; j++ )
					{
						Object key2 = keys2[j];
						Object value2 = filterValue.get(key2);
						
						if(((String)k).equals("Unit")
								|| ((String)k).equals("Indic_nrg")
								|| ((String)k).equals("Geo"))
						{
							Utils.checkSingleFilterString(key2, value2);
						}else
						{
							Utils.checkSingleFilterNumber(key2, value2);
						}
						
						
										
					}
			
				
				
				// $or e $and
			}else if(k instanceof String && (((String)k).equals("$or") || ((String)k).equals("$and")) )
			{
						
						if(! (filterJSON.get(k) instanceof JSONArray ) )	
						{
							throw new JSONInvalidValue();
						}
						
						JSONArray ArrayfilterJSON = (JSONArray) filterJSON.get(k);
						
						
						Object[] elementsInside = ArrayfilterJSON.toArray();
						
						for( int j = 0; j < elementsInside.length; j++ )
						{
							Object elementInside = elementsInside[j];
							JSONObject inElement = (JSONObject)elementInside;
							
							// keysInside contiene il nome degli attributi
							Object[] keysInside = inElement.keySet().toArray();
							
							for(int l=0;l<keysInside.length;l++)
							{
								Object attributeObj = keysInside[l];
								Object valueObj = inElement.get(attributeObj);
																
								Utils.checkLogicalFilter(attributeObj, valueObj);
	
							}
							
						}
						
			}
			
			throw new JSONInvalidKey();
						
		}

	}
	
	/**
	 * 
	 * @param key
	 * @param value
	 * @throws JSONInvalidKey
	 * @throws JSONInvalidValue
	 */
	private static void checkSingleFilterString(Object key, Object value) throws JSONInvalidKey, JSONInvalidValue {
		if(key instanceof String ) 
		{
			switch((String)key) 
			{
				case "$in":
				case "$nin":
					if( value instanceof JSONArray )
					{						
						for (int i=0; i<((JSONArray)value).size();i++) 
						{
							if(!(((JSONArray)value).get(i) instanceof String ) ) 
							{
								break;
							}
						}
						return;
					}
					break;
				case "$not":
					if(value instanceof String)
					{
						return;
					}
			}
			
			throw new JSONInvalidValue();
			
		}

		throw new JSONInvalidKey();
		
	}
	
	/**
	 * 
	 * @param key
	 * @param value
	 * @throws JSONInvalidKey
	 * @throws JSONInvalidValue
	 */
	private static void checkSingleFilterNumber(Object key, Object value) throws JSONInvalidKey, JSONInvalidValue {
		if(key instanceof String ) 
		{
			switch((String)key) 
			{
				case "$gt":
				case "$gte":
				case "$lt":
				case "$lte":
					if(value instanceof Number) 
					{
						return;
					}
					break;
					
				case "$bt":
					if(value instanceof JSONArray
							&& ((JSONArray)value).size() == 2
							&& ((JSONArray)value).get(0) instanceof Number 
							&& ((JSONArray)value).get(1) instanceof Number ) 
						{return;}
					break;
				case "$not":
					if(value instanceof Number)
					{
						return;
					}
					break;
			}
			
			throw new JSONInvalidValue();
			
		}

		throw new JSONInvalidKey();
		
	}
	
	/**
	 * 
	 * @param key
	 * @param value
	 * @throws JSONInvalidKey
	 * @throws JSONInvalidValue
	 */
	private static void checkLogicalFilter(Object key, Object value) throws JSONInvalidKey, JSONInvalidValue
	{
		if(key instanceof String ) 
		{
			if( getStringFields().contains((String)key) )
			{
				// Stringa
				if( value instanceof String ) return;
			}else
			{
				// Numero
				if( value instanceof Number ) return;
			}
			
			throw new JSONInvalidValue();
		
		}
		throw new JSONInvalidKey();
	}
	
	/**
	 * 
	 * @param ht
	 * @param field
	 * @return
	 */
	// restituisce un oggetto JSONArray contenente i dati analitici dell'hashmap passatagli come argomento
	public static JSONObject getJSONAnalyticsString( Map<String, Integer> ht, String field )
	{
		JSONObject finalJSON = new JSONObject();
		JSONArray json = new JSONArray();
		
		for (String uniqueString : ht.keySet() )
		{
			int uniqueStringCount = ht.get(uniqueString);
			JSONObject jobj = new JSONObject ();
			jobj.put("Valore", uniqueString);
			jobj.put("Contatore", uniqueStringCount);
			json.add(jobj);
		}
		
		finalJSON.put("Tipo", "String");
		finalJSON.put("Attributo", field);
		finalJSON.put("Dati", json);
		
		return finalJSON;
	}
}
	