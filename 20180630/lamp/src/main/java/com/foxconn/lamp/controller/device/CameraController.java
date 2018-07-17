package com.foxconn.lamp.controller.device;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.foxconn.lamp.camera.domain.CameraInfo;
import com.foxconn.lamp.camera.service.CameraService;
import com.foxconn.lamp.common.constant.URLConstant;
import com.foxconn.lamp.common.entity.FrontPage;
import com.foxconn.lamp.common.entity.ResultMap;
import com.foxconn.lamp.controller.base.BaseController;
import com.foxconn.lamp.dto.CameraInfoDto;
import com.foxconn.lamp.dto.MarkLampDto;

@RestController
@RequestMapping(URLConstant.CAMERA_BASE)
public class CameraController extends BaseController
{

	@Autowired
	private CameraService cameraService;

	@RequestMapping(value = URLConstant.CAMERA_PAGE_LIST, method =
	{ RequestMethod.POST, RequestMethod.GET })
	public ResultMap<? extends Object> getPageList(CameraInfoDto bean, FrontPage<CameraInfo> page)
	{
		return getMessage(cameraService.selectPage(page, bean));
	}

	@RequestMapping(value = URLConstant.CAMERA_DETAIL, method =
	{ RequestMethod.POST, RequestMethod.GET })
	public ResultMap<? extends Object> getDetail(@PathVariable("id") String id)
	{
		return getMessage(cameraService.selectDetailById(id));
	}

	@RequestMapping(value = URLConstant.CAMERA_ADD, method =
	{ RequestMethod.POST, RequestMethod.GET, RequestMethod.PUT })
	public ResultMap<? extends Object> addCamera(@RequestBody CameraInfo bean)
	{
		return getMessage(cameraService.addCameraInfo(bean));
	}
	
	@RequestMapping(value = URLConstant.CAMERA_UPDATE, method =
	{ RequestMethod.POST, RequestMethod.GET, RequestMethod.PUT })
	public ResultMap<? extends Object> updateCameraInfo(@RequestBody CameraInfo bean)
	{
		return getMessage(cameraService.updateById(bean));
	}

	@RequestMapping(value = URLConstant.CAMERA_ADD_MARK_LAMP, method =
	{ RequestMethod.POST, RequestMethod.GET, RequestMethod.PUT })
	public ResultMap<? extends Object> mark(@RequestBody MarkLampDto bean)
	{
		return getMessage(cameraService.addLamp(bean));
	}

	@RequestMapping(value = URLConstant.CAMERA_UPDATE_MARK_LAMP, method =
	{ RequestMethod.POST, RequestMethod.GET, RequestMethod.PUT })
	public ResultMap<? extends Object> updateCameraMarkLamp(@RequestBody MarkLampDto bean)
	{
		return getMessage(cameraService.updateMarkLamp(bean));
	}

	@RequestMapping(value = URLConstant.CAMERA_DEL_MARK_LAMP, method =
	{ RequestMethod.POST, RequestMethod.GET, RequestMethod.DELETE })
	public ResultMap<? extends Object> deleteMarkLamp(@PathVariable("id") String id)
	{
		return getMessage(cameraService.deleteMarkLamp(id));
	}

	@RequestMapping(value = URLConstant.CAMERA_GET_MARK_LAMPS, method =
	{ RequestMethod.POST, RequestMethod.GET, RequestMethod.PUT })
	public ResultMap<? extends Object> getMarkLamps(MarkLampDto bean)
	{
		return getMessage(cameraService.getMarkLamps(bean));
	}
}
