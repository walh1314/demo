package com.foxconn.lamp.controller.device;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.toolkit.StringUtils;
import com.foxconn.lamp.camera.domain.CameraInfo;
import com.foxconn.lamp.camera.service.CameraService;
import com.foxconn.lamp.common.constant.URLConstant;
import com.foxconn.lamp.common.entity.FrontPage;
import com.foxconn.lamp.common.entity.ResultMap;
import com.foxconn.lamp.common.exception.ErrorCodes;
import com.foxconn.lamp.controller.base.BaseController;
import com.foxconn.lamp.dto.CameraInfoDto;
import com.foxconn.lamp.dto.MarkLampDto;
import com.foxconn.lamp.thirdparty.shinobi.ShinobiService;

@RestController
@RequestMapping(URLConstant.CAMERA_BASE)
public class CameraController extends BaseController
{

	@Autowired
	private CameraService cameraService;

	@Autowired
	private ShinobiService shinobiService;

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
	

	@RequestMapping(value = URLConstant.CAMERA_DEL, method =
	{ RequestMethod.POST, RequestMethod.GET, RequestMethod.PUT, RequestMethod.DELETE })
	public ResultMap<? extends Object> deleteCameraInfoById(@PathVariable("id") String id)
	{
		return getMessage(cameraService.deleteById(id));
	}

	@RequestMapping(value = URLConstant.CAMERA_ADD_MARK_LAMP, method =
	{ RequestMethod.POST, RequestMethod.GET, RequestMethod.PUT  })
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

	@RequestMapping(value = URLConstant.CAMERA_GET_MARK_IMAGE, method =
	{ RequestMethod.POST, RequestMethod.GET, RequestMethod.PUT })
	public ResultMap<? extends Object> getCameraSnapShot(MarkLampDto bean, HttpServletResponse response, HttpServletRequest request)
			throws IOException
	{
		ResultMap<String> result = new ResultMap<>();
		if (StringUtils.isEmpty(bean.getDeviceId()))
		{
			result.setCode(ErrorCodes.SHINOBI_DEVICE_ID_EMPTY.getCode());
			result.setMsg(ErrorCodes.SHINOBI_DEVICE_ID_EMPTY.getDesc());
		} else
		{
			String imageResult = shinobiService.getSnapShot(bean.getDeviceId());
			
			if (StringUtils.isNotEmpty(imageResult))
			{
				result.setData("data:image/png;base64,"+imageResult);
			} else {
				result.setCode(ErrorCodes.SHINOBI_IMAGE_EMPTY.getCode());
				result.setMsg(ErrorCodes.SHINOBI_IMAGE_EMPTY.getDesc());
			}
		}
		return getMessage(result);
	}
}
