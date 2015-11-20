package com.benlinus92.webspring.dao;

import java.util.List;

public interface CountryCurrencyRepo {

	List<CountryCurrency> getListByCountryId(String countryId);
	CountryCurrency findById(int id);
	CountryCurrency findByName(String country);
	void updateCountryCurrencyByName(String country);
	void deleteCountryCurrency();
}
