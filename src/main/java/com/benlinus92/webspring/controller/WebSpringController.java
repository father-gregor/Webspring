package com.benlinus92.webspring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.benlinus92.webspring.service.CountryCurrencyService;

@Controller
@RequestMapping("/")
public class WebSpringController {
	
	@Autowired
	CountryCurrencyService service;
	@Autowired
	private Environment environment;
	
	@RequestMapping(value="/currency-{countryId1}-{countryId2}-{daysCount}", method=RequestMethod.GET)
	public ModelAndView requestCurrency(@PathVariable String countryId1, @PathVariable String countryId2, 
			@PathVariable String daysCount, Model model) {
		service.updateDatabaseOnDemand(daysCount);
		model.addAttribute("countryCurr",service.getListByCountryId(countryId1, countryId2));
		return new ModelAndView("currency");
		//("welcome", "message", json);
		//return "welcome";
	}
	@RequestMapping("/json-{date}")
	public String requestJson(@PathVariable String date, Model model) {
		//service.writeJsonToDatabase(CURR_API_HISTORY + date + CURR_API_ID
			//	+ CURR_API_SET + "AUD,CHF,EUR,GBP,PLN");
		return "welcome";
	}
	@RequestMapping("/welcome-{date}")
	public String viewText(@PathVariable String date, Model model) {
		System.out.println("Before database update: " + System.getProperty("updated"));
		long startTime = System.currentTimeMillis();
		service.updateDatabaseOnDemand(date);
		long endTime = System.currentTimeMillis();
		System.out.println("Before database update: " + System.getProperty("updated") + "; exec time = " + (endTime-startTime));
		String w = System.getProperty("updated");
		model.addAttribute("message2", w);
		//return new ModelAndView("welcome", "message", json);
		return "welcome";
	}
}
