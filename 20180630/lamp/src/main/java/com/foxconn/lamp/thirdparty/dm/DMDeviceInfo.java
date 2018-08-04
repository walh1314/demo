package com.foxconn.lamp.thirdparty.dm;

import java.util.List;

public class DMDeviceInfo
{

	private String topic;
	private String manufacturer;
	private String macAddr;
	private String imei;
	private String modelName;
	private String sku;
	private String osType;
	private String serialNo;

	private List<DeviceTypeInfo> deviceTypeInfo;

	public String getTopic()
	{
		return topic;
	}

	public void setTopic(String topic)
	{
		this.topic = topic;
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

	public String getImei()
	{
		return imei;
	}

	public void setImei(String imei)
	{
		this.imei = imei;
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

	public String getOsType()
	{
		return osType;
	}

	public void setOsType(String osType)
	{
		this.osType = osType;
	}

	public String getSerialNo()
	{
		return serialNo;
	}

	public void setSerialNo(String serialNo)
	{
		this.serialNo = serialNo;
	}

	public List<DeviceTypeInfo> getDeviceTypeInfo()
	{
		return deviceTypeInfo;
	}

	public void setDeviceTypeInfo(List<DeviceTypeInfo> deviceTypeInfo)
	{
		this.deviceTypeInfo = deviceTypeInfo;
	}

}
