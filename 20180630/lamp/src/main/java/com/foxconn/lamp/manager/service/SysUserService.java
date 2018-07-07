package com.foxconn.lamp.manager.service;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.foxconn.lamp.common.entity.ResultMap;
import com.foxconn.lamp.common.exception.ErrorCodes;
import com.foxconn.lamp.manager.domain.SysUser;
import com.foxconn.lamp.manager.mapper.SysUserMapper;
import com.mysql.jdbc.StringUtils;

/**
 * 用户业务实现层
 *
 * Created by bysocket on 07/02/2017.
 */
@Service
public class SysUserService extends ServiceImpl<SysUserMapper, SysUser>
{
	@Autowired
	private SysUserMapper userMapper; // 主数据源
	
	public SysUser findByName(String userName)
	{
		SysUser user = userMapper.selectByName(userName);
		return user;
	}

	public ResultMap<? extends Object> login(String userName, String password)
	{

		ResultMap<? extends Object> result = new ResultMap<>();

		if (StringUtils.isNullOrEmpty(userName))
		{
			result.setCode(ErrorCodes.USER_NAME_EMPTY.getCode());
			result.setCode(ErrorCodes.USER_NAME_EMPTY.getDesc());
		} else if (StringUtils.isNullOrEmpty(password))
		{
			result.setCode(ErrorCodes.USER_PASSWORD_EMPTY.getCode());
			result.setCode(ErrorCodes.USER_PASSWORD_EMPTY.getDesc());
		} else
		{
			// 包装用户名和密码以备后边其他类使用
			UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(userName, password);
			Subject subject = SecurityUtils.getSubject();
			try
			{
				subject.login(usernamePasswordToken);
				return result;
			} catch (UnknownAccountException e)
			{
				result.setCode(ErrorCodes.LOGIN_USER_OR_PASSWORD_FAIL.getCode());
				result.setCode(ErrorCodes.LOGIN_USER_OR_PASSWORD_FAIL.getDesc());
				return result;
			} catch (IncorrectCredentialsException e)
			{
				result.setCode(ErrorCodes.LOGIN_USER_OR_PASSWORD_FAIL.getCode());
				result.setCode(ErrorCodes.LOGIN_USER_OR_PASSWORD_FAIL.getDesc());
				return result;
			} catch (Exception e)
			{
				result.setCode(ErrorCodes.SYSTEM_EXCEPTION.getCode());
				result.setCode(ErrorCodes.SYSTEM_EXCEPTION.getDesc());
				result.setArgs(new String[]
				{ "link.system.admin" });
				return result;
			}
		}
		return result;
	}
}
