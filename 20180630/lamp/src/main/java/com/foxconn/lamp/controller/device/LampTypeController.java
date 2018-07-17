package com.foxconn.lamp.controller.device;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.foxconn.lamp.common.constant.URLConstant;
import com.foxconn.lamp.common.entity.ResultMap;
import com.foxconn.lamp.controller.base.BaseController;
import com.foxconn.lamp.device.domain.LampType;
import com.foxconn.lamp.device.service.LampTypeService;
import com.foxconn.lamp.dto.LampTypeDto;

@RestController
@RequestMapping(URLConstant.LAMP_TYPE_BASE)
public class LampTypeController extends BaseController
{

	@Autowired
	private LampTypeService lampTypeService;

	@RequestMapping(value = URLConstant.LAMP_TYPE_LIST, method =
	{ RequestMethod.POST, RequestMethod.GET })
	public ResultMap<? extends Object> getTypeList(LampTypeDto bean)
	{
		return getMessage(lampTypeService.selectListByMap(bean));
	}

	@RequestMapping(value = URLConstant.LAMP_TYPE_DETAIL, method =
	{ RequestMethod.POST, RequestMethod.GET })
	public ResultMap<? extends Object> getDetail(@PathVariable("id") String id)
	{
		return getMessage(lampTypeService.selectDetailById(id));
	}

	@RequestMapping(value = URLConstant.LAMP_TYPE_ADD, method =
	{ RequestMethod.POST, RequestMethod.GET, RequestMethod.PUT })
	public ResultMap<? extends Object> addLampType(LampType bean)
	{
		return getMessage(lampTypeService.addLampType(bean));
	}
}
