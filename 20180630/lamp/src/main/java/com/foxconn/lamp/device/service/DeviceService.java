package com.foxconn.lamp.device.service;

import java.util.List;
import java.util.Map;

import com.foxconn.lamp.common.entity.ResultMap;
import com.foxconn.lamp.dto.DeviceDto;
import com.foxconn.lamp.vo.DeviceVo;

public interface DeviceService
{
	/**
	 * 获得需要添加的设备List
	 * 
	 * @param bean
	 * @return
	 */
	ResultMap<List<DeviceVo>> getRequireAddDeviceList(DeviceDto bean);
	
	
	ResultMap<Map<String,Object>> addDeviceList(List<DeviceDto>  beans);
}
