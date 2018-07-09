package com.foxconn.lamp.config.ds;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Aspect
@Slf4j
public class DataSourceInterceptor
{

	@Pointcut(value = "execution(public * com.foxconn.lamp.**.mapper.**.*(..))")
	private void lampDataSourceServicePointcut()
	{
	}

	/**
	 * 切换数据源1
	 */
	@Before("lampDataSourceServicePointcut()")
	public void dataSource1Interceptor()
	{
		log.debug("切换到数据源{}..............................", "lampDataSource");
		DbContextHolder.setDbType(DBTypeEnum.lampDataSource);
	}

	/*
	 * @Before("lampDataSource ServicePointcut()") public void
	 * dataSource2Interceptor(){
	 * log.debug("切换到数据源{}.......................","datasource2");
	 * DbContextHolder.setDbType(DBTypeEnum.datasource2); }
	 */
}