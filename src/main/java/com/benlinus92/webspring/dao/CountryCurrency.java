package com.benlinus92.webspring.dao;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="USD")
public class CountryCurrency {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ID")
	private int id;
	@Column(name="COUNTRY", nullable=false)
	private String country;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@Temporal(TemporalType.DATE)
	@Column(name="CURR_DATE", nullable=false)
	private Calendar currDate;
	@Column(name="CURRENCY", nullable=false)
	private String currency;
	
	public CountryCurrency(String country, String currency, Calendar date) {
		this.country = country;
		this.currency = currency;
		this.currDate = date;
	}
	
	public int getId() {
		return this.id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCountry() {
		return this.country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public Calendar getCurrDate() {
		return this.currDate;
	}
	public void setCurrDate(Calendar date) {
		this.currDate = date;
	}
	public String getCurrency() {
		return this.currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
}
