package com.foxconn.lamp.controller.logs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.foxconn.lamp.common.constant.URLConstant;
import com.foxconn.lamp.common.entity.ResultMap;
import com.foxconn.lamp.common.exception.BaseException;
import com.foxconn.lamp.common.exception.ErrorCodes;
import com.foxconn.lamp.controller.base.BaseController;
import com.foxconn.lamp.dto.LogDto;
import com.foxconn.lamp.thirdparty.ThirdpartyConstant;
import com.foxconn.lamp.thirdparty.am.AMService;
import com.foxconn.lamp.thirdparty.starlogs.IpCameraService;

@RestController
@RequestMapping(URLConstant.LOGS_BASE)
public class LogsController extends BaseController
{

	@Autowired
	private IpCameraService ipCameraService;

	@Autowired
	private AMService amService;

	@RequestMapping(value = URLConstant.LOGS_GET_LOG_USER, method =
	{ RequestMethod.POST, RequestMethod.GET })
	public ResultMap<? extends Object> getLogByUser(@RequestBody LogDto bean)
	{
		ResultMap<JSONArray> result = new ResultMap<>();
		JSONArray logs = null;
		Long memberCode = amService.registerUser(bean.getUserId(), bean.getMobile());
		if (memberCode != null)
		{
			JSONObject jsonObject = ipCameraService.getLogByUser(memberCode);
			if (jsonObject != null)
			{
				if (jsonObject.containsKey(ThirdpartyConstant.AM_DM_ERR_CODE))
				{
					String errorCode = jsonObject.getString(ThirdpartyConstant.AM_DM_ERR_CODE);
					if (!ThirdpartyConstant.AM_DM_SUCCESS.equals(errorCode))
					{
						throw new BaseException(ErrorCodes.AM_DM_CODE_ERROR.getCode(),
								errorCode + ":" + jsonObject.getString(ThirdpartyConstant.AM_DM_ERR_MSG));
					}

					if (jsonObject.containsKey(ThirdpartyConstant.STARTLOGS_LOGS))
					{
						logs = jsonObject.getJSONArray(ThirdpartyConstant.STARTLOGS_LOGS);
					}
				} else
				{
					throw new BaseException(ErrorCodes.AM_DM_DATA_FAIL);
				}
			}
			result.setData(logs);
		} else
		{
			result.setCode(ErrorCodes.FAILED.getCode());
			result.setMsg(ErrorCodes.FAILED.getDesc());
		}

		return result;
		// return getMessage(deviceService.getRequireAddDeviceList(bean));
	}

}
