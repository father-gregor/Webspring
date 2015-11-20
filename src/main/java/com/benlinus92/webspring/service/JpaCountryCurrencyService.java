package com.benlinus92.webspring.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.benlinus92.webspring.dao.CountryCurrency;
import com.benlinus92.webspring.dao.CountryCurrencyRepo;

@Service("employeeService")
public class JpaCountryCurrencyService implements CountryCurrencyService {

	@Autowired
	private CountryCurrencyRepo dao;
	
	@Override
	public Map<Calendar, String> getListByCountryId(String countryId1,
			String countryId2) {
		
		List<CountryCurrency> listCountry1 = dao.getListByCountryId(countryId1);
		List<CountryCurrency> listCountry2 = dao.getListByCountryId(countryId2);
		Map<Calendar, String> result = new LinkedHashMap<Calendar, String>();
		Iterator<CountryCurrency> it1 = listCountry1.iterator();
		Iterator<CountryCurrency> it2 = listCountry2.iterator();
		while(it1.hasNext() & it2.hasNext()) {
			CountryCurrency country1 = (CountryCurrency) it1.next();
			CountryCurrency country2 = (CountryCurrency) it2.next();
			if(country1.getCurrDate().equals(country2.getCurrDate())) {
				BigDecimal currDec1 = new BigDecimal(country1.getCurrency());
				BigDecimal currDec2 = new BigDecimal(country2.getCurrency());
				//String date = country1.getCurrDate();
				//Formula: 1 USD = x FirstCurrency
				//1 USD = y SecondCurrency
				//1 FirstCurrency = z AnotherCurrency
				//Equation: z = (1 * y) / x
				result.put(country1.getCurrDate(), 
						currDec2.divide(currDec1, 3, RoundingMode.HALF_UP).toString()); 
			}
		}
		return result;
	}
	@Override
	public CountryCurrency findById(int id) {
		return dao.findById(id);
	}
	@Override
	public CountryCurrency findByName(String country) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void updateCountryCurrencyByName(String country) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void deleteCountryCurrency() {
		// TODO Auto-generated method stub
		
	}
}
