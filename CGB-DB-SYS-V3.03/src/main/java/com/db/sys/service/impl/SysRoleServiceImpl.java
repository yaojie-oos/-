package com.db.sys.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.db.common.exception.ServiceException;
import com.db.common.util.PageUtil;
import com.db.common.vo.CheckBox;
import com.db.common.vo.PageObject;
import com.db.sys.dao.SysRoleDao;
import com.db.sys.dao.SysRoleMenuDao;
import com.db.sys.dao.SysUserRoleDao;
import com.db.sys.entity.SysRole;
import com.db.sys.service.SysRoleService;
import com.db.sys.vo.SysRoleMenuVo;
@Service
public class SysRoleServiceImpl implements SysRoleService {
	@Autowired
	private SysRoleDao sysRoleDao;
	@Autowired
	private SysUserRoleDao sysUserRoleDao;
	@Autowired
	private SysRoleMenuDao sysRoleMenuDao;
	@Override
	public PageObject<SysRole> findPageObjects(String name, Integer pageCurrent) {
		//1.参数校验
		if(pageCurrent==null||pageCurrent<1)
		throw new IllegalArgumentException("当前页码不正确");
		//2.查询总记录数并进行验证
		int rowCount = sysRoleDao.getRowCount(name);
		if(rowCount==0)
		throw new ServiceException("没有对应的记录");
		//3.查询当前页
		int pageSize=3;
		int startIndex = (pageCurrent-1)*pageSize;
		List<SysRole> list = sysRoleDao.findPageObjects(name, startIndex, pageSize);
		//4.进行分装并返回
		return PageUtil.newInstance(pageCurrent, rowCount, pageSize, list);
	}
	@Override
	public int deleteObject(Integer id) {
		//1.参数校验
		if(id==null||id<1) 
		throw new IllegalArgumentException("id值无效");
		//2.执行删除
		//2.1删除角色和用户关系数据
		sysUserRoleDao.deleteObjectsByRoleId(id);
		//2.2删除角色和菜单关系数据
		sysRoleMenuDao.deleteObjectsByRoleId(id);
		//2.3删除角色自身信息
		int rows=sysRoleDao.deleteObject(id);
		if(rows==0)
		throw new ServiceException("记录不存在");
		//3.返回结果
		return rows;
	}
	@Override
	public int saveObject(SysRole entity, Integer[] menuIds) {
		//1.参数校验
		if(entity==null)
		throw new IllegalArgumentException("保存对象不能为空");
		if(StringUtils.isEmpty(entity.getName()))
		throw new IllegalArgumentException("角色名称不能为空");
		if(menuIds==null||menuIds.length==0)
		throw new IllegalArgumentException("必须为角色分配权限");
		//2.保存角色自身信息
		int rows = sysRoleDao.insertObject(entity);
		//3.保存角色和菜单的关系数据
		sysRoleMenuDao.insertObjects(entity.getId(), menuIds);
		//4.返回结果
		return rows;
	}
	@Override
	public SysRoleMenuVo findObjectById(Integer id) {
		//1.参数校验
		if(id==null||id<1)
		throw new IllegalArgumentException("id值无效");
		//2.基于id查询数据
		//2.1查询角色自身信息
		//2.2查询角色对应菜单id
		SysRoleMenuVo vo = sysRoleDao.findObjectById(id);
		if(vo==null)
		throw new ServiceException("记录不存在");
		return vo;
	}
	@Override
	public int updateObject(SysRole entity, Integer[] menuIds) {
		//1.参数校验
		if(entity==null)
		throw new IllegalArgumentException("保存对象不能为空");
		if(StringUtils.isEmpty(entity.getName()))
		throw new IllegalArgumentException("角色名称不能为空");
		if(menuIds==null||menuIds.length==0)
		throw new IllegalArgumentException("必须为角色分配权限");
		int rowCount = sysRoleDao.getCountByName(entity.getName());
		
		//2.更新角色自身信息
		int rows = sysRoleDao.updateObject(entity);
		//3.更新角色和菜单的关系数据
		//3.1先删除关系数据
		sysRoleMenuDao.deleteObjectsByRoleId(entity.getId());
		//3.2再添加新的关系数据
		sysRoleMenuDao.insertObjects(entity.getId(), menuIds);
		//4.返回结果
		return rows;
	}
	@Override
	public List<CheckBox> findObjects() {
		List<CheckBox> list = sysRoleDao.findObjects();
		if(list==null||list.isEmpty())
		throw new ServiceException("系统没有角色信息");
		return list;
	}
	@Override
	public void isRoleExist(String name) {
		if(StringUtils.isEmpty(name))
		throw new IllegalArgumentException("角色名不能为空!!!");
		int rows = sysRoleDao.getCountByName(name);
		if(rows>0)
		throw new ServiceException("此角色已存在");
	}
	

}
