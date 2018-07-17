package com.foxconn.lamp.vo;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

/**
 * @author liupingan
 */
@Slf4j
public class MarkLampsVo implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1790780492798256420L;

	private String id;
	
	private String deviceId;

	private String maintainer;

	private String type;

	private String desc;
	
	private Long status;

	@JSONField(name = "sn")
	private String serail;

	private List<PointVo> points;

	@JSONField(serialize = false)
	private String pointsString;// 新添加的字段

	
	public Long getStatus()
	{
		return status;
	}

	public void setStatus(Long status)
	{
		this.status = status;
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

	public String getDesc()
	{
		return desc;
	}

	public void setDesc(String desc)
	{
		this.desc = desc;
	}

	public String getSerail()
	{
		return serail;
	}

	public void setSerail(String serail)
	{
		this.serail = serail;
	}

	public List<PointVo> getPoints()
	{
		return points;
	}

	public void setPoints(List<PointVo> points)
	{
		this.points = points;
	}

	public String getPointsString()
	{
		return pointsString;
	}

	public void setPointsString(String pointsString)
	{
		this.pointsString = pointsString;
		ObjectMapper objmapper = new ObjectMapper();  
        try {  
            @SuppressWarnings("unchecked")
			List<PointVo> list = objmapper.readValue(pointsString,List.class);//将json字符串转化成list  
            setPoints(list);//调用setStar方法  
            this.pointsString = pointsString;  
        } catch (IOException e) {  
           log.error(e.getMessage());
        } 
	}

}
