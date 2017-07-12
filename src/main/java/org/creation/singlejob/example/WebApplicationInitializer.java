package org.creation.singlejob.example;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class WebApplicationInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class[] { };
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class[] { AppConfig.class,WebMvcConfig.class };
	}

	@Override
	protected String[] getServletMappings() {

		return new String[] { "/" };
	}

}