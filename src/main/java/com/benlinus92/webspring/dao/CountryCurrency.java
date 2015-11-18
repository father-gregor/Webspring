package com.benlinus92.webspring.dao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="CAD")
public class CountryCurrency {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ID")
	private int id;
	@DateTimeFormat(pattern="dd-MM-yyyy")
	@Column(name="CURR_DATE", nullable=false)
	private String currDate;
	@Column(name="CURRENCY", nullable=false)
	private String currency;
	
	public int getId() {
		return this.id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCurrDate() {
		return this.currDate;
	}
	public void setCurrDate(String date) {
		this.currDate = date;
	}
	public String getCurrency() {
		return this.currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
}
