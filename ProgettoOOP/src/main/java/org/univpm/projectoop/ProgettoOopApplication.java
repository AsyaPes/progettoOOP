package org.univpm.projectoop;

import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.univpm.projectoop.utils.ParserTSV;
import org.univpm.projectoop.utils.Utils;

/**
 * Applicazione principale per avviare il progetto
 * @author Asya Pesaresi e Lorenzo Vagnini
 *
 */
@SpringBootApplication
public class ProgettoOopApplication {

	/**
	 * Metodo Main
	 * @param args
	 */
	public static void main(String[] args)
	{

		
		try {
			Utils.openConnection();
			
			JSONObject obj = Utils.getJSONFromURL("http://data.europa.eu/euodp/data/api/3/action/package_show?id=BKQIqLLiRs3MtFB12nrLpA");
			Utils.downloadTSVfromJSON(obj);
			
			ParserTSV.parserDataSet();

			Utils.closeConnection();
		} catch (IOException e) {
			
			System.err.println(e);
			return;
			
		} catch (ParseException e) {

			System.err.println(e);
			return;
		}
		
		SpringApplication.run(ProgettoOopApplication.class, args);
	}

}
