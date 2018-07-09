package com.foxconn.lamp.config.ds;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class DynamicDataSource extends AbstractRoutingDataSource
{

	@Override
	protected Object determineCurrentLookupKey()
	{
		return DbContextHolder.getDbType();
	}
}
