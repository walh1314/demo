package com.foxconn.lamp.thirdparty.am;

import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 请求参数
 * 
 * @author liupingan
 *
 */
public class AvailableDeviceUserRequest
{

	@JSONField(name = "deviceTypeId")
	private List<Integer> deviceTypeIds;
	private Long memberCode;

	public AvailableDeviceUserRequest()
	{

	}

	public AvailableDeviceUserRequest(Long memberCode, List<Integer> deviceTypeIds)
	{
		this.deviceTypeIds = deviceTypeIds;
		this.memberCode = memberCode;
	}

	public Long getMemberCode()
	{
		return memberCode;
	}

	public void setMemberCode(Long memberCode)
	{
		this.memberCode = memberCode;
	}

	public List<Integer> getDeviceTypeIds()
	{
		return deviceTypeIds;
	}

	public void setDeviceTypeIds(List<Integer> deviceTypeIds)
	{
		this.deviceTypeIds = deviceTypeIds;
	}
	
	

}
