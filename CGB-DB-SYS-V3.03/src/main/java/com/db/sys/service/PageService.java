package com.db.sys.service;

import com.db.common.vo.PageObject;
//111111111111111
public interface PageService<T> {
	PageObject<T> findPageObjects(Integer pageCurrent,
			String username);

}
