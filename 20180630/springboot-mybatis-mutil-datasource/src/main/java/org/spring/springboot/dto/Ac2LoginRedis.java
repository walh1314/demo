package org.spring.springboot.dto;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author liupingan
 */
public class Ac2LoginRedis implements Serializable
{

	/**
	 * 
	 */	
	private static final long serialVersionUID = -5413969120874484757L;

	@JSONField(name = "TOKEN")
	private String token;

	private String pid;

	public String getToken()
	{
		return token;
	}

	public void setToken(String token)
	{
		this.token = token;
	}

	public String getPid()
	{
		return pid;
	}

	public void setPid(String pid)
	{
		this.pid = pid;
	}

	

}
