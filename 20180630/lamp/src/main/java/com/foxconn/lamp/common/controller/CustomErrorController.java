package com.foxconn.lamp.common.controller;

import org.springframework.web.bind.annotation.RestController;

import com.foxconn.lamp.common.entity.ResultMap;
import com.foxconn.lamp.common.exception.ErrorCodes;

@RestController
//@RequestMapping("${server.error.path:${error.path:/error}}")
public class CustomErrorController //implements ErrorController
{

	public String getErrorPath()
	{
		return "/error";
	}

	//@RequestMapping
	//@ResponseBody
	public ResultMap<? extends Object> doHandleError()
	{
		return new ResultMap<>(ErrorCodes.FAILED);
	}
}