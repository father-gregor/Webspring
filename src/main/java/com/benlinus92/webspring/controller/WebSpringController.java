package com.benlinus92.webspring.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;

import org.springframework.aop.interceptor.PerformanceMonitorInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.DefaultPropertiesPersister;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.benlinus92.webspring.configuration.AppConstants;
import com.benlinus92.webspring.service.CountryCurrencyService;

@Controller
@RequestMapping("/")
@PropertySource(value="file:" + AppConstants.PROPERTIES_PATH,ignoreResourceNotFound = true)
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
