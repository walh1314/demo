package org.spring.springboot.dto;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author liupingan
 */
public class Ac2CheckData implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6723301719853832546L;

	@JSONField(name = "username")
	private String userName;

	public String getUserName()
	{
		return userName;
	}

	public void setUserName(String userName)
	{
		this.userName = userName;
	}

}
