package org.univpm.projectoop.utils;
import org.json.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;


public class Utils {

 
    public static JSONObject getJSONFromURL(String url) throws IOException, ParseException {
        StringBuilder sb = new StringBuilder();
        int currentChar;
        try(InputStream is = new URL(url).openStream()) {
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            while ((currentChar = br.read()) != -1){
                sb.append((char) currentChar);
            }
            
            JSONParser parser = new JSONParser();
            JSONObject json = (JSONObject) parser.parse(sb.toString());

            
            return json;
        }
    }

    public static void downloadCSVfromJSON(JSONObject json){
        JSONObject result = (JSONObject) json.get("result");
        JSONArray data = (JSONArray) result.get("resources");
        for(Object resource : data){
            if(resource instanceof JSONObject){
                JSONObject jsonResource = (JSONObject) resource;
                if(((String)jsonResource.get("format")).contains("CSV")){
                    try {
                        downloadCSVFromURL(((String)jsonResource.get("url")));
                    } catch (IOException e) {
                        System.out.println(e.toString());
                    }
                }
            }
        }

    }


    private static void downloadCSVFromURL(String url) throws IOException{
        if(!Files.exists(Paths.get("data.csv"))){
            InputStream is = new URL(url).openStream();
            Files.copy(is, Paths.get("data.csv"), StandardCopyOption.REPLACE_EXISTING);
        } else {
            System.out.println("Il file CSV è già stato scaricato!");
        }
    }

}