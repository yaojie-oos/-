package com.db.sys.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.db.common.exception.ServiceException;
import com.db.common.vo.Node;
import com.db.sys.dao.SysMenuDao;
import com.db.sys.dao.SysRoleMenuDao;
import com.db.sys.entity.SysMenu;
import com.db.sys.service.SysMenuService;

@Service
public class SysMenuServiceImpl implements SysMenuService{
	@Autowired
	private SysMenuDao sysMenuDao;
	@Autowired
	private SysRoleMenuDao sysRoleMenuDao;
	@Override
	public List<Map<String, Object>> findObjects() {
		List<Map<String, Object>> list = sysMenuDao.findObjects();
		if(list==null||list.size()==0) {
			throw new ServiceException("记录不存在");
		}
		return list;
	}
	@Override
	public int deleteObject(Integer id) {
		//1.参数校验
		if(id==null||id<1) 
		throw new ServiceException("id值无效");
		//2.基于id判断此菜单是否有子元素
		int rowCount = sysMenuDao.getChildCount(id);
		if (rowCount>0) 
		throw new ServiceException("请先删除子元素");
		//3.基于id删除菜单与角色关系数据
		sysRoleMenuDao.deleteObjectsByMenuId(id);
		//4.基于id删除菜单自身数据
		int rows = sysMenuDao.deleteObject(id);
		if(rows==0)throw new ServiceException("记录已经不存在");
		//5.返回结果
		return rows;
	}
	@Override
	public List<Node> findZtreeMenuNodes() {
		return sysMenuDao.findZtreeMenuNodes();
	}
	@Override
	public int saveObject(SysMenu menu) {
		//1.校验参数
		if(menu==null)
		throw new IllegalArgumentException("保存对象不能为空");
		if(StringUtils.isEmpty(menu.getName()))
		throw new IllegalArgumentException("菜单名不能为空");
		//2.保存数据
		int row = sysMenuDao.insertObject(menu);
		//3.返回结果
		return row;
	}
	
	@Override
	public int updateObject(SysMenu menu) {
		//1.校验参数
		if(menu==null)
		throw new IllegalArgumentException("保存对象不能为空");
		if(StringUtils.isEmpty(menu.getName()))
		throw new IllegalArgumentException("菜单名不能为空");
		//2.保存数据
		int row = sysMenuDao.updateObject(menu);
		if(row==0)throw new ServiceException("记录不存在");
		//3.返回结果
		return row;
	}

	

}
