package com.foxconn.lamp.common.advice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.foxconn.lamp.common.entity.ResultMap;
import com.foxconn.lamp.common.exception.BaseException;
import com.foxconn.lamp.common.exception.ErrorCodes;
import com.foxconn.lamp.common.exception.ShiroAuthenticationException;
import com.foxconn.lamp.controller.base.BaseMessageResource;

@ControllerAdvice
@ResponseBody
public class ExceptionHandlerAdvice
{
	@Autowired
	private BaseMessageResource baseMessageResource;

	@ExceptionHandler(Exception.class)
	//@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ResultMap<? extends Object> handleException(Exception e)
	{
		return baseMessageResource.getMessage( new ResultMap<>(ErrorCodes.FAILED));
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	//@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ResultMap<? extends Object> handleIllegalParamException(MethodArgumentNotValidException e)
	{
		List<ObjectError> errors = e.getBindingResult().getAllErrors();
		String tips = "参数不合法";
		if (errors.size() > 0)
		{
			tips = errors.get(0).getDefaultMessage();
		}
		ResultMap<? extends Object> result = new ResultMap<>(ErrorCodes.FAILED);
		result.setMsg(tips);
		return baseMessageResource.getMessage(result);
	}
	
	@ExceptionHandler(ShiroAuthenticationException.class)
	//@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ResultMap<? extends Object> handleShiroAuthenticationException(ShiroAuthenticationException e)
	{
		
		ResultMap<? extends Object> result = new ResultMap<>(e.getCode(),e.getMsg());
		return baseMessageResource.getMessage(result);
	}
	
	@ExceptionHandler(BaseException.class)
	//@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ResultMap<? extends Object> handleBaseException(BaseException e)
	{
		
		ResultMap<? extends Object> result = new ResultMap<>(e.getCode(),e.getMsg());
		return baseMessageResource.getMessage(result);
	}
}