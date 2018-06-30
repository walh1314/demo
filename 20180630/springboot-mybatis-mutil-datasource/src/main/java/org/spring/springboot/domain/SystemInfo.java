package org.spring.springboot.domain;

/**
 * 系统信息类 Created by bysocket on 07/02/2017.
 */
public class SystemInfo
{

	private Long id;

	private String pid;
	/**
	 * 描述
	 */
	private String description;

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
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
