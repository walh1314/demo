package com.foxconn.lamp.manager.domain;

import java.io.Serializable;

public class SysUserRole implements Serializable
{

	private static final long serialVersionUID = 2296810675938787305L;

	private Integer id;
	/**
	 * 用户ID
	 */
	private String uid;
	/**
	 * 角色ID
	 */
	private String rid;

	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	public String getUid()
	{
		return uid;
	}

	public void setUid(String uid)
	{
		this.uid = uid;
	}

	public String getRid()
	{
		return rid;
	}

	public void setRid(String rid)
	{
		this.rid = rid;
	}
}
