package com.db.sys.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.db.common.vo.JsonResult;
import com.db.sys.entity.SysRole;
import com.db.sys.service.SysRoleService;

@Controller
@RequestMapping("/role/")
public class SysRoleController {
	@Autowired
	SysRoleService sysRoleService;
	
	@ResponseBody
	@RequestMapping("doCheck")
	public JsonResult doCheck(String name) {
		sysRoleService.isRoleExist(name);
		return new JsonResult("名字不存在");
	}
	@RequestMapping("doRoleListUI")
	public String doRoleListUI() {
		return "sys/role_list";
	}
	
	@RequestMapping("doRoleEditUI")
	public String doRoleEditUI() {
		return "sys/role_edit";
	}
	
	@RequestMapping("doFindPageObjects")
	@ResponseBody
	public JsonResult doFindPageObjects(Integer pageCurrent,String name) {
		return new JsonResult(sysRoleService.findPageObjects(name, pageCurrent));
	}
	
	@RequestMapping("doDeleteObject")
	@ResponseBody
	public JsonResult doDeleteObject(Integer id) {
		sysRoleService.deleteObject(id);
		return new JsonResult("delete ok!");
	}
	
	@RequestMapping("doSaveObject")
	@ResponseBody
	public JsonResult doSaveObject(SysRole entity,
									Integer[] menuIds) {
		sysRoleService.saveObject(entity, menuIds);
		return new JsonResult("save ok!");
	}
	
	@RequestMapping("doUpdateObject")
	@ResponseBody
	public JsonResult doUpdateObject(SysRole entity,
									Integer[] menuIds) {
		sysRoleService.updateObject(entity, menuIds);
		return new JsonResult("update ok!");
	}
	
	@RequestMapping("doFindObjectById")
	@ResponseBody
	public JsonResult doFindObjectById(Integer id) {
		return new JsonResult(sysRoleService.findObjectById(id));
	}
	
	@RequestMapping("doFindRoles")
	@ResponseBody
	public JsonResult doFindObjects() {
		return new JsonResult(sysRoleService.findObjects());
	}
}
