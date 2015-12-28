package com.benlinus92.webspring.dao;

import java.util.Calendar;
import java.util.List;

public interface CountryCurrencyRepo {

	List<CountryCurrency> getListByCountryId(String countryId);
	void insertCurrency(CountryCurrency entity);
	void updateCurrency(CountryCurrency entity);
	List<CurrencyNameList> getCurrencyList();
	Calendar getLatestDate();
}
