package com.foxconn.lamp.manager.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import com.foxconn.lamp.common.constant.RedisConstant;
import com.foxconn.lamp.common.entity.FrontPage;
import com.foxconn.lamp.common.entity.PageResult;
import com.foxconn.lamp.common.entity.ResultMap;
import com.foxconn.lamp.common.exception.BaseException;
import com.foxconn.lamp.common.exception.ErrorCodes;
import com.foxconn.lamp.common.util.SystemUtil;
import com.foxconn.lamp.dto.UserDto;
import com.foxconn.lamp.manager.domain.SysUser;
import com.foxconn.lamp.manager.mapper.SysUserMapper;
import com.foxconn.lamp.manager.service.SysUserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mysql.jdbc.StringUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * 用户业务实现层
 *
 * Created by bysocket on 07/02/2017.
 */
@Service
@Slf4j
public class SysUserServiceImpl implements SysUserService
{
	@Autowired
	private SysUserMapper userMapper; // 主数据源

	@Autowired
	StringRedisTemplate stringRedisTemplate;

	@Autowired
	SystemUtil systemUtil;

	public SysUser findByName(String userName)
	{
		SysUser user = userMapper.selectByName(userName);
		return user;
	}

	public ResultMap<Map<String, Object>> login(String userName, String password)
	{

		ResultMap<Map<String, Object>> result = new ResultMap<>();

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
				Map<String, Object> map = new HashMap<String, Object>(1);
				map.put("name", userName);
				List<SysUser> list = userMapper.selectByMap(map);
				Map<String, Object> dataMap = new HashMap<>(2);
				dataMap.put("userInfo", (list == null ? null : list.get(0)));
				ValueOperations<String, String> opsForValue = stringRedisTemplate.opsForValue();
				dataMap.put("token",
						opsForValue.get(RedisConstant.SHIRO_USER_LOGIN_TOKEN + userName.toLowerCase().trim()));
				systemUtil.setCurrentUser((list == null ? null : list.get(0)));
				// opsForValue.set(RedisConstant.SHIRO_USER_INFO +
				// userName.toLowerCase().trim(), JSONObject.toJSONString((list
				// == null ? null : list.get(0))));
				result.setData(dataMap);
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

	public ResultMap<PageResult<SysUser>> selectPage(FrontPage<SysUser> page, UserDto bean)
	{

		ResultMap<PageResult<SysUser>> result = new ResultMap<>();
		Map<String, Object> map = new HashMap<>();
		map.put("name", bean.getName());
		map.put("email", bean.getEmail());

		PageHelper.startPage(page.getCurrentPage(), page.getPageSize());

		List<SysUser> pageList = userMapper.selectByMap(map);
		PageInfo<SysUser> pageInfo = new PageInfo<>(pageList);
		PageResult<SysUser> pageResult = new PageResult<>(pageInfo);

		result.setData(pageResult);
		return result;
	}

	@Override
	public List<SysUser> selectByMap(Map<String, Object> map)
	{
		return userMapper.selectByMap(map);
	}

	@Override
	public ResultMap<Map<String, Object>> updateById(SysUser record)
	{
		ResultMap<Map<String, Object>> result = new ResultMap<>();
		setCreaterAndModifier(record, false);
		Integer updateResult = userMapper.updateById(record);
		if (updateResult != 1)
		{
			throw new BaseException(ErrorCodes.USER_UPDATE_FAIL);
		}
		return result;
	}

	@Override
	public ResultMap<SysUser> selectDetailById(String id)
	{
		ResultMap<SysUser> result = new ResultMap<>();
		if (StringUtils.isNullOrEmpty(id))
		{
			result.setCode(ErrorCodes.USER_ID_EMPTY.getCode());
			result.setMsg(ErrorCodes.USER_ID_EMPTY.getDesc());
			return result;
		}

		try
		{
			SysUser user = userMapper.selectById(Integer.valueOf(id));
			result.setData(user);
			return result;
		} catch (NumberFormatException e)
		{
			result.setCode(ErrorCodes.USER_ID_ERROR.getCode());
			result.setMsg(ErrorCodes.USER_ID_ERROR.getDesc());
			log.error(e.getMessage());
			return result;
		}
	}

	@Override
	public ResultMap<Map<String, Object>> addUserInfo(SysUser record)
	{
		ResultMap<Map<String, Object>> result = new ResultMap<>();
		if (StringUtils.isNullOrEmpty(record.getName()))
		{
			result.setCode(ErrorCodes.USER_NAME_EMPTY.getCode());
			result.setMsg(ErrorCodes.USER_NAME_EMPTY.getDesc());
			return result;
		}

		if (StringUtils.isNullOrEmpty(record.getEmail()))
		{
			result.setCode(ErrorCodes.USER_EMAIL_EMPTY.getCode());
			result.setMsg(ErrorCodes.USER_EMAIL_EMPTY.getDesc());
			return result;
		}

		if (StringUtils.isNullOrEmpty(record.getMobile()))
		{
			result.setCode(ErrorCodes.USER_MOBILE_EMPTY.getCode());
			result.setMsg(ErrorCodes.USER_MOBILE_EMPTY.getDesc());
			return result;
		}

		setCreaterAndModifier(record, true);

		Integer updateResult = userMapper.insert(record);
		if (updateResult != 1)
		{
			throw new BaseException(ErrorCodes.USER_ADD_FAIL);
		}
		return result;
	}

	private void setCreaterAndModifier(SysUser record, boolean isSetCreate)
	{
		SysUser currentUser = systemUtil.getCurrentUser();
		if (record != null)
		{
			if (currentUser != null)
			{
				record.setModifier(currentUser.getName());
				if (isSetCreate)
				{
					record.setCreater(currentUser.getName());
					record.setCreateTime(new Date());
				}

			}
			record.setModifyTime(new Date());
		}
	}

	@Override
	public ResultMap<Map<String, Object>> deleteById(String id)
	{
		ResultMap<Map<String, Object>> result = new ResultMap<>();
		if (StringUtils.isNullOrEmpty(id))
		{
			result.setCode(ErrorCodes.USER_ID_EMPTY.getCode());
			result.setMsg(ErrorCodes.USER_ID_EMPTY.getDesc());
			return result;
		}

		try
		{
			Integer deleteResult = userMapper.deleteById(Integer.valueOf(id));
			if (deleteResult != 1)
			{
				throw new BaseException(ErrorCodes.USER_DELETE_FAIL);
			}
			return result;
		} catch (NumberFormatException e)
		{
			result.setCode(ErrorCodes.USER_ID_ERROR.getCode());
			result.setMsg(ErrorCodes.USER_ID_ERROR.getDesc());
			log.error(e.getMessage());
			return result;
		}
	}

}
