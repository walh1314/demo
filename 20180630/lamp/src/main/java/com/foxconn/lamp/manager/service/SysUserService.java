package com.foxconn.lamp.manager.service;

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

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.foxconn.lamp.common.constant.RedisConstant;
import com.foxconn.lamp.common.entity.FrontPage;
import com.foxconn.lamp.common.entity.PageResult;
import com.foxconn.lamp.common.entity.ResultMap;
import com.foxconn.lamp.common.exception.ErrorCodes;
import com.foxconn.lamp.dto.UserDto;
import com.foxconn.lamp.manager.domain.SysUser;
import com.foxconn.lamp.manager.mapper.SysUserMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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

	@Autowired
	StringRedisTemplate stringRedisTemplate;

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
				List<SysUser> list = this.selectByMap(map);
				Map<String, Object> dataMap = new HashMap<>(2);
				dataMap.put("userInfo", (list == null ? null : list.get(0)));
				ValueOperations<String, String> opsForValue = stringRedisTemplate.opsForValue();
				dataMap.put("token",
						opsForValue.get(RedisConstant.SHIRO_USER_LOGIN_TOKEN + userName.toLowerCase().trim()));
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
		EntityWrapper<SysUser> wrapper = new EntityWrapper<SysUser>();
		SysUser user = new SysUser();
		user.setName(bean.getName());
		user.setEmail(bean.getEmail());
		wrapper.setEntity(user);
		
		//PageHelper.startPage(page.getCurrentPage()== null ? page.getCurrentPage():1, page.getPageSize() == null? page.getPageSize():10);
		Page<SysUser> pageList = this.selectPage(page.getPagePlus(), wrapper);
		
		// 3、获取分页查询后的数据
		//PageResult<SysUser> pageInfo = new PageResult<>(pageList.getRecords());
		PageResult<SysUser> customPage = new PageResult<SysUser>(pageList);
		//result.setData(pageInfo);
		result.setData(customPage);
		return result;
	}

}
