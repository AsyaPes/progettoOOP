  
package com.project.OOP;

import com.project.OOP.utils.CSVDownloader;
import org.json.JSONObject;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;


public class StartApp {
	public static void main(String[] args) {
		try {
			JSONObject obj = CSVDownloader.getJSONFromURL("https://ec.europa.eu/eurostat/estat-navtree-portlet-prod/BulkDownloadListing?file=data/nrg_ind_342m.tsv.gz&unzip=true");
			CSVDownloader.downloadCSVfromJSON(obj);
		} catch (IOException e){
			System.out.println(e);
		}
		SpringApplication.run(OopApplication.class, args);
	}
}
