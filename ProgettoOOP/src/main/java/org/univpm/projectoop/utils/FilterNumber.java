package org.univpm.projectoop.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * Classe dedicata alla gestione delle statistiche di tipo numerico sui dati
 * @author Asya Pesaresi e Lorenzo Vagnini
 *
 */
public class FilterNumber {
	
	private double avg;
	private int sum;
	private int min;
	private int max;
	private double dev_std;
	private int count;

	private List<Integer> stocksData;
	
	/**
	 * Costruttore della classe FilterNumber
	 */
	public FilterNumber ()
	{
		this.avg=0;
		this.count=0;
		this.dev_std=0;
		this.sum=0;	
		this.stocksData= new ArrayList<Integer>();
	}
	
	/**
	 * Metodo che aggiunge un nuovo valore ala lista 
	 * aggiornando somma, contatore, min e max dei valori inseriti
	 * @param field
	 */
	public void insertValue(Integer field)
	{
		this.stocksData.add(field);
		this.setSum(field);
		this.count++;
		
		if(this.count == 1) 
		{
			this.setMin(field);
			this.setMax(field);
		}
			else 
			{
				this.setMin(Math.min(field,this.min));
				this.setMax(Math.max(field,this.max));
			}
		
		setAvg();
	}

	/**
	 * Metodo che restituisce avg
	 * @return avg Media
	 */
	public double getAvg() {
		return avg;
	}

	/**
	 * Metodo che imposta il valore di avg
	 * @param avg Media
	 */
	private void setAvg(double avg) {
		this.avg = avg;
	}
	
	/**
	 * Metodo che calcola la media 
	 */
	public void setAvg() {
		setAvg((double)sum/count);
	}
	
	/**
	 * Metodo che restituisce sum
	 * @return sum Somma 
	 */
	public int getSum() {
		return sum;
	}
	
	/**
	 * Metodo che aggiorna la somma dopo aver aggiunto un valore
	 * @param value
	 */
	private void setSum(Integer value)
	{
		sum+=value;
	}
   
	/**
	 * Metodo che restituisce min
	 * @return min Minimo
	 */
	public int getMin() {
		return min;
	}

	/**
	 * Metodo che imposta il valore di min
	 * @param min
	 */
	private void setMin(int min) {
		this.min = min;
	}
	
	/**
	 * Metodo che restituisce max
	 * @return max Massimo
	 */
	public int getMax() {
		return max;
	}

	/**
	 * Metodo che imposta il valore di max
	 * @param max
	 */
	private void setMax(int max) {
		this.max = max;
	}

	/**
	 * Metodo che restiuisce dev_std
	 * @return dev_std Deviazione Standard
	 */
	public double getDev_std() {
		calculateDev_Std();
		return dev_std;
	}

	/**
	 * Metodo che calcola la dev_std
	 * Deviazione Standard
	 */
	private void calculateDev_Std() {
		double sum = 0;
        for (int v : this.stocksData) {
            sum += Math.pow(v - avg, 2);
        }
        
        setDev_std((double) Math.sqrt(sum/count));     
	}

	/**
	 * Metodo che imposta la dev_std
	 * @param dev_std
	 */
	private void setDev_std(double dev_std) {
		this.dev_std = dev_std;
	}

	/**
	 * Metodo che restituisce conut
	 * @return count
	 */
	public int getCount() {
		return count;
	}

	/**
	 * Metodo che restituisce le stats sotto forma di oggetto JSON
	 * @param field Attributo di tipo stringa
	 * @return numDataObject JSONObject contenente le stats
	 */
	public JSONObject getJSONAnalyticsNumeric( String field )
	{
		JSONObject numData = new JSONObject();
		JSONArray json = new JSONArray();
		
		JSONObject numDataObject = new JSONObject();
		
		numDataObject.put("Attributo", field );
		
		numData.put("Somma", this.getSum() );
		numData.put("Massimo", this.getMax() );
		numData.put("Minimo", this.getMin() );
		numData.put("DeviazioneStandard", this.getDev_std() );
		numData.put("Conteggio", this.getCount() );
		numData.put("Media", this.getAvg() );
		
		numDataObject.put("Dati", numData);
		numDataObject.put("Tipo", "Integer");
		
		return numDataObject;
	}

}
