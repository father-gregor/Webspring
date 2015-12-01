package com.benlinus92.webspring.service;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.benlinus92.webspring.configuration.AppConstants;
import com.benlinus92.webspring.dao.CountryCurrency;
import com.benlinus92.webspring.dao.CountryCurrencyRepo;
import com.benlinus92.webspring.json.Base;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@Service("employeeService")
@PropertySource(value="classpath:update.properties",ignoreResourceNotFound = true)
public class JpaCountryCurrencyService implements CountryCurrencyService {

	@Autowired
	private Environment environment;
	@Autowired
	private CountryCurrencyRepo dao;

	@Value("${updated}")
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
	public void writeJsonToDatabase(String apiURL, Calendar date) {
		String apiJson = readJsonFromUrl(apiURL);
		Gson gson = new Gson();
		Base baseJson = gson.fromJson(apiJson, Base.class);
		for(Map.Entry<String, String> entry : baseJson.getQuotes().entrySet()) {
			String country = entry.getKey().replaceFirst("USD", "");
			String currency = entry.getValue();
			CountryCurrency entity = new CountryCurrency(country, currency, date);
			dao.insertCurrency(entity);
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
	public void updateDatabaseOnDemand(String date) {
		if(isDatabaseOutOfDate(date) == true) {
			Calendar dbDate = Calendar.getInstance();
			Calendar userDate = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			try {
				dbDate.setTime(sdf.parse(System.getProperty("updated")));
				userDate.setTime(sdf.parse(date));
			} catch(ParseException e) { 
				e.printStackTrace();
			}
			dbDate.add(Calendar.DATE, 1);
			while(dbDate.compareTo(userDate) <= 0) {
				String urlDate = sdf.format(dbDate.getTime());
				String url = AppConstants.CURR_API_HISTORY + urlDate + AppConstants.CURR_API_SET + AppConstants.CURR_API_RATESLIST;
				writeJsonToDatabase(url, dbDate);
				dbDate.add(Calendar.DATE, 1);
			}
			try {
				Resource r = new ClassPathResource("update.properties");
				Properties props = new Properties();
				InputStream in = getClass().getClassLoader().getResourceAsStream("update.properties");
			    props.load(in);
			    in.close();
			    OutputStream out = new FileOutputStream(r.getFile()); 
			    props.setProperty("updated", date);
			    System.setProperty("updated", date);
			    props.store(out, null);
			    out.close();
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
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
	private boolean isDatabaseOutOfDate(String date) {
		Calendar dbDate = Calendar.getInstance();
		Calendar userDate = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			dbDate.setTime(sdf.parse(System.getProperty("updated")));
			userDate.setTime(sdf.parse(date));
		} catch(ParseException e) { 
			e.printStackTrace();
		}
		return dbDate.before(userDate);
	}
}
