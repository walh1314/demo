package com.foxconn.lamp.vo;

import java.io.Serializable;

/**
 * @author liupingan
 */
public class PointVo implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4996995592688461160L;
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
