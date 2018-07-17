package com.foxconn.lamp.manager.domain;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author z77z
 * @since 2017-02-16
 */
public class SysPermissionInit implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4080634888180654009L;
	private Integer id;
	private String url;
	private String permissionInit;
	private Integer sort;

	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id)
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

	public String getPermissionInit()
	{
		return permissionInit;
	}

	public void setPermissionInit(String permissionInit)
	{
		this.permissionInit = permissionInit;
	}

	public Integer getSort()
	{
		return sort;
	}

	public void setSort(Integer sort)
	{
		this.sort = sort;
	}
}
