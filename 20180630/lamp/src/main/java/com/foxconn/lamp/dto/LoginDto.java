package com.foxconn.lamp.dto;

import java.io.Serializable;

public class LoginDto implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2050429407160767435L;
	private String userName;
	private String password;
	public String getUserName()
	{
		return userName;
	}
	public void setUserName(String userName)
	{
		this.userName = userName;
	}
	public String getPassword()
	{
		return password;
	}
	public void setPassword(String password)
	{
		this.password = password;
	}
}
