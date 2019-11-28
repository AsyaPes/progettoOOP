package org.univpm.projectoop.dataset;

import java.util.List;

public class Stock {

	public String unit;
	public int product;
	public String indic_nrg;
	public String geo;
	public List <Integer> time;
	
	public Stock(String unit,int product,String indic_nrg,String geo,List <Integer> time) {
		
		this.unit = unit;
		this.product = product;
		this.indic_nrg = indic_nrg;
		this.geo = geo;
		this.time = time;
	}

	public Stock() {
		// TODO Auto-generated constructor stub
	}

	public String getUnit() {
		return unit;
	}

	public int getProduct() {
		return product;
	}

	public String getIndic_nrg() {
		return indic_nrg;
	}

	public String getGeo() {
		return geo;
	}

	public List<Integer> getTime() {
		return time;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public void setProduct(int product) {
		this.product = product;
	}

	public void setIndic_nrg(String indic_nrg) {
		this.indic_nrg = indic_nrg;
	}

	public void setGeo(String geo) {
		this.geo = geo;
	}

	public void setTime(List<Integer> time) {
		this.time = time;
	}
	
}
