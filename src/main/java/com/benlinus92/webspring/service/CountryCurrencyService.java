package com.benlinus92.webspring.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.benlinus92.webspring.dao.CountryCurrency;

public interface CountryCurrencyService {
	
	Map<Calendar,String> getListByCountryId(String countryId1, String countryId2);
	void writeJsonToDatabase(String url, Calendar date);
	String readJsonFromUrl(String url);
	void updateDatabaseOnDemand(String date);
	CountryCurrency findById(int id);
	CountryCurrency findByName(String country);
	void updateCountryCurrencyByName(String country);
	void deleteCountryCurrency();
}
