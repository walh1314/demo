package com.foxconn.lamp.controller.manager;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.foxconn.lamp.common.constant.URLConstant;
import com.foxconn.lamp.common.entity.ResultMap;
import com.foxconn.lamp.common.exception.BaseException;
import com.foxconn.lamp.common.exception.ErrorCodes;
import com.foxconn.lamp.common.exception.ShiroAuthenticationException;
import com.foxconn.lamp.controller.base.BaseController;
import com.foxconn.lamp.dto.LoginDto;
import com.foxconn.lamp.manager.service.SysUserService;
import com.mysql.jdbc.StringUtils;

@RestController
public class SystemController extends BaseController
{
	@Autowired
	private SysUserService sysUserService;

	/**
	 * login
	 * @param bean userName and password
	 * @return ResultMap<? extends Object>
	 */
	@RequestMapping(value = URLConstant.SYS_LOGIN,method={RequestMethod.POST,RequestMethod.GET})
	public ResultMap<? extends Object> login(@RequestBody LoginDto bean)
	{
		return getMessage(sysUserService.login(bean.getUserName(), bean.getPassword()));
	}
	
	@RequestMapping(value = URLConstant.SYS_LOGIN_FAIL,method={RequestMethod.POST,RequestMethod.GET})
	public ResultMap<? extends Object> loginFail(HttpServletRequest request)
	{
		ResultMap<Map<String, Object>> result = new ResultMap<>();
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");
		if(StringUtils.isNullOrEmpty(userName) && StringUtils.isNullOrEmpty(password)){
			result.setCode(ErrorCodes.NOT_LOGIN.getCode());
			result.setMsg(ErrorCodes.NOT_LOGIN.getDesc());
		} else {
			result.setCode(ErrorCodes.LOGIN_USER_OR_PASSWORD_FAIL.getCode());
			result.setMsg(ErrorCodes.LOGIN_USER_OR_PASSWORD_FAIL.getDesc());
		}
		return getMessage(result);
	}
	
	@RequestMapping(value = URLConstant.SYS_EXCPEITON_HANDLE,method={RequestMethod.POST,RequestMethod.GET})
	public ResultMap<? extends Object> handleException(Exception exception,HttpServletRequest request)
	{
		ResultMap<Map<String, Object>> result = new ResultMap<>(ErrorCodes.SYSTEM_EXCEPTION);
		if(exception instanceof BaseException){
			BaseException e = (BaseException) exception;
			result.setCode(e.getCode());
			result.setMsg(e.getMsg());
		} else if(exception instanceof ShiroAuthenticationException){
			ShiroAuthenticationException e = (ShiroAuthenticationException) exception;
			result.setCode(e.getCode());
			result.setMsg(e.getMsg());
		}
		return getMessage(result);
	}
	
	
	/**
	 * @param bean
	 * @return 
	 */
	@RequestMapping(value = URLConstant.SYS_LOGOUT,method={RequestMethod.POST})
	public ResultMap<? extends Object> logout(@RequestBody LoginDto bean)
	{
		return getMessage(sysUserService.login(bean.getUserName(), bean.getPassword()));
	}
	
	/**
	 * @param bean
	 * @return 
	 */
	@RequestMapping(value = URLConstant.SYS_KICKOUT,method={RequestMethod.POST})
	public ResultMap<? extends Object> kickout(@RequestBody LoginDto bean)
	{
		return getMessage(sysUserService.login(bean.getUserName(), bean.getPassword()));
	}
	
	/**
	 * @param bean
	 * @return
	 */
	@RequestMapping(value = URLConstant.SYS_GET_GIFCODE,method={RequestMethod.POST})
	public ResultMap<? extends Object> getGifCode(@RequestBody LoginDto bean)
	{
		return getMessage(sysUserService.login(bean.getUserName(), bean.getPassword()));
	}
	
}
