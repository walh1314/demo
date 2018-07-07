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
	private String password; // hashed密码
	private String age; // 年龄
	private String status; // 用户状态
	private String email; // 邮箱
	private String mobile; // 电话号码
	private String info; // 说明
	private String token; // 说明

	
	
	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getToken()
	{
		return token;
	}

	public void setToken(String token)
	{
		this.token = token;
	}

	public UserDto(){
		
	}
	
	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public String getAge()
	{
		return age;
	}

	public void setAge(String age)
	{
		this.age = age;
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

	public String getMobile()
	{
		return mobile;
	}

	public void setMobile(String mobile)
	{
		this.mobile = mobile;
	}

	public String getInfo()
	{
		return info;
	}

	public void setInfo(String info)
	{
		this.info = info;
	}
}
