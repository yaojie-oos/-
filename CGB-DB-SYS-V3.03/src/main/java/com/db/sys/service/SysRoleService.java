package com.db.sys.service;

import java.util.List;

import com.db.common.vo.CheckBox;
import com.db.common.vo.PageObject;
import com.db.sys.entity.SysRole;
import com.db.sys.vo.SysRoleMenuVo;

public interface SysRoleService {
	
	void isRoleExist(String name);
	/**
	 * 分页查询角色信息
	 * @param name
	 * @param pageCurrent
	 * @return
	 */
	PageObject<SysRole> findPageObjects(String name,Integer pageCurrent);
	
	int deleteObject(Integer id);
	
	int saveObject(SysRole entity,
				Integer[] menuIds);
	
	int updateObject(SysRole entity,
			Integer[] menuIds);
	/**
	 * 基于角色id获取角色以及对应的菜单信息
	 * @param id
	 * @return
	 */
	SysRoleMenuVo findObjectById(Integer id);
	
	List<CheckBox> findObjects();
}
