package com.db.sys.service.impl;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.db.common.annotation.RequiredCache;
import com.db.common.annotation.RequiredLog;
import com.db.common.exception.ServiceException;
import com.db.common.util.PageUtil;
import com.db.common.vo.PageObject;
import com.db.sys.dao.SysLogDao;
import com.db.sys.entity.SysLog;

import com.db.sys.service.SysLogService;
@Service
public class SysLogServiceImpl implements SysLogService{
	@Autowired
	private SysLogDao sysLogDao;
	@Override
	@RequiredCache
	public PageObject<SysLog> findPageObject(Integer pageCurrent, String username) {
		//参数的合法性校验
		if(pageCurrent==null||pageCurrent<1) {
			throw new IllegalArgumentException("页码值无效");
		}
		//基于用户名统计总记录数并进行验证
		int rowCount = sysLogDao.getRowCount(username);
		if(rowCount==0) {
			throw new ServiceException("没有找到这个记录");
		}
		//基于查询条件查询当前页的日志信息
		int pageSize = 3;
		int startIndex = (pageCurrent-1)*pageSize;
		List<SysLog> list = sysLogDao.findPageObjects(username, startIndex, pageSize);
		//对查询的结果进行封装并返回
		return PageUtil.newInstance(pageCurrent, rowCount, pageSize, list);
	}
	@RequiresPermissions("sys:log:delete")
	@Override
	public int deleteById(Integer... ids) {
		//1.判定参数有效性
		if(ids==null||ids.length==0)
		throw new IllegalArgumentException("请先选择");
		//2.基于id执行删除操作
		int rows = sysLogDao.deleteById(ids);
		if(rows==0)
		throw new ServiceException("记录可能不存在");
		//返回删除结果
		return rows;
	}

}
