package com.foxconn.lamp.controller.device;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.foxconn.lamp.common.constant.URLConstant;
import com.foxconn.lamp.common.entity.ResultMap;
import com.foxconn.lamp.controller.base.BaseController;
import com.foxconn.lamp.device.service.DeviceService;
import com.foxconn.lamp.dto.DeviceDto;

@RestController
@RequestMapping(URLConstant.DEVICE_BASE)
public class DeviceController extends BaseController
{

	@Autowired
	private DeviceService deviceService;

	@RequestMapping(value = URLConstant.DEVICE_REQUIRE_ADD_LIST, method =
	{ RequestMethod.POST, RequestMethod.GET })
	public ResultMap<? extends Object> getRequireAddDeviceList( DeviceDto bean)
	{
		return getMessage(deviceService.getRequireAddDeviceList(bean));
	}
	
	@RequestMapping(value = URLConstant.DEVICE_ADD_LIST, method =
	{ RequestMethod.POST, RequestMethod.GET })
	public ResultMap<? extends Object> addList(@RequestBody List<DeviceDto> beans)
	{
		return getMessage(deviceService.addDeviceList(beans));
	}
}
