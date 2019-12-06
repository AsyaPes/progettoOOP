package org.univpm.projectoop.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


public class FilterNumber {
	
	private double avg;
	private int sum;
	private int min;
	private int max;
	private double dev_std;
	private int count;

	private List<Integer> stocksData;
	
	public FilterNumber ()
	{
		this.avg=0;
		this.count=0;
		this.dev_std=0;
		this.sum=0;	
		this.stocksData= new ArrayList<Integer>();
	}
	
 
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



	public double getAvg() {
		return avg;
	}


	private void setAvg(double avg) {
		this.avg = avg;
	}
  
	public void setAvg() {
		setAvg((double)sum/count);
	}
	

	public int getSum() {
		return sum;
	}

   private void setSum(Integer value) //Somma un valore
   {
	   sum+=value;
   }
   
	public int getMin() {
		return min;
	}


	private void setMin(int min) {
		this.min = min;
	}
	

	public int getMax() {
		return max;
	}


	private void setMax(int max) {
		this.max = max;
	}


	public double getDev_std() {
		calculateDev_Std();
		return dev_std;
	}


	private void calculateDev_Std() {
		double sum = 0;
        for (int v : this.stocksData) {
            sum += Math.pow(v - avg, 2);
        }
        
        setDev_std((double) Math.sqrt(sum/count));     
	}


	private void setDev_std(double dev_std) {
		this.dev_std = dev_std;
	}


	public int getCount() {
		return count;
	}

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
