package com.benlinus92.webspring.service;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.benlinus92.webspring.configuration.AppConstants;
import com.benlinus92.webspring.dao.CountryCurrency;
import com.benlinus92.webspring.dao.CountryCurrencyRepo;
import com.benlinus92.webspring.dao.CurrencyNameList;
import com.benlinus92.webspring.json.Base;
import com.benlinus92.webspring.json.DateCurrencyPair;
import com.benlinus92.webspring.json.News;
import com.google.gson.Gson;

@Service("employeeService")
public class JpaCountryCurrencyService implements CountryCurrencyService {

	@Autowired
	private Environment environment;
	@Autowired
	private CountryCurrencyRepo dao;

	/**
	 * Formula: 1 USD = x FirstCurrency
	   1 USD = y SecondCurrency
	   1 FirstCurrency = z SecondCurrency
	   Equation: z = (1 * y) / x
	 */
	@Override
	public List<DateCurrencyPair> getListByCountryId(String countryId1,
			String countryId2) {
		
		List<CountryCurrency> listCountry1 = dao.getListByCountryId(countryId1);
		List<CountryCurrency> listCountry2 = dao.getListByCountryId(countryId2);
		List<DateCurrencyPair> currencyList = new ArrayList<DateCurrencyPair>();
		Iterator<CountryCurrency> it1 = listCountry1.iterator();
		Iterator<CountryCurrency> it2 = listCountry2.iterator();
		while(it1.hasNext() & it2.hasNext()) {
			CountryCurrency country1 = (CountryCurrency) it1.next();
			CountryCurrency country2 = (CountryCurrency) it2.next();
			if(country1.getCurrDate().equals(country2.getCurrDate())) {
				BigDecimal currDec1 = new BigDecimal(country1.getCurrency());
				BigDecimal currDec2 = new BigDecimal(country2.getCurrency());
				DateCurrencyPair dc = new DateCurrencyPair();
				dc.setCurrency(currDec2.divide(currDec1, 4, RoundingMode.HALF_UP).toString());
				dc.setDate(this.convertCalendarToString(country1.getCurrDate()));
				currencyList.add(dc);
			}
		}
		return currencyList;
	}
	@Override
	public List<CurrencyNameList> getCurrencyNameList() {
		return dao.getCurrencyList();
	}
	@Override
	public void updateDatabaseOnDemand(String date) {
		Calendar dbDate = Calendar.getInstance();
		Calendar userDate = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			String urlDate = null;
			String url = null;
			dbDate = dao.getLatestDate();
			userDate.setTime(sdf.parse(date));
			if(dbDate.before(userDate)) {
				urlDate = sdf.format(dbDate.getTime());
				url = AppConstants.CURR_API_HISTORY + urlDate + AppConstants.CURR_API_SET + AppConstants.CURR_API_RATESLIST;
				updateEntityInDatabase(url, dbDate);
				dbDate.add(Calendar.DATE, 1);
				while(dbDate.compareTo(userDate) <= 0) {
					urlDate = sdf.format(dbDate.getTime());
					url = AppConstants.CURR_API_HISTORY + urlDate + AppConstants.CURR_API_SET + AppConstants.CURR_API_RATESLIST;
					writeEntityToDatabase(url, dbDate);
					dbDate.add(Calendar.DATE, 1);
				}
			} else {
				urlDate = sdf.format(dbDate.getTime());
				url = AppConstants.CURR_API_HISTORY + urlDate + AppConstants.CURR_API_SET + AppConstants.CURR_API_RATESLIST;
				updateEntityInDatabase(url, dbDate);	
			}
		} catch(ParseException e) { 
			e.printStackTrace();
		}
	}
	@Override
	public void writeEntityToDatabase(String apiURL, Calendar date) {
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
	public void updateEntityInDatabase(String apiURL, Calendar date) {
		String apiJson = readJsonFromUrl(apiURL);
		Gson gson = new Gson();
		Base baseJson = gson.fromJson(apiJson, Base.class);
		for(Map.Entry<String, String> entry : baseJson.getQuotes().entrySet()) {
			String country = entry.getKey().replaceFirst("USD", "");
			String currency = entry.getValue();
			CountryCurrency entity = new CountryCurrency(country, currency, date);
			dao.updateCurrency(entity);
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
			throw new RuntimeException("Exception while calling URL: "+ apiURL, e);
		} 
		return sb.toString();
	}
	@Override
	public List<News> getNewsByCountryId(String countryId) {
		String url = AppConstants.GOOGLE_NEWS_URL + countryId;
		List<News> newsList = new ArrayList<News>();
		try {
			Document doc = Jsoup.connect(url).get();
			Elements elems = doc.select("div.g-section.news.sfe-break-bottom-16");
			if(elems != null) {
				for(Element e: elems) {
					String header = e.getElementById("n-cn-").text();
					String link = e.getElementById("n-cn-").attr("href");
					String source = e.getElementsByClass("src").first().text();
					String date = e.getElementsByClass("date").first().text();
					newsList.add(new News(header, link, source, date));
				}
			}
		} catch(IOException ex) {
			ex.printStackTrace();
		}
		return newsList; 
	}
	private String convertCalendarToString(Calendar date) {
		SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
		return formatDate.format(date.getTime());
	}
}
