package org.univpm.projectoop.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList; // importa la classeArrayList
import java.util.List;

import org.univpm.projectoop.dataset.Stock;

/**
 * Classe astratta contenente i metodi per il parsing dei dati
 * @author Asya Pesaresi e Lorenzo Vagnini
 *
 */
public abstract class ParserTSV {
	
	private static List<Stock> data = new ArrayList<Stock>();
	
	/**
	 * Metodo che restituisce data
	 * @return data
	 */
	public static List<Stock> getData() {
		return data;
	}

	private static List<String> header = new ArrayList<String>();
	
	/**
	 * Metodo che restituisce header
	 * @return header
	 */
	public static List<String> getHeader() {
		return header;
	}

	/**
	 * Metodo che imposta il valore di header
	 * @param header
	 */
	public static void setHeader(List<String> header) {
		ParserTSV.header = header;
	}
	/**
	 * 
	 * @param s
	 */
	public static void addStock(Stock s) {
		data.add(s);
	}
	/**
	 * 
	 * @throws IOException
	 */
	public static void parserDataSet () throws IOException{
		
	BufferedReader TSVFile = new BufferedReader(new FileReader("data.tsv"));
	String dataRow = TSVFile.readLine(); // Legge la prima linea
	String[] dataArray = dataRow.split("[,\\t]");
	for (String h : dataArray) {
		
		h=h.trim(); //Trim rimuove spazi tab e a capo all'inizio e alla fine
		header.add(h);
	}
	
	dataRow = TSVFile.readLine(); // Legge la seconda linea
	
	while (dataRow != null)
	{
		dataArray = dataRow.split("[,\\t]");
		
		if(dataArray.length != header.size()) {
			System.out.println("La riga non contiene il giusto numero di dati: " + dataRow.trim());
			dataRow = TSVFile.readLine(); // Legge la seconda linea
			continue;
		}
		
		for(int i=0;i< dataArray.length;i++)
		{
			//se il campo è : allora sostituisco quel valore (di posizione i) "0"
			String tmp = dataArray[i].trim();
			if(tmp.equals(":"))
				dataArray[i] = "0";
			else
				dataArray[i] = tmp;
		}
		
		Stock s = new Stock();
		List<Integer> DataTime= new ArrayList<Integer>();
		
		s.setUnit(dataArray[0]);
		s.setProduct(Integer.parseInt(dataArray[1]) );
		s.setIndic_nrg(dataArray[2]);
		s.setGeo(dataArray[3]);
		
		for (int i=0 ; i<header.size()-4;i++)
		{
			DataTime.add(Integer.parseInt(dataArray[4+i]));
		}
		
		s.setTime(DataTime);
		
		addStock(s);
		
		dataRow = TSVFile.readLine(); // Read next line of data.
	}
	// Close the file once all data has been read.
	TSVFile.close();


	 }
}
