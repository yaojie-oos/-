package com.db.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.db.sys.entity.SysUser;
import com.db.sys.vo.SysUserDeptVo;

public interface SysUserDao {
	/**
	 * 基于用户名查询用户信息
	 * @param username
	 * @return
	 */
	SysUser findUserByUserName(String username);
	int updateObject(SysUser entity);
	
	/**
	 * 基于用户id查询用户所对应的信息
	 * @param id
	 * @return
	 */
	SysUserDeptVo findObjectById(Integer id);
	
	int getRowCount(@Param("username")String username);
	
	List<SysUserDeptVo> findPageObjects(
					@Param("username")String username,
					@Param("startIndex")Integer startIndex,
					@Param("pageSize")Integer pageSize);
	/**
	 * 禁用或者启用用户信息
	 * @param id 用户id
	 * @param valid 状态
	 * @param modifiedUser	修改用户
	 * @return	修改的行数
	 */
	int validById(@Param("id")Integer id,
				  @Param("valid")Integer valid,
				  @Param("modifiedUser")String modifiedUser);
	/**
	 * 保存用户自身信息
	 * @param entity
	 * @return
	 */
	int insertObject(SysUser entity);
	
	SysUser findByUsername(String username);
	
	int updatePwd(SysUser entity);
}
