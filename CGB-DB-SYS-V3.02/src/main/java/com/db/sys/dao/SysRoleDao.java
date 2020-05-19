package com.db.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.db.common.vo.CheckBox;
import com.db.sys.entity.SysRole;
import com.db.sys.vo.SysRoleMenuVo;

public interface SysRoleDao {
	
	int getCountByName(String name);
	int getRowCount(@Param("name")String name);
	
	List<SysRole> findPageObjects(@Param("name")String name,
								@Param("startIndex")Integer startIndex,
								@Param("pageSize")Integer pageSize);
	
	/**
	 * 基于角色id删除自身信息
	 * @param id
	 * @return
	 */
	int deleteObject(Integer id);
	
	/**
	 * 将角色信息写入数据库
	 * @param entity
	 * @return
	 */
	int insertObject(SysRole entity);
	
	/**
	 * 将角色信息更新到数据库
	 * @param entity
	 * @return
	 */
	int updateObject(SysRole entity);
	
	/**
	 * 基于角色id查询角色以及对应的菜单信息
	 * @param id
	 * @return
	 */
	SysRoleMenuVo findObjectById(Integer id);
	
	/**
	 * 查询所有角色信息
	 * @return
	 */
	List<CheckBox> findObjects();
}
