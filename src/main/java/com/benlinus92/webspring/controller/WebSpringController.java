package com.benlinus92.webspring.controller;

import java.io.IOException;

import org.springframework.aop.interceptor.PerformanceMonitorInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.benlinus92.webspring.service.CountryCurrencyService;

@Controller
@RequestMapping("/")
@PropertySource("classpath:resources/update.properties")
public class WebSpringController {
	private static final String CURR_API_LIVE = "http://apilayer.net/api/live?";
	private static final String CURR_API_HISTORY = "http://apilayer.net/api/historical?date=";
	private static final String CURR_API_ID = "&access_key=194a276698ccbe38f3d622a013f4dc64";
	private static final String CURR_API_SET = "&currencies="; 
	
	@Value("{updated}")
	public String msg;
	@Autowired
	CountryCurrencyService service;
	
	@RequestMapping(value="/currency-{countryId1}-{countryId2}", method=RequestMethod.GET)
	public String requestCurrency(@PathVariable String countryId1, @PathVariable String countryId2, Model model) {
		String message = "Testing spring in web Openshift";
		String request = "http://query.yahooapis.com/v1/public/yql?format=xml&q=select%20*%20from%20" +
                "yahoo.finance.xchange%20where%20pair%20in%20(\"USDEUR\",%20\"USDUAH\",%20\"USDRUB\")&env=store://datatables.org/alltableswithkeys";
		model.addAttribute("countryCurr",service.getListByCountryId(countryId1, countryId2));
		//String json = par.performRequest(OPEN_API_LATEST + OPEN_API_ID);
		//return new ModelAndView("welcome", "message", json);
		return "welcome";
	}
	@RequestMapping("/json-{date}")
	public String requestJson(@PathVariable String date, Model model) {
		service.writeJsonToDatabase(CURR_API_HISTORY + date + CURR_API_ID
				+ CURR_API_SET + "AUD,CHF,EUR,GBP,PLN");
		return "welcome";
	}
	@RequestMapping("/welcome")
	public String viewText(Model model) {
		model.addAttribute("message", this.msg);
		//String json = par.performRequest(OPEN_API_LATEST + OPEN_API_ID);
		//return new ModelAndView("welcome", "message", json);
		return "welcome";
	}
}
