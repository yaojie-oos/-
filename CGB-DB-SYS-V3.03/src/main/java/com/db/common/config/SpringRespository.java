package com.db.common.config;

import java.io.IOException;

import javax.sql.DataSource;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import com.alibaba.druid.pool.DruidDataSource;

@MapperScan("com.db.**.dao")
@PropertySource("classpath:db.properties")
public class SpringRespository {
	@Bean
	@Lazy(false)
	public DataSource newDruidDataSource(Environment e) {
		DruidDataSource dataSource = new DruidDataSource();
		dataSource.setPassword(e.getProperty("jdbcPassword"));
		dataSource.setUsername(e.getProperty("jdbcUser"));
		dataSource.setUrl(e.getProperty("jdbcUrl"));
		dataSource.setDriverClassName(e.getProperty("jdbcDriver"));
		return dataSource;
	}
	
	@Bean
	public SqlSessionFactoryBean newSqlSessionFactoryBean(DataSource dataSource) throws IOException {
		SqlSessionFactoryBean factory = new SqlSessionFactoryBean();
		factory.setDataSource(dataSource);
		Resource[] mapperLocations = new PathMatchingResourcePatternResolver().
				getResources("classpath*:mapper/sys/*Mapper.xml");
		factory.setMapperLocations(mapperLocations);
		return factory;
	}
}
