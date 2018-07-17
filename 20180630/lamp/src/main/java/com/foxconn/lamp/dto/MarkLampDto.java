package com.foxconn.lamp.dto;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author liupingan
 */
public class MarkLampDto implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5540485009379429066L;
	private String deviceId;

	private String id;

	private String maintainer;

	private String type;

	@JSONField(name = "sn")
	private String serail;

	private String desc;

	private List<PointDto> points;

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getDeviceId()
	{
		return deviceId;
	}

	public void setDeviceId(String deviceId)
	{
		this.deviceId = deviceId;
	}

	public String getMaintainer()
	{
		return maintainer;
	}

	public void setMaintainer(String maintainer)
	{
		this.maintainer = maintainer;
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public String getSerail()
	{
		return serail;
	}

	public void setSerail(String serail)
	{
		this.serail = serail;
	}

	public String getDesc()
	{
		return desc;
	}

	public void setDesc(String desc)
	{
		this.desc = desc;
	}

	public List<PointDto> getPoints()
	{
		return points;
	}

	public void setPoints(List<PointDto> points)
	{
		this.points = points;
	}
}
