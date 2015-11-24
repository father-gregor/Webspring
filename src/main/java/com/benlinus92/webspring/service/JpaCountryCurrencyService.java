package com.benlinus92.webspring.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.Calendar;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import com.benlinus92.webspring.dao.CountryCurrency;
import com.benlinus92.webspring.dao.CountryCurrencyRepo;
import com.benlinus92.webspring.json.Base;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@Service("employeeService")
@PropertySource("classpath:resources/update.properties")
public class JpaCountryCurrencyService implements CountryCurrencyService {

	@Autowired
	private CountryCurrencyRepo dao;

	@Value("{updated}")
	public String msg;
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
	public void writeJsonToDatabase(String apiURL) {
		String apiJson = this.readJsonFromUrl(apiURL);
		Gson gson = new Gson();
		Base baseJson = gson.fromJson(apiJson, Base.class);
		for(Map.Entry<String, String> entry : baseJson.getQuotes().entrySet()) {
			System.out.println(entry.getKey() + ":" + entry.getValue());
		}
	}
	@Override
	public String readJsonFromUrl(String apiURL) {
		StringBuilder sb = new StringBuilder();
		URLConnection urlConn = null;
		InputStreamReader in = null;
		try {
			URL url = new URL(apiURL);
			urlConn = url.openConnection();
			if (urlConn != null)
				urlConn.setReadTimeout(60 * 1000);
			if (urlConn != null && urlConn.getInputStream() != null) {
				in = new InputStreamReader(urlConn.getInputStream(),
						Charset.defaultCharset());
				BufferedReader bufferedReader = new BufferedReader(in);
				if (bufferedReader != null) {
					int cp;
					while ((cp = bufferedReader.read()) != -1) {
						sb.append((char) cp);
					}
					bufferedReader.close();
				}
			}
			in.close();
		} catch (Exception e) {
			throw new RuntimeException("Exception while calling URL:"+ apiURL, e);
		} 
		return sb.toString();
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
