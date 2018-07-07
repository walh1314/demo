package com.foxconn.lamp.common.advice;

import java.util.List;

import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import com.foxconn.lamp.common.entity.ResultMap;
import com.foxconn.lamp.common.exception.ErrorCodes;

//@ControllerAdvice
//@ResponseBody
public class ExceptionHandlerAdvice
{

	//@ExceptionHandler(Exception.class)
	public ResultMap<? extends Object> handleException(Exception e)
	{
		e.printStackTrace();
		return new ResultMap<>();
	}

	//@ExceptionHandler(MethodArgumentNotValidException.class)
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
		return result;
	}
}