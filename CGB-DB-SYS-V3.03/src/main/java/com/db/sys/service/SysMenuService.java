package com.db.sys.service;

import java.util.List;
import java.util.Map;

import com.db.common.vo.Node;
import com.db.sys.entity.SysMenu;

public interface SysMenuService {
	List<Map<String,Object>> findObjects();
	/**
	 * 基于菜单id删除菜单以及对应的关系数据
	 * @param id
	 * @return
	 */
	int deleteObject(Integer id);
	
	List<Node> findZtreeMenuNodes();
	/**
	 * 将菜单信息传给数据层对象
	 * @param menu
	 * @return
	 */
	int saveObject(SysMenu menu);
	/**
	 * 将菜单信息更新到数据库
	 * @param menu
	 * @return
	 */
	int updateObject(SysMenu menu);
}
