package com.benlinus92.webspring.configuration;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.springframework.core.io.FileSystemResource;
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
		Resource r = new FileSystemResource(AppConstants.PROPERTIES_PATH);
		Properties props = new Properties();
		try {
			InputStream stream = r.getInputStream();
			props.load(stream);
			System.setProperty("updated", props.getProperty("updated"));
		} catch(IOException e) {
			e.printStackTrace();
		}
		return new String[] {"/"};
	}
}
