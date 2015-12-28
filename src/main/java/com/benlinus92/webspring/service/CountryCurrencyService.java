package com.benlinus92.webspring.service;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import com.benlinus92.webspring.dao.CurrencyNameList;
import com.benlinus92.webspring.json.DateCurrency;

public interface CountryCurrencyService {
	
	List<DateCurrency> getListByCountryId(String countryId1, String countryId2);
	List<CurrencyNameList> getCurrencyNameList();
	void writeEntityToDatabase(String url, Calendar date);
	void updateEntityInDatabase(String url, Calendar date);
	String readJsonFromUrl(String url);
	void updateDatabaseOnDemand(String date);
}
