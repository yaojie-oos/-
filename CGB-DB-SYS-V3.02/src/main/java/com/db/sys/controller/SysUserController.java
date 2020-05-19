package com.db.sys.controller;

import java.util.Map;


import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.db.common.vo.JsonResult;
import com.db.sys.entity.SysPwd;
import com.db.sys.entity.SysUser;
import com.db.sys.service.SysUserService;


@Controller
@RequestMapping("/user/")
public class SysUserController {
	@Autowired
	private SysUserService sysUserService;
	
	@RequestMapping("doUserListUI")
	public String doUserListUI() {
		return "sys/user_list";
	}
	
	@RequestMapping("doFindPageObjects")
	@ResponseBody
	public JsonResult doFindPageObjects(String username,Integer pageCurrent) {
		return new JsonResult(sysUserService.findPageObjects(pageCurrent,username));
	}
	
	@RequestMapping("doValidById")
	@ResponseBody
	public JsonResult doValidById(
			Integer id,Integer valid) {
		SysUser user = (SysUser) SecurityUtils.getSubject().getPrincipal();
		sysUserService.validById(id, valid,user.getUsername() );
		return new JsonResult("update ok");
	}
	
	@RequestMapping("doUserEditUI")
	public String doUserEditListUI() {
		return "sys/user_edit";
	}
	
	@RequestMapping("doSaveObject")
	@ResponseBody
	public JsonResult doSaveObject(SysUser entity,Integer[] roleIds) {
		sysUserService.saveObject(entity, roleIds);
		return new JsonResult("save ok!");
	}
	
	@RequestMapping("doUpdateObject")
	@ResponseBody
	public JsonResult doUpdateObject(SysUser entity,Integer[] roleIds) {
		sysUserService.updateObject(entity, roleIds);
		return new JsonResult("update ok!");
	}
	
	@RequestMapping("doFindObjectById")
	@ResponseBody
	public JsonResult doFindObjectById(Integer id) {
		Map<String, Object> map = sysUserService.findObjectById(id);
		return new JsonResult(map);
	}
	
	@RequestMapping("doLogin")
	@ResponseBody
	public JsonResult doLogin(String username,String password,Boolean rememberMe) {
		//1.提交信息到业务层
		//1.1获取主题对象（subject）
		Subject subject = SecurityUtils.getSubject();
		//1.2提交信息（提交给shiro的securityManager对象）
		UsernamePasswordToken token = new UsernamePasswordToken(username,password);
		if(rememberMe)
			token.setRememberMe(rememberMe);
		subject.login(token);//执行登入认证操作
		
		return new JsonResult("login ok!");
	}
	
	@RequestMapping("doUpdatePassword")
	@ResponseBody
	public JsonResult doUpdatePassword(SysPwd entity) {
		SysUser user = (SysUser)SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
		String username = user.getUsername();
		sysUserService.updatePwd(username, entity);
		return new JsonResult("修改成功!");
	}
}
