package com.foxconn.lamp.config.ds;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.alibaba.druid.pool.DruidDataSource;

@Configuration
// 扫描 Mapper 接口并容器管理
@MapperScan(basePackages = LampDataSourceConfig.PACKAGE, sqlSessionFactoryRef = "lampSqlSessionFactory")
public class LampDataSourceConfig
{

	// 精确到 master 目录，以便跟其他数据源隔离
	static final String PACKAGE = "com.foxconn.lamp.**.mapper";
	static final String MAPPER_LOCATION = "classpath:mapper/lamp/*.xml";

	@Value("${lamp.datasource.url}")
	private String url;

	@Value("${lamp.datasource.username}")
	private String user;

	@Value("${lamp.datasource.password}")
	private String password;

	@Value("${lamp.datasource.driverClassName}")
	private String driverClass;

	@Bean(name = "lampDataSource")
	@Primary
	public DataSource LampDataSource()
	{
		DruidDataSource dataSource = new DruidDataSource();
		dataSource.setDriverClassName(driverClass);
		dataSource.setUrl(url);
		dataSource.setUsername(user);
		dataSource.setPassword(password);
		return dataSource;
	}

	@Bean(name = "lampTransactionManager")
	@Primary
	public DataSourceTransactionManager LampTransactionManager()
	{
		return new DataSourceTransactionManager(LampDataSource());
	}

	@Bean(name = "lampSqlSessionFactory")
	@Primary
	public SqlSessionFactory LampSqlSessionFactory(@Qualifier("lampDataSource") DataSource LampDataSource)
			throws Exception
	{
		final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
		sessionFactory.setDataSource(LampDataSource);
		sessionFactory.setMapperLocations(
				new PathMatchingResourcePatternResolver().getResources(LampDataSourceConfig.MAPPER_LOCATION));
		return sessionFactory.getObject();
	}
}