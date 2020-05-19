package com.db.common.config;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;

import com.db.sys.service.realm.ShiroUserRealm;

public class SpringShiro {
	@Bean("security")
	public DefaultWebSecurityManager newDefaultWebSecurityManager(ShiroUserRealm realm) {
		DefaultWebSecurityManager security = new DefaultWebSecurityManager();
		security.setRealm(realm);
		security.setRememberMeManager(newCookieRememberMeManager());
		security.setSessionManager(newDefaultWebSessionManager());
		security.setCacheManager(newMemoryConstrainedCacheManager());
		return security;
	}
	@Bean("shiroFilterFactory")
	public ShiroFilterFactoryBean newShiroFilterFactoryBean(SecurityManager securityManager) {
		ShiroFilterFactoryBean factory = new ShiroFilterFactoryBean();
		factory.setSecurityManager(securityManager);
		factory.setLoginUrl("/doLoginUI.do");
		Map<String,String> map = new LinkedHashMap<>();
		map.put("/bower_components/**","anon");
		map.put("/build/**","anon");
		map.put("/dist/**","anon");
		map.put("/plugins/**","anon");
		map.put("/user/doLogin.do","anon");
		map.put("/doLogout.do","logout");
		map.put("/**","authc");
		factory.setFilterChainDefinitionMap(map);
		return factory;
	}
	@Bean("life")
	public LifecycleBeanPostProcessor newLifecycleBeanPostProcessor() {
		LifecycleBeanPostProcessor life = new LifecycleBeanPostProcessor();
		return life;
	}
	@Bean
	@DependsOn("life")
	public DefaultAdvisorAutoProxyCreator newDefaultAdvisorAutoProxyCreator() {
		DefaultAdvisorAutoProxyCreator proxy = new DefaultAdvisorAutoProxyCreator();
		return proxy;
	}
	@Bean
	public AuthorizationAttributeSourceAdvisor newAuthorizationAttributeSourceAdvisor(SecurityManager securityManager) {
		AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
		advisor.setSecurityManager(securityManager);
		return advisor;
	}
	
	public MemoryConstrainedCacheManager newMemoryConstrainedCacheManager() {
		MemoryConstrainedCacheManager cache = new MemoryConstrainedCacheManager();
		return cache;
	}
	
	public CookieRememberMeManager newCookieRememberMeManager() {
		CookieRememberMeManager remember = new CookieRememberMeManager();
		SimpleCookie cookie = new SimpleCookie("remember");
		cookie.setMaxAge(60*60);
		remember.setCookie(cookie);
		return remember;
	}
	
	public DefaultWebSessionManager newDefaultWebSessionManager() {
		DefaultWebSessionManager session = new DefaultWebSessionManager();
		session.setGlobalSessionTimeout(1000*60*60);
		return session;
	}
}
