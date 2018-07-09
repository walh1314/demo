package com.foxconn.lamp.config.ds;

public enum DBTypeEnum
{
	lampDataSource("lampDataSource");
	private String value;

	DBTypeEnum(String value)
	{
		this.value = value;
	}

	public String getValue()
	{
		return value;
	}
}
