package org.univpm.projectoop.utils;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.univpm.projectoop.dataset.Stock;

public class FilterString {
	private String text;
	private int count;
	private int singular;
	private float percent;
	
	
	public FilterString(ArrayList<Stock> Stocks,String text,String field) throws Exception {
		
		this.count=Stocks.size();
		this.text=text;
		
		if(count==0) {
			throw new IOException("Errore, non ci sono dati corrispondenti ai filtri");
		}
		
		
		for(int i=0;i<count;i++) {
			
			Method m = null;
			try {
				m = Stock.class.getMethod("get"+field.substring(0, 1).toUpperCase()+field.substring(1),null);
                Object string = m.invoke(Stocks.get(i));
                if((text.equals((String) string))){
                    singular++;
                }
            } catch (ClassCastException e) {
                e.printStackTrace();
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Il formato non è corretto"); 
            }
        }
        this.percent = (float) singular/count;
    }
}

										

