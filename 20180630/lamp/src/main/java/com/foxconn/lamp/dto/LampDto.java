package com.foxconn.lamp.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * @author liupingan
 */
public class LampDto implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3228261735467104484L;
	private String id;
	private String deviceId;
	private String name; // 设备名
	private String identifier;// 设备编号

	private String type;// 设备类型
	private Date exFactoryDate;// 出厂日期
	private String status;// 状态
	private String desc; // 备注

	private Date createTime;

	private String createUser;
	private String updateUser;
	private Date updateTime;
	
	private String points;
	
	public String getPoints()
	{
		return points;
	}

	public void setPoints(String points)
	{
		this.points = points;
	}

	public String getDeviceId()
	{
		return deviceId;
	}

	public void setDeviceId(String deviceId)
	{
		this.deviceId = deviceId;
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getIdentifier()
	{
		return identifier;
	}

	public void setIdentifier(String identifier)
	{
		this.identifier = identifier;
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public Date getExFactoryDate()
	{
		return exFactoryDate;
	}

	public void setExFactoryDate(Date exFactoryDate)
	{
		this.exFactoryDate = exFactoryDate;
	}

	public String getStatus()
	{
		return status;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}

	public String getDesc()
	{
		return desc;
	}

	public void setDesc(String desc)
	{
		this.desc = desc;
	}

	public Date getCreateTime()
	{
		return createTime;
	}

	public void setCreateTime(Date createTime)
	{
		this.createTime = createTime;
	}

	public String getCreateUser()
	{
		return createUser;
	}

	public void setCreateUser(String createUser)
	{
		this.createUser = createUser;
	}

	public String getUpdateUser()
	{
		return updateUser;
	}

	public void setUpdateUser(String updateUser)
	{
		this.updateUser = updateUser;
	}

	public Date getUpdateTime()
	{
		return updateTime;
	}

	public void setUpdateTime(Date updateTime)
	{
		this.updateTime = updateTime;
	}

}
