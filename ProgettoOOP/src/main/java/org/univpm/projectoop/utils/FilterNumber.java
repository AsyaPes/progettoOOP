package org.univpm.projectoop.utils;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.univpm.projectoop.dataset.Stock;

public class FilterNumber {
	
	private double avg;
	private double sum;
	private double min;
	private double max;
	private double dev_std;
	private int count;

	
	 private static boolean isInteger(String field) {
        try {
            Integer.parseInt(field);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
 
	public FilterNumber (ArrayList<Stock> Stocks,String field) throws Exception
	{
		double[] value_array=new double [count];
		this.count=Stocks.size();
		if( count == 0 )
		{
			throw new IOException("Errore, non ci sono dati corrispondenti ai filtri");
			
		}
		else
		{
			
			for(int i=0;i<count;i++)	
			{
			    Method m=null;
			    try {
	                m = Stock.class.getMethod("get"+field.substring(0, 1).toUpperCase()+field.substring(1),null);
	                Object doubleValue = m.invoke(Stocks.get(i));
	                value_array[i] = (double) doubleValue;
	            }catch (ClassCastException e) {
	                e.printStackTrace();
	                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Il formato non è corretto"); 
	            }

			}
		}

	//set
/*
 	    setAvg(value_array);
		setSum(value_array);
		setMin(value_array);
		setMax(value_array);
		setDev_std(value_array);
	*/	
	}


	public double getAvg() {
		return avg;
	}


	public void setAvg(double avg) {
		this.avg = avg;
	}
  
	public void setAvg() {
		setAvg(sum/count);
	}
	

	public double getSum() {
		return sum;
	}


	public void setSum(double sum) {
		this.sum = sum;
	}

   private void setSum(double[] value_array)
   {
	   for(double v:value_array)
	   {
		   sum+=v;
	   }
   }
   
	public double getMin() {
		return min;
	}


	public void setMin(double min) {
		this.min = min;
	}

	private void setMin(double[]value_array)
	{
		double min=value_array[0];
		for (int i=1;i<value_array.length;i++)
		{
			if( value_array[i]<min)
			{
				min=value_array[i];
			}
		}
		
		setMin(min);
	}

	public double getMax() {
		return max;
	}


	public void setMax(double max) {
		this.max = max;
	}

	private void setMax(double[]value_array)
	{
		double max=value_array[0];
		for (int i=1;i<value_array.length;i++)
		{
			if( value_array[i]>max)
			{
				max=value_array[i];
			}
		}
		
		setMax(max);
	}

	public double getDev_std() {
		return dev_std;
	}


	public void setDev_std(double dev_std) {
		this.dev_std = dev_std;
	}

	private void setDev_std(double[] value_array) {
        double sum = 0;
        for (double v : value_array) {
            sum += Math.pow(v - avg, 2);
        }
        
        setDev_std((double) Math.sqrt(sum/count));     
    }

	public int getCount() {
		return count;
	}


	public void setCount(int count) {
		this.count = count;
	}
	
	
	

}
