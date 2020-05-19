package com.db.sys.service;

import com.db.common.vo.PageObject;
import com.db.sys.entity.SysLog;

/**
 * 日志模块的业务接口
 * 负责日志业务的规范定义
 * @author 11716
 *
 */
public interface SysLogService {
	/**
	 * 按条件执行用户行为日志的查询操作
	 * @param pageCurrent 当前页码
	 * @param username 查询条件
	 * @return	当前页需要呈现的日志数据
	 */
	PageObject<SysLog> findPageObject(Integer pageCurrent,
										String username);

	int deleteById(Integer... ids);
}
