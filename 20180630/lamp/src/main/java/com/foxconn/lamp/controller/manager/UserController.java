package com.foxconn.lamp.controller.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.foxconn.lamp.common.constant.URLConstant;
import com.foxconn.lamp.common.entity.FrontPage;
import com.foxconn.lamp.common.entity.ResultMap;
import com.foxconn.lamp.controller.base.BaseController;
import com.foxconn.lamp.dto.UserDto;
import com.foxconn.lamp.manager.domain.SysUser;
import com.foxconn.lamp.manager.service.SysUserService;

@RestController
@RequestMapping(URLConstant.USER_BASE)
public class UserController extends BaseController
{

	@Autowired
	private SysUserService sysUserService;

	/**
	 * get user list
	 * 
	 * @param bean
	 *            userName and password
	 * @return ResultMap<? extends Object>
	 */
	@RequestMapping(value = URLConstant.USER_PAGE_LIST, method =
	{ RequestMethod.POST, RequestMethod.GET })
	public ResultMap<? extends Object> login( UserDto bean,FrontPage<SysUser> page)
	{
		return sysUserService.selectPage(page, bean);
	}

}
