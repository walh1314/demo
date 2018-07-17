package com.foxconn.lamp.manager.domain;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.TableName;

/**
 * <p>
 * 
 * </p>
 *
 * @author z77z
 * @since 2017-02-13
 */
@TableName("sys_permission")
public class SysPermission implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2922376743661763427L;
	private String id;
	/**
	 * url地址
	 */
	private String url;
	/**
	 * url描述
	 */
	private String name;

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getUrl()
	{
		return url;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

}
