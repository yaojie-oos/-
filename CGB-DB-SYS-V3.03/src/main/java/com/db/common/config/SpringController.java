package com.db.common.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.db.common.web.TimeAccessInterceptor;

@ComponentScan({"com.db.sys.controller,com.db.common.web"})
@EnableWebMvc
public class SpringController extends WebMvcConfigurerAdapter{
	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		registry.jsp("/WEB-INF/pages/",".html");
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		TimeAccessInterceptor interceptor = new TimeAccessInterceptor();
		registry.addInterceptor(interceptor)
		.addPathPatterns("/user/doLogin.do");
	}
	
	
	
}
