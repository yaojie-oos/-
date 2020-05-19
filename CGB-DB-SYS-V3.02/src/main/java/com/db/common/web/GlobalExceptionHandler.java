package com.db.common.web;

/**
 * @ControllerAdvice 修饰的类为一个全局异常处理类
 * @author 11716
 *
 */
import org.apache.shiro.ShiroException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationException;
import org.slf4j.Logger;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.db.common.vo.JsonResult;
@ControllerAdvice
public class GlobalExceptionHandler {
	private Logger log = org.slf4j.LoggerFactory.getLogger(GlobalExceptionHandler.class);
	@ExceptionHandler(ShiroException.class)
	@ResponseBody
	public JsonResult doHandleShiroException(ShiroException e) {
		e.printStackTrace();
		log.error("shiro"+e.getMessage());
		JsonResult result = new JsonResult();
		result.setState(0);
		if(e instanceof UnknownAccountException) {
			result.setMessage("账户不存在");
		}
		else if(e instanceof LockedAccountException) {
			result.setMessage("账户被禁用");
		}
		else if(e instanceof IncorrectCredentialsException) {
			result.setMessage("密码不正确");
		}else if(e instanceof AuthorizationException) {
			result.setMessage("没有此操作的权限");
		}
		
		return result;
		
	}
	/**
	 * @ExceptionHandler 使用此注解修饰的方法为异常处理方法
	 * @return
	 */
	@ExceptionHandler(RuntimeException.class)
	@ResponseBody
	public JsonResult doHandleRunTimeException(RuntimeException e) {
		e.printStackTrace();
		return new JsonResult(e);
	}
}
