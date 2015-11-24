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
import java.util.Properties;

import org.springframework.aop.interceptor.PerformanceMonitorInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.DefaultPropertiesPersister;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.benlinus92.webspring.service.CountryCurrencyService;

@Controller
@RequestMapping("/")
@PropertySource(value="classpath:update.properties",ignoreResourceNotFound = true)
public class WebSpringController {
	private static final String CURR_API_LIVE = "http://apilayer.net/api/live?";
	private static final String CURR_API_HISTORY = "http://apilayer.net/api/historical?date=";
	private static final String CURR_API_ID = "&access_key=194a276698ccbe38f3d622a013f4dc64";
	private static final String CURR_API_SET = "&currencies="; 
	
	@Autowired
	CountryCurrencyService service;
	@Autowired
	private Environment environment;
	
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
		String w = "working";
		try {
			Resource r = new ClassPathResource("update.properties");
			Properties props = new Properties();
		     props.setProperty("updated", "15-15-2015");
		     // get or create the file
		     //File f = new File("update.properties");
		     //OutputStream out = new FileOutputStream( r.getFile() );
		     byte[] encoded = Files.readAllBytes(Paths.get(r.getURI()));
		     w = new String(encoded, Charset.defaultCharset());
		     //w = r.getFile().getAbsolutePath();
		     // write into it
		     //props.store(out, "commentss");
		     //w = props.getProperty("updated");
		     
		} catch(IOException e) { w = e.getMessage();}
		model.addAttribute("message", environment.getProperty("updated"));
		//Properties props = new Properties();
		//Resource r = new ClassPathResource("update.properties");
		//w = props.getProperty("updated");
		model.addAttribute("message2", w);
		//String json = par.performRequest(OPEN_API_LATEST + OPEN_API_ID);
		//return new ModelAndView("welcome", "message", json);
		return "welcome";
	}
}
