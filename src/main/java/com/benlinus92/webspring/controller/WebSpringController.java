package com.benlinus92.webspring.controller;

import java.io.IOException;

import org.springframework.aop.interceptor.PerformanceMonitorInterceptor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.benlinus92.webspring.model.Parser;

@Controller
public class WebSpringController {
	private Parser par = new Parser();
	
	@RequestMapping("/welcome")
	public ModelAndView viewText() throws IOException {
		String message = "Testing spring in web Openshift";
		String request = "http://query.yahooapis.com/v1/public/yql?format=xml&q=select%20*%20from%20" +
                "yahoo.finance.xchange%20where%20pair%20in%20(\"USDEUR\",%20\"USDUAH\",%20\"USDRUB\")&env=store://datatables.org/alltableswithkeys";
		String json = par.performRequest(request);
		return new ModelAndView("welcome", "message", json);
	}
}
