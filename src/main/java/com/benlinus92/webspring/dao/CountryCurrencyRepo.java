package com.benlinus92.webspring.dao;

public interface CountryCurrencyRepo {

	CountryCurrency findById(int id);
	CountryCurrency findByName(String country);
	void updateCountryCurrencyByName(String country);
	void deleteCountryCurrency();
}
