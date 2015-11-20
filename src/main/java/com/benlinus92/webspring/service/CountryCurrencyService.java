package com.benlinus92.webspring.service;

import java.util.List;
import java.util.Map;

import com.benlinus92.webspring.dao.CountryCurrency;

public interface CountryCurrencyService {
	
	Map<String,String> getListByCountryId(String countryId1, String countryId2);
	CountryCurrency findById(int id);
	CountryCurrency findByName(String country);
	void updateCountryCurrencyByName(String country);
	void deleteCountryCurrency();
}
