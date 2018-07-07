package com.foxconn.lamp.dto;

import java.io.Serializable;
import java.util.List;

/**
 * @author liupingan
 */
public class MarkLampDto implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5540485009379429066L;
	private String id;
	private PointDto location;
	private List<PointDto> points;
	public String getId()
	{
		return id;
	}
	public void setId(String id)
	{
		this.id = id;
	}
	public PointDto getLocation()
	{
		return location;
	}
	public void setLocation(PointDto location)
	{
		this.location = location;
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
