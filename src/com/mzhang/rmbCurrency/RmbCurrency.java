package com.mzhang.rmbCurrency;

public class RmbCurrency {
	//YYYY-MM-DD
	private String date;
	
	//three letters e.g. USD
	private Currency currency;
	
	private Float govIntermediatePrice;
	
	public RmbCurrency(String date, Currency currency, Float govIntermediatePrice){
		this.date = date;
		this.currency = currency;
		this.govIntermediatePrice = govIntermediatePrice;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	public Float getGovIntermediatePrice() {
		return govIntermediatePrice;
	}

	public void setGovIntermediatePrice(Float govIntermediatePrice) {
		this.govIntermediatePrice = govIntermediatePrice;
	}
	
	
}
