package com.benlinus92.webspring.dao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="CURRENCY_LIST")
public class CurrencyNameList {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ID")
	private int id;
	@Column(name="CURRENCY", nullable=false)
	private String currencyId;
	@Column(name="COUNTRY_EN", nullable=false)
	private String country;
	public CurrencyNameList() {}
	
	public int getId() {
		return this.id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCurrencyId() {
		return this.currencyId;
	}
	public void setCurrencyId(String currency) {
		this.currencyId = currency;
	}
	public String getCountry() {
		return this.country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
}
