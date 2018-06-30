package org.spring.springboot.dto;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author liupingan
 */
public class Ac2LoginResult implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6723301719853832546L;

	@JSONField(name = "TOKEN")
	private String token;

	private String status;

	public String getToken()
	{
		return token;
	}

	public void settoken(String token)
	{
		this.token = token;
	}

	public String getStatus()
	{
		return status;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}

}
