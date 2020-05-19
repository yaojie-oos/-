package com.db.common.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Service;

@Service
@Aspect
public class SysCacheAspect {
	/**定义切入点，其中@annotation(com.db.common.annotation.RequiredCache)
	 * 为注解方式切入点表达式*/
	@Pointcut("@annotation(com.db.common.annotation.RequiredCache)")
	public void doCache() {}
	
	@Around("doCache()")
	public Object around(ProceedingJoinPoint jp) throws Throwable{
		//1.先从缓存取数据
		System.out.println("取数据");
		//2.缓存没有则执行业务
		System.out.println("执行目标方法");
		Object result = jp.proceed();
		//3.将查询结果存储到缓存对象
		System.out.println("存入缓存");
		return result;
	}
}
