package com.foxconn.lamp.dto;

import java.io.Serializable;

import com.foxconn.lamp.vo.DeviceTypeInfoVo;

public class DeviceDto implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2531590063645328253L;
	private String userId;
	private String mobile;
	
	private String name;
	private String deviceId;
	private String alias;
	private String manufacturer;
	private String macAddr;
	private DeviceTypeInfoVo deviceType;
	private String modelName;
	private String sku;
	private String serial;
	
	private Integer status;
	
	private String topic;

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

	public DeviceTypeInfoVo getDeviceType()
	{
		return deviceType;
	}

	public void setDeviceType(DeviceTypeInfoVo deviceType)
	{
		this.deviceType = deviceType;
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

	public String getSerial()
	{
		return serial;
	}

	public void setSerial(String serial)
	{
		this.serial = serial;
	}

	public String getUserId()
	{
		return userId;
	}

	public void setUserId(String userId)
	{
		this.userId = userId;
	}

	public String getMobile()
	{
		return mobile;
	}

	public void setMobile(String mobile)
	{
		this.mobile = mobile;
	}

}
