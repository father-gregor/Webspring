package com.benlinus92.webspring.service;

import com.benlinus92.webspring.dao.CountryCurrency;

public interface CountryCurrencyService {
	
	CountryCurrency findById(int id);
	CountryCurrency findByName(String country);
	void updateCountryCurrencyByName(String country);
	void deleteCountryCurrency();
}
