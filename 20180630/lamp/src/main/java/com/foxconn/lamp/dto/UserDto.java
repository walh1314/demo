package com.foxconn.lamp.dto;

import java.io.Serializable;

public class UserDto implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5730048495641586644L;
	/**
	 * 
	 */
	private String id;
	private String name; // 用户名
	private String status; // 用户状态
	private String email; // 邮箱
	public String getId()
	{
		return id;
	}
	public void setId(String id)
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
	public String getStatus()
	{
		return status;
	}
	public void setStatus(String status)
	{
		this.status = status;
	}
	public String getEmail()
	{
		return email;
	}
	public void setEmail(String email)
	{
		this.email = email;
	}

	
}
