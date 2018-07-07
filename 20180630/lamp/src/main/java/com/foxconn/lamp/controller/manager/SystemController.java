package com.foxconn.lamp.controller.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.foxconn.lamp.common.constant.URLConstant;
import com.foxconn.lamp.common.entity.ResultMap;
import com.foxconn.lamp.controller.base.BaseController;
import com.foxconn.lamp.dto.LoginDto;
import com.foxconn.lamp.manager.service.SysUserService;

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
	@RequestMapping(value = URLConstant.SYS_LOGIN,method={RequestMethod.POST})
	public ResultMap<? extends Object> login(@RequestBody LoginDto bean)
	{
		return sysUserService.login(bean.getUserName(), bean.getPassword());
	}
	
	
	/**
	 * @param bean
	 * @return 
	 */
	@RequestMapping(value = URLConstant.SYS_LOGOUT,method={RequestMethod.POST})
	public ResultMap<? extends Object> logout(@RequestBody LoginDto bean)
	{
		return sysUserService.login(bean.getUserName(), bean.getPassword());
	}
	
	/**
	 * @param bean
	 * @return 
	 */
	@RequestMapping(value = URLConstant.SYS_KICKOUT,method={RequestMethod.POST})
	public ResultMap<? extends Object> kickout(@RequestBody LoginDto bean)
	{
		return sysUserService.login(bean.getUserName(), bean.getPassword());
	}
	
	/**
	 * @param bean
	 * @return
	 */
	@RequestMapping(value = URLConstant.SYS_GET_GIFCODE,method={RequestMethod.POST})
	public ResultMap<? extends Object> getGifCode(@RequestBody LoginDto bean)
	{
		return sysUserService.login(bean.getUserName(), bean.getPassword());
	}
	
}
