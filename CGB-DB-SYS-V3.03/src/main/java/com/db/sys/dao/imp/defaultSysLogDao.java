package com.db.sys.dao.imp;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.db.sys.dao.SysLogDao;
import com.db.sys.entity.SysLog;
@Repository("defaultSysLogDao")
public class defaultSysLogDao implements SysLogDao {
	@Autowired
	private SqlSessionFactory sf;

	@Override
	@Before
	public int deleteObjects(Integer... id) {
		SqlSession s = sf.openSession();
		SysLogDao mapper = s.getMapper(SysLogDao.class);
		int rows = mapper.deleteObjects(id);
		s.commit();
		s.close();
		return rows;
	}

	@Override
	public int getRowCount(String username) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<SysLog> findPageObjects(String username, Integer startIndex, Integer pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int deleteById(Integer... ids) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insertObject(SysLog entity) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
	
	

}
