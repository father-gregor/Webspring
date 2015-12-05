package com.benlinus92.webspring.json;

public class DateCurrency {
	private String currency;
	private String date;
	
	public String getCurrency() {
		return this.currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getDate() {
		return this.date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String toString() {
		return this.currency + " - " + this.date;
	}
}
