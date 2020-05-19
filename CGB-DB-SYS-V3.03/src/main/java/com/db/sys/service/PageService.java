package com.db.sys.service;

import com.db.common.vo.PageObject;
//123123123
public interface PageService<T> {
	PageObject<T> findPageObjects(Integer pageCurrent,
			String username);

}
