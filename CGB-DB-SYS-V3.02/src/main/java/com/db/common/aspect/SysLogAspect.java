package com.db.common.aspect;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Date;

import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.druid.util.StringUtils;
import com.db.common.annotation.RequiredLog;
import com.db.sys.dao.SysLogDao;
import com.db.sys.dao.SysUserDao;
import com.db.sys.entity.SysLog;
import com.db.sys.entity.SysUser;

/**
 * 通过此类为系统中的某些
 * 业务操作添加日志扩展功能
 * 其中：@Aspect 描述的类为一个切面对象
 * 这样的类中通常会有两大部分构成
 * 1.Pointcut切入点(织入扩展功能的电)
 * 2.Advice通知(扩展功能)
 * @author 11716
 *
 */
@Aspect
@Service
public class SysLogAspect {
	@Autowired
	private SysLogDao sysLogDao;
	/**定义的切入点，其定义要借助@Pointcut
	 * bean(sysUserServiceImpl)为切入点表达式、
	 * 其语法结构：
	 * 1.bean（bean的名字）例如bean(sysUserServiceImpl)
	 * 2.bean（bean的表达式）例如bean(*ServiceImpl)
	 * */
	@Pointcut("bean(*ServiceImpl)")
	public void doLog() {}
	
	/**
	 * @Around 此注解描述的方法为一个通知，
	 * 这个通知中可以在目标方法执行之前和之后做一些事情
	 * @param jp 连接点对象（封装了目标方法信息）
	 * @return
	 * @throws Throwable
	 */
	@Around("doLog()")
	public Object around(ProceedingJoinPoint jp) throws Throwable{
		long t1 = System.currentTimeMillis();
		//执行下一个切面方法，没有则执行目标方法
		Object result = jp.proceed();
		long t2 = System.currentTimeMillis();
		Method targetMethod = getTargetMethod(jp);
		String methodName = getTargetMethodName(targetMethod);
		System.out.println(methodName+" excute time:"+(t2-t1));
		saveObject(jp,(t2-t1));
		return result;
	}
	/**将用户行为日志信息写入到数据
	 * @throws Exception 
	 * @throws NoSuchMethodException */
	private void saveObject(ProceedingJoinPoint jp, long time) throws NoSuchMethodException, Exception {
		//获取用户行为日志信息
		SysUser user = (SysUser)SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
		String username = user.getUsername();
		Method method = getTargetMethod(jp);
		String methodName = getTargetMethodName(method);
		String params = Arrays.toString(jp.getArgs());
		String operation = "operation";
		RequiredLog rLog = method.getDeclaredAnnotation(RequiredLog.class);
		if(rLog!=null&&!StringUtils.isEmpty(rLog.value())) {
			operation=rLog.value();
		}
		String ip = "127.123.100";
		//封装用户行为日志信息
		SysLog entity = new SysLog();
		entity.setMethod(methodName);
		entity.setIp(ip);
		entity.setUsername(username);
		entity.setParams(params);
		entity.setOperation(operation);
		entity.setTime(time);
		entity.setCreatedTime(new Date());
		//将日志信息持久化
		sysLogDao.insertObject(entity);
		
	}

	private String getTargetMethodName(Method targetMethod) {
		return new StringBuilder(targetMethod.getDeclaringClass().getName()).append(".")
		.append(targetMethod.getName()).toString();
	}

	/**基于连接点获取目标方法对象
	 * @throws Exception 
	 * @throws NoSuchMethodException */
	private Method getTargetMethod(ProceedingJoinPoint jp) throws NoSuchMethodException, Exception {
		//获取目标类对象（字节码对象）
		Class<?> targetCls = jp.getTarget().getClass();
		//2.获取方法签名信息（方法名，参数列表信息）
		MethodSignature signature = (MethodSignature) jp.getSignature();
		Method method = targetCls.getDeclaredMethod(signature.getName(), signature.getParameterTypes());
		return method;
	}
	

}
