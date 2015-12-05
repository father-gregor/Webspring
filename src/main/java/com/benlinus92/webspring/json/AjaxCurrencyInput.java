package com.benlinus92.webspring.json;

public class AjaxCurrencyInput {
	private String countryid1;
	private String countryid2;
	private String date;
	
	public String getCountryId1() {
		return this.countryid1;
	}
	public void setCountryId1(String countryId1) {
		this.countryid1 = countryId1;
	}
	public String getCountryId2() {
		return this.countryid2;
	}
	public void setCountryId2(String countryId2) {
		this.countryid2 = countryId2;
	}
	public String getDate() {
		return this.date;
	}
	public void getDate(String date) {
		this.date = date;
	}
}
