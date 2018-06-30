package org.spring.springboot.dto;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author liupingan
 */
public class Ac2CheckResult implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6723301719853832546L;

	@JSONField(name = "status")
	private String status;

	private Ac2CheckData data;

	public String getStatus()
	{
		return status;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}

	public Ac2CheckData getData()
	{
		return data;
	}

	public void setData(Ac2CheckData data)
	{
		this.data = data;
	}


	
	

}
