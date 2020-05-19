package com.db.common.web;

import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.db.common.exception.ServiceException;
/**
 * springmvc中的handler拦截器定义
 * 
 * 基于此拦截器实现按时间的拦截和放行
 * @author 11716
 *
 */
@Component
public class TimeAccessInterceptor extends HandlerInterceptorAdapter{
	/**此方法在后端控制器方法执行之前执行
	 * @return 此返回值决定了这个请求继续执行还是到此为止*/
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		System.out.println("preHandle");
		//1.获取日历对象(通过此对象设置时间)
		Calendar c = Calendar.getInstance();  
		//2.设置开始允许访问时间
		c.set(Calendar.HOUR_OF_DAY, 9);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		long start = c.getTimeInMillis();
		//3.设置结束允许访问时间
		c.set(Calendar.HOUR_OF_DAY, 20);
		long end = c.getTimeInMillis();
		//4.基于时间设置拦截和放行
		long time = System.currentTimeMillis();
		if(time<start||time>end)
		throw new ServiceException("此时间点不允许访问");
		return true;
	}
}
