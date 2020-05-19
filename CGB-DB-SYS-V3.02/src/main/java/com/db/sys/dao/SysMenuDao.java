package com.db.sys.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.db.common.vo.Node;
import com.db.sys.entity.SysMenu;

public interface SysMenuDao {
	/*
	 * 是否有子菜单
	 */
	int getChildCount(Integer id);
	/*
	 * 基于id删除菜单
	 */
	int deleteObject(Integer id);
	/*
	 * 查询节点
	 */
	List<Node> findZtreeMenuNodes();
	/*
	 * 将内存中的menu对象持久化到数据库
	 */
	int insertObject(SysMenu menu);
	/*
	 * 将内存中的menu对象更新到数据库
	 */
	int updateObject(SysMenu menu);
	List<Map<String,Object>> findObjects();
	
	List<String> findPermissions(@Param("menuIds")Integer[] menuIds);
}
