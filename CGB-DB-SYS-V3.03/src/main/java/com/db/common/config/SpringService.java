package com.db.common.config;


import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@ComponentScan({"com.db.common.aspect","com.db.sys.service"})
@EnableWebMvc
@EnableAspectJAutoProxy
@EnableTransactionManagement
public class SpringService {
	@Bean
	public DataSourceTransactionManager newDataSourceTransactionManager(DataSource dataSource) {
		DataSourceTransactionManager d = new DataSourceTransactionManager();
		d.setDataSource(dataSource);
		return d;
		
	}
}
