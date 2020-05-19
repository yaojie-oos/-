package com.db.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.db.sys.entity.SysPwd;

/**
 * 角色用户关系表
 * @author 11716
 *
 */
public interface SysUserRoleDao {
	
	int updatePwd(SysPwd entity);
	
	/**
	 * 基于用户id找到角色
	 * @param id
	 * @return
	 */
	List<Integer> findRoleIdsByUserId(Integer UserId);
	/**
	 * 基于角色id删除角色和用户关系数据
	 * @param roleId
	 * @return
	 */
	int deleteObjectsByRoleId(Integer roleId);
	
	/**
	 * 基于用户id删除角色和用户关系数据
	 * @param roleId
	 * @return
	 */
	int deleteObjectsByUserId(Integer userId);
	
	/**
	 * 将用户角色关系数据写入用户角色关系表
	 * @param userId
	 * @param roleIds
	 * @return
	 */
	int insertObjects(@Param("userId")Integer userId,
			@Param("roleIds")Integer[] roleIds);
}
