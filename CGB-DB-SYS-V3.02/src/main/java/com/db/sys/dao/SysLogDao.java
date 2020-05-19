package com.db.sys.dao;
import java.util.List;

/*
 * 日志接口
 */
import org.apache.ibatis.annotations.Param;

import com.db.sys.entity.SysLog;

public interface SysLogDao {
	/*
	 * 基于id删除日志信息
	 */
	int deleteObjects(@Param("ids")Integer... id);
	/**
	 * 基于查询条件统计总记录数
	 * @param username
	 * @return
	 */
	int getRowCount(@Param("username")String username);
	/**
	 * 基于条件查询当前页面要呈现的日志信息
	 * @param username  用户名
	 * @param startIndex	当前页起始位置
	 * @param pageSize	页面大小（每一页最多显示多少条记录）
	 * @return 当前页记录
	 */
	List<SysLog> findPageObjects(@Param("username")String username,
								@Param("startIndex")Integer startIndex,
								@Param("pageSize")Integer pageSize);
	int deleteById(@Param("ids")Integer... ids);
	/**日志信息写入到数据库*/
	int insertObject(SysLog entity);
}
