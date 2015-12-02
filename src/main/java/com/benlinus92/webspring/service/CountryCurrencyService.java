package com.benlinus92.webspring.service;

import java.util.Calendar;
import java.util.Map;

public interface CountryCurrencyService {
	
	Map<Calendar,String> getListByCountryId(String countryId1, String countryId2);
	void writeJsonToDatabase(String url, Calendar date);
	String readJsonFromUrl(String url);
	void updateDatabaseOnDemand(String date);
}
