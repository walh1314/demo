package com.foxconn.lamp.dto;

import java.io.Serializable;

/**
 * @author liupingan
 */
public class PointDto implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8193985930465175514L;
	private String x;
	private String y;
	public String getX()
	{
		return x;
	}
	public void setX(String x)
	{
		this.x = x;
	}
	public String getY()
	{
		return y;
	}
	public void setY(String y)
	{
		this.y = y;
	}
}
