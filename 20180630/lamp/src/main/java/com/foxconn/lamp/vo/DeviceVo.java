package com.foxconn.lamp.vo;

import com.alibaba.fastjson.annotation.JSONField;

public class DeviceVo
{
	private String name;
	private String deviceId;
	private String alias;
	private String manufacturer;
	private String macAddr;
	private DeviceTypeInfoVo deviceType;
	private String modelName;
	private String sku;
	private String topic;
	
	@JSONField(name="serial")
	private String serialNo;
	
	//是否有添加1：有，0：没有
	private Integer status;
	
	public Integer getStatus()
	{
		return status;
	}
	public void setStatus(Integer status)
	{
		this.status = status;
	}
	public String getTopic()
	{
		return topic;
	}
	public void setTopic(String topic)
	{
		this.topic = topic;
	}
	public DeviceTypeInfoVo getDeviceType()
	{
		return deviceType;
	}
	public void setDeviceType(DeviceTypeInfoVo deviceType)
	{
		this.deviceType = deviceType;
	}
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public String getDeviceId()
	{
		return deviceId;
	}
	public void setDeviceId(String deviceId)
	{
		this.deviceId = deviceId;
	}
	public String getAlias()
	{
		return alias;
	}
	public void setAlias(String alias)
	{
		this.alias = alias;
	}
	public String getManufacturer()
	{
		return manufacturer;
	}
	public void setManufacturer(String manufacturer)
	{
		this.manufacturer = manufacturer;
	}
	public String getMacAddr()
	{
		return macAddr;
	}
	public void setMacAddr(String macAddr)
	{
		this.macAddr = macAddr;
	}
	public String getModelName()
	{
		return modelName;
	}
	public void setModelName(String modelName)
	{
		this.modelName = modelName;
	}
	public String getSku()
	{
		return sku;
	}
	public void setSku(String sku)
	{
		this.sku = sku;
	}
	public String getSerialNo()
	{
		return serialNo;
	}
	public void setSerialNo(String serialNo)
	{
		this.serialNo = serialNo;
	}
}