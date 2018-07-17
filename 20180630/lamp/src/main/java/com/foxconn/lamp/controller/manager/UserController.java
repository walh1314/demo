package com.foxconn.lamp.controller.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
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
	public ResultMap<? extends Object> getPageList(UserDto bean, FrontPage<SysUser> page)
	{
		return getMessage(sysUserService.selectPage(page, bean));
	}

	@RequestMapping(value = URLConstant.USER_DETAIL, method =
	{ RequestMethod.POST, RequestMethod.GET })
	public ResultMap<? extends Object> getDetail(@PathVariable("id") String id)
	{
		return getMessage(sysUserService.selectDetailById(id));
	}

	@RequestMapping(value = URLConstant.USER_UPDATE, method =
	{ RequestMethod.POST, RequestMethod.GET, RequestMethod.PUT })
	public ResultMap<? extends Object> updateUserInfo(@RequestBody SysUser bean)
	{
		return getMessage(sysUserService.updateById(bean));
	}

	@RequestMapping(value = URLConstant.USER_ADD, method =
	{ RequestMethod.POST, RequestMethod.GET, RequestMethod.PUT })
	public ResultMap<? extends Object> addUserInfo(@RequestBody SysUser bean)
	{
		return getMessage(sysUserService.addUserInfo(bean));
	}

	@RequestMapping(value = URLConstant.USER_DEL, method =
	{ RequestMethod.POST, RequestMethod.GET, RequestMethod.DELETE })
	public ResultMap<? extends Object> deleteById(@PathVariable("id") String id)
	{
		return getMessage(sysUserService.deleteById(id));
	}
}
