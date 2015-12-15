package com.benlinus92.webspring.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import com.benlinus92.webspring.dao.CurrencyNameList;
import com.benlinus92.webspring.json.AjaxCurrencyInput;
import com.benlinus92.webspring.json.DateCurrency;
import com.benlinus92.webspring.service.CountryCurrencyService;
import com.google.gson.Gson;

@Controller
@RequestMapping("/")
public class WebSpringController {
	
	@Autowired
	CountryCurrencyService service;
	@Autowired
	private Environment environment;
	
	@RequestMapping(value="/", method=RequestMethod.GET)
	public ModelAndView getCurrenciesList() {
		List<CurrencyNameList> list = service.getCurrencyNameList();
		ModelAndView model = new ModelAndView("/index");
		model.addObject("currencyList", list);
		return model;
	}
	@RequestMapping(value="/currency-{countryId1}-{countryId2}-{daysCount}", method=RequestMethod.GET)
	public ModelAndView requestCurrency(@PathVariable String countryId1, @PathVariable String countryId2, 
			@PathVariable String daysCount, Model model) {
		service.updateDatabaseOnDemand(daysCount);
		model.addAttribute("countryCurr",service.getListByCountryId(countryId1, countryId2));
		return new ModelAndView("currency");
		 //return "welcome";
	}
	@RequestMapping(value="/getcurrencies", method=RequestMethod.POST)
	public @ResponseBody List<DateCurrency> currenciesJson(@RequestBody AjaxCurrencyInput currInput) {
		service.updateDatabaseOnDemand(currInput.getDate());
		return service.getListByCountryId(currInput.getCountryId1(), currInput.getCountryId2());
	}
}
