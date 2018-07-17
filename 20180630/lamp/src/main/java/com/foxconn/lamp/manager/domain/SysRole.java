package com.foxconn.lamp.manager.domain;

import java.io.Serializable;

public class SysRole implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8354458405935109118L;
	private Integer id;
	/**
	 * 角色名称
	 */
	private String name;
	/**
	 * 角色类型
	 */
	private String type;

	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}
}
