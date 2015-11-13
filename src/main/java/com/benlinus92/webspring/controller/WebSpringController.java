package com.benlinus92.webspring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class WebSpringController {
	@RequestMapping("/welcome")
	public ModelAndView viewText() {
		String message = "Testing spring in web Openshift";
		return new ModelAndView("welcome", "message", message);
	}
}
