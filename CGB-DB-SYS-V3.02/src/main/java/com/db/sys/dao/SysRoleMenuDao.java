package com.db.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

/**
 * 基于此接口操作角色菜单关系表数据（sys_role_menus）
 * @author 11716
 *
 */
public interface SysRoleMenuDao {
	/**
	 * 基于菜单id删除关系表数据
	 * @param menuId 菜单id
	 * @return	删除的行数
	 */
	int deleteObjectsByMenuId(Integer menuId);
	
	/**
	 * 基于角色id删除角色和菜单的关系数据
	 * @param roleId
	 * @return
	 */
	int deleteObjectsByRoleId(Integer roleId);
	
	/**
	 * 将角色和菜单的关系写入到数据库
	 * @param roleId
	 * @param menuId
	 * @return
	 */
	int insertObjects(
			@Param("roleId")Integer roleId,
			@Param("menuIds")Integer[] menuIds);
	
	int findMenuIdsByRoleId(Integer id);
	/**
	 * 基于多个角色id查询多个菜单id
	 * @param roleIds
	 * @return
	 */
	List<Integer> findMenuIdsByRoleIds(@Param("roleIds")Integer[] roleIds);
}
