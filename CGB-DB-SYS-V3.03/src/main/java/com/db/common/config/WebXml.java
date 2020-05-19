package com.db.common.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class WebXml extends AbstractAnnotationConfigDispatcherServletInitializer{

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class[] {SpringRespository.class,SpringService.class,SpringShiro.class};
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class[] {SpringController.class};
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] {"*.do"};
	}
	
	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		shiro(servletContext);
		super.onStartup(servletContext);
	}

	private void shiro(ServletContext servletContext) {
		javax.servlet.FilterRegistration.Dynamic d = servletContext.addFilter("shiroFilter", DelegatingFilterProxy.class);
		d.addMappingForUrlPatterns(null, true, "/*");
		d.setInitParameter("targetBeanName", "shiroFilterFactory");
	}

}
