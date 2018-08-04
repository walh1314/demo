package com.foxconn.lamp.dto;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;

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

	@JSONField(serialzeFeatures={SerializerFeature.DisableCircularReferenceDetect})
	private List<PointDto> points;
	
	/**
	 * 需要的图片类型
	 */
	private String imageType;
	
	/**
	 * 需要获取的图片宽度
	 */
	private String imageWidth;
	
	/**
	 * 图片高度
	 */
	private String imageHeight;

	private double scale;
	
	private List<Integer> threshold;
	
	
	public List<Integer> getThreshold()
	{
		return threshold;
	}

	public void setThreshold(List<Integer> threshold)
	{
		this.threshold = threshold;
	}

	public double getScale()
	{
		return scale;
	}

	public void setScale(double scale)
	{
		this.scale = scale;
	}

	public String getImageWidth()
	{
		return imageWidth;
	}

	public void setImageWidth(String imageWidth)
	{
		this.imageWidth = imageWidth;
	}

	public String getImageHeight()
	{
		return imageHeight;
	}

	public void setImageHeight(String imageHeight)
	{
		this.imageHeight = imageHeight;
	}

	public String getImageType()
	{
		return imageType;
	}

	public void setImageType(String imageType)
	{
		this.imageType = imageType;
	}

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
