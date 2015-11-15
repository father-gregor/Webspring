package com.benlinus92.webspring.dao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="COUNTRY_")
public class CountryCurrency {
	@Column(name="ID")
	private int id;
	@Column(name="DATE")
	private String date;
	@Column(name="CURRENCY")
	private String currency;
	
	public int getId() {
		return this.id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDate() {
		return this.date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getCurrency() {
		return this.currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
}
