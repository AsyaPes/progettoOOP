package org.univpm.projectoop.utils;
import org.json.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;


public class Utils {
	
	public static URLConnection openConnection;
	public static InputStream in;

    public static void openConnection() throws IOException
    {
		openConnection = new URL("https://ec.europa.eu/eurostat/estat-navtree-portlet-prod/BulkDownloadListing?file=data/nrg_ind_342m.tsv.gz&unzip=true").openConnection();
		in = openConnection.getInputStream();
    }
 
	public static void closeConnection() throws IOException
	{
		in.close();
	}

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


    private static void downloadTSVFromURL(String url) throws IOException{
        if(!Files.exists(Paths.get("data.tsv"))){
        	
        	URL urlObject = new URL(url);
        	HttpURLConnection connection = (HttpURLConnection)urlObject.openConnection();
        	HttpURLConnection.setFollowRedirects(true);
            InputStream is = connection.getInputStream();
            
            Files.copy(is, Paths.get("data.tsv"), StandardCopyOption.REPLACE_EXISTING);
            System.out.println("FILE SCARICATO");
        } else {
            System.out.println("Il file TSV è già stato scaricato!");
        }
    }
}

