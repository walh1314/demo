package com.foxconn.lamp.controller.device;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.foxconn.lamp.camera.domain.CameraType;
import com.foxconn.lamp.camera.service.CameraTypeService;
import com.foxconn.lamp.common.constant.URLConstant;
import com.foxconn.lamp.common.entity.ResultMap;
import com.foxconn.lamp.controller.base.BaseController;
import com.foxconn.lamp.dto.CameraTypeDto;

@RestController
@RequestMapping(URLConstant.CAMERA_TYPE_BASE)
public class CameraTypeController extends BaseController
{

	@Autowired
	private CameraTypeService cameraTypeService;

	@RequestMapping(value = URLConstant.CAMERA_TYPE_LIST, method =
	{ RequestMethod.POST, RequestMethod.GET })
	public ResultMap<? extends Object> getTypeList(CameraTypeDto bean)
	{
		return getMessage(cameraTypeService.selectListByMap(bean));
	}

	@RequestMapping(value = URLConstant.CAMERA_TYPE_DETAIL, method =
	{ RequestMethod.POST, RequestMethod.GET })
	public ResultMap<? extends Object> getDetail(@PathVariable("id") String id)
	{
		return getMessage(cameraTypeService.selectDetailById(id));
	}

	@RequestMapping(value = URLConstant.CAMERA_TYPE_ADD, method =
	{ RequestMethod.POST, RequestMethod.GET, RequestMethod.PUT })
	public ResultMap<? extends Object> addCameraType(CameraType bean)
	{
		return getMessage(cameraTypeService.addCameraType(bean));
	}
}
