package com.foxconn.lamp.thirdparty.dm;

public class DeviceInfoRequest
{

	private String deviceId;

	public DeviceInfoRequest(){
		
	}
	public DeviceInfoRequest(String deviceId){
		this.deviceId = deviceId;
	}
	public String getDeviceId()
	{
		return deviceId;
	}

	public void setDeviceId(String deviceId)
	{
		this.deviceId = deviceId;
	}
}
