package com.db.common.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Service;

@Service
@Aspect
public class OtherAspect {
	@Before("bean(sysUserServiceImpl)")
	public void before() {
		System.out.println("@Before");
	}
	@After("bean(sysUserServiceImpl)")
	public void after() {
		System.out.println("@After");
	}
	
	@AfterReturning("bean(sysUserServiceImpl)")
	public void afterReturning() {
		System.out.println("@AfterReturning");
	}
	
	@AfterThrowing("bean(sysUserServiceImpl)")
	public void afterThrowing() {
		System.out.println("@AfterThrowing");
	}
	
	@Around("bean(sysUserServiceImpl)")
	public Object around(ProceedingJoinPoint jp) throws Throwable {
		System.out.println("@Around before");
		Object result = jp.proceed();
		System.out.println("@Around after");
		return result;
	}
}
