package com.benlinus92.webspring.configuration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class WebSpringInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return null;
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class<?>[] { AppConfig.class};
	}

	@Override
	protected String[] getServletMappings() {
		Resource r = new ClassPathResource("update.properties");
		Properties props = new Properties();
		try {
			InputStream stream = getClass().getClassLoader().getResourceAsStream("update.properties");
			props.load(stream);
			System.setProperty("updated", props.getProperty("updated"));
		} catch(IOException e) {
			e.printStackTrace();
		}
		return new String[] {"/"};
	}
}
