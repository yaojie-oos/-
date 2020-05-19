package com.db.common.util;

import java.util.List;

import com.db.common.vo.PageObject;

public abstract class PageUtil {
	/**
	 * 泛型方法：方法返回值左侧加泛型
	 * 泛型是实现通用编程的一种手段
	 * @param <T>
	 * @param pageCurrent
	 * @param rowCount
	 * @param pageSize
	 * @param list
	 * @return
	 */
	public static <T> PageObject<T> newInstance(Integer pageCurrent, int rowCount, int pageSize, List<T> list) {
		PageObject<T> pageObject = new PageObject<T>();
		pageObject.setPageCount((rowCount-1)/pageSize+1);
		pageObject.setPageCurrent(pageCurrent);
		pageObject.setRecords(list);
		pageObject.setRowCount(rowCount);
		pageObject.setPageSize(pageSize);
		return pageObject;
	}
}
