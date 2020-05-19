package com.db.sys.service;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.db.sys.entity.SysPwd;
import com.db.sys.entity.SysUser;
import com.db.sys.vo.SysUserDeptVo;

public interface SysUserService extends PageService<SysUserDeptVo>{
	
	int validById(@Param("id")Integer id,
				  @Param("valid")Integer valid,
				  @Param("modifiedUser")String modifiedUser);	
	
	int saveObject(SysUser entity,Integer[] roleIds);
	int	updateObject(SysUser entity,Integer[] roleIds);

	Map<String,Object> findObjectById(Integer id);
	
	int updatePwd(String username,SysPwd entity);
}
