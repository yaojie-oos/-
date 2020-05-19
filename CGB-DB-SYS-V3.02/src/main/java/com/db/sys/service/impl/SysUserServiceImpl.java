package com.db.sys.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.db.common.annotation.RequiredLog;
import com.db.common.exception.ServiceException;
import com.db.common.util.PageUtil;
import com.db.common.vo.PageObject;
import com.db.sys.dao.SysUserDao;
import com.db.sys.dao.SysUserRoleDao;
import com.db.sys.entity.SysPwd;
import com.db.sys.entity.SysUser;
import com.db.sys.service.SysUserService;
import com.db.sys.vo.SysUserDeptVo;
@Service
@Transactional(rollbackFor = Throwable.class,
				isolation = Isolation.READ_COMMITTED,
				timeout = 60)
public class SysUserServiceImpl implements SysUserService{
	@Autowired
	private SysUserDao sysUserDao;

	@Autowired
	private SysUserRoleDao sysUserRoleDao;
	@Override
	@Transactional(readOnly = true)
	public PageObject<SysUserDeptVo> findPageObjects(Integer pageCurrent,String username) {
		//1.参数校验
		if(pageCurrent==null||pageCurrent<1)
			throw new IllegalArgumentException("页码值无效");
		//2.查询总记录数并且校验
		int rowCount = sysUserDao.getRowCount(username);
		if(rowCount==0)
			throw new ServiceException("没有对应记录");
		//3.查询当前页要呈现的记录
		int pageSize = 3;
		int startIndex = (pageCurrent-1)*pageSize;
		List<SysUserDeptVo> records = sysUserDao.findPageObjects(username, startIndex, pageSize);
		PageObject<SysUserDeptVo> pageObjects = PageUtil.newInstance(pageCurrent, rowCount, pageSize, records);
		//4.封装数据并且返回
		return pageObjects;
	}
	
	@RequiredLog("禁用启用")
	@RequiresPermissions("sys:user:valid")
	@Override
	public int validById(Integer id, Integer valid, String modifiedUser) {
		//1.参数校验
		if(id==null||id<1)
			throw new IllegalArgumentException("参数不存在");
		if(valid==null||(valid!=0&&valid!=1))
			throw new IllegalArgumentException("valid值无效");
		//2.修改状态
		int rows = sysUserDao.validById(id, valid, modifiedUser);
		if(rows==0)
			throw new ServiceException("记录不存在");
		//3.返回结果
		return rows;
	}
	@Override
	public int saveObject(SysUser entity, Integer[] roleIds) {
		//1.参数校验
		if(entity==null)
			throw new IllegalArgumentException("保存对象不能为空");
		if(StringUtils.isEmpty(entity.getUsername()))
			throw new IllegalArgumentException("用户名不能为空");
		if(StringUtils.isEmpty(entity.getPassword()))
			throw new IllegalArgumentException("密码不能为空");

		if(roleIds==null||roleIds.length==0)
			throw new ServiceException("必须为用户分配角色信息");
		//2.保存用户自身信息
		//2.0构建一个盐值对象
		String salt = UUID.randomUUID().toString();
		//2.1对密码进行加密
		SimpleHash sh = new SimpleHash("MD5",entity.getPassword(),salt,1);
		String password = sh.toHex();
		entity.setPassword(password);
		entity.setSalt(salt);
		sysUserDao.insertObject(entity);
		//3.保存用户和角色关系数据
		int rows=sysUserRoleDao.insertObjects(entity.getId(), roleIds);
		//4.返回结果
		return rows;
	}
	@Override
	public Map<String, Object> findObjectById(Integer id) {
		//1.参数校验
		if(id==null||id<1)
			throw new IllegalArgumentException("id值不正确");
		//2.基于id查询用户以及对应部门信息
		SysUserDeptVo user = sysUserDao.findObjectById(id);
		if(user==null)
			throw new ServiceException("用户信息不存在");
		//3.基于id查询用户对应的角色id
		List<Integer> roleIds = sysUserRoleDao.findRoleIdsByUserId(id);
		//4.对信息进行封装
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("user", user);
		map.put("roleIds", roleIds);
		return map;
	}
	
	
	@Override
	public int updateObject(SysUser entity, Integer[] roleIds) {
		//1.参数校验
		if(entity==null)
			throw new IllegalArgumentException("保存对象不能为空");
		if(StringUtils.isEmpty(entity.getUsername()))
			throw new IllegalArgumentException("用户名不能为空");
		

		if(roleIds==null||roleIds.length==0)
			throw new ServiceException("必须为用户分配角色信息");
		
		int rows = sysUserDao.updateObject(entity);
		if(rows==0)
		throw new ServiceException("用户信息不存在");
		sysUserRoleDao.deleteObjectsByUserId(entity.getId());
		int row = sysUserRoleDao.insertObjects(entity.getId(), roleIds);
		//4.返回结果
		return row;
	}

	@Override
	public int updatePwd(String username,SysPwd entity) {
		if(entity==null)
			throw new IllegalArgumentException("数据不存在");
		if(StringUtils.isEmpty(entity.getPwd()))
			throw new ServiceException("请输入原密码");
		if(StringUtils.isEmpty(entity.getCfgPwd()))
			throw new ServiceException("请输入再次输入密码");
		if(StringUtils.isEmpty(entity.getNewPwd()))
			throw new ServiceException("请输入新密码");
		
		SysUser user = sysUserDao.findByUsername(username);
		System.out.println(username);
		String salt = user.getSalt();
		SimpleHash sh = new SimpleHash("MD5",entity.getPwd(),salt,1);
		String password = sh.toHex();
		if(!password.equals(user.getPassword()))
			throw new ServiceException("原始密码不正确！");
		if(!entity.getCfgPwd().equals(entity.getNewPwd()))
			throw new ServiceException("两次密码不一致");
		salt = UUID.randomUUID().toString();
		sh = new SimpleHash("MD5",entity.getNewPwd(),salt,1);
		password = sh.toHex();
		entity.setSalt(salt);
		entity.setNewPwd(password);
		
		SysUser sysUser = new SysUser();
		sysUser.setPassword(password);
		sysUser.setSalt(salt);
		sysUser.setUsername(username);
		int rows = sysUserDao.updatePwd(sysUser);
		return rows;
	}



}
