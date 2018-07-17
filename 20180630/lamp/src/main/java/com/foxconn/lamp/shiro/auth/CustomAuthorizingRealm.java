package com.foxconn.lamp.shiro.auth;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import com.foxconn.lamp.common.constant.RedisConstant;
import com.foxconn.lamp.common.exception.BaseException;
import com.foxconn.lamp.common.exception.ErrorCodes;
import com.foxconn.lamp.common.util.EncryptUtil;
import com.foxconn.lamp.manager.domain.SysPermission;
import com.foxconn.lamp.manager.domain.SysRole;
import com.foxconn.lamp.manager.domain.SysUser;
import com.foxconn.lamp.manager.service.SysPermissionService;
import com.foxconn.lamp.manager.service.SysRoleService;
import com.foxconn.lamp.manager.service.SysUserService;

public class CustomAuthorizingRealm extends AuthorizingRealm
{
	@Autowired
	private SysUserService sysUserService;

	@Autowired
	private SysPermissionService sysPermissionService;

	@Autowired
	StringRedisTemplate stringRedisTemplate;

	@Autowired
	private SysRoleService sysRoleService;
	
	@Autowired
	private UserToken userToken;
	
	@Value("${server.servlet.session.timeout}")
	private int timeout = 30; //默认为30分钟
	// 认证.登录
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException
	{
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
		String name = token.getUsername();
		String password = String.valueOf(token.getPassword());
		// 访问一次，计数一次
		ValueOperations<String, String> opsForValue = stringRedisTemplate.opsForValue();
		opsForValue.increment(RedisConstant.SHIRO_LOGIN_COUNT + name, 1);
		// 计数大于5时，设置用户被锁定一小时
		opsForValue.set(RedisConstant.SHIRO_LOGIN_COUNT + name, "0");
		if (Integer.parseInt(opsForValue.get(RedisConstant.SHIRO_LOGIN_COUNT + name)) >= 5)
		{
			opsForValue.set(RedisConstant.SHIRO_IS_LOCK + name, "LOCK");
			stringRedisTemplate.expire(RedisConstant.SHIRO_IS_LOCK + name, 1, TimeUnit.HOURS);
		}

		if ("LOCK".equals(opsForValue.get(RedisConstant.SHIRO_IS_LOCK + name)))
		{
			throw new BaseException(ErrorCodes.LOGIN_BAN_LOFIN_TIMES,new DisabledAccountException("由于密码输入错误次数大于5次，帐号已经禁止登录！"));
		}
		Map<String, Object> map = new HashMap<String, Object>(2);
		map.put("name", name);
		// 密码进行加密处理 明文为 password+name
		String paw = password + name;

		String pawDES = EncryptUtil.encryptBasedDes(paw);

		map.put("password", pawDES);
		SysUser user = null;
		// 从数据库获取对应用户名密码的用户
		List<SysUser> userList = sysUserService.selectByMap(map);
		if (userList != null && userList.size() != 0)
		{
			user = userList.get(0);
		}
		if (null == user)
		{
			throw new BaseException(ErrorCodes.LOGIN_USER_OR_PASSWORD_FAIL, new AccountException("帐号或密码不正确！"));
		} else if ("-1".equals(user.getStatus()))
		{
			/**
			 * 如果用户的status为禁用。那么就抛出<code>DisabledAccountException</code>
			 */
			throw new BaseException(ErrorCodes.LOGIN_BAN_LOFIN, new DisabledAccountException("此帐号已经设置为禁止登录！"));
		} else
		{
			// 登录成功
			// 更新登录时间 last login time
			user.setLastLoginTime(new Date());
			sysUserService.updateById(user);
			// 清空登录计数
			opsForValue.set(RedisConstant.SHIRO_LOGIN_COUNT + name, "0");
			opsForValue.set(RedisConstant.SHIRO_IS_LOCK + name, "UNLOCK");
			
			
			//设置token，并且验证是否超时
			opsForValue.set(RedisConstant.SHIRO_USER_LOGIN_TOKEN + name.toLowerCase().trim(),userToken.createToken(name,String.valueOf(System.currentTimeMillis())), timeout,TimeUnit.MINUTES);
		}
		return new SimpleAuthenticationInfo(user, password, getName());
	}

	// 授权
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principal)
	{
		SysUser user = (SysUser) SecurityUtils.getSubject().getPrincipal();
		Integer userId = user.getId();
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		// 根据用户ID查询角色（role），放入到Authorization里。
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("uid", userId);
		List<SysRole> roleList = sysRoleService.selectByMap(map);
		Set<String> roleSet = new HashSet<String>();
		for (SysRole role : roleList)
		{
			roleSet.add(role.getType());
		}
		info.setRoles(roleSet);
		// 根据用户ID查询权限（permission），放入到Authorization里。

		List<SysPermission> permissionList = sysPermissionService.selectByMap(map);
		Set<String> permissionSet = new HashSet<String>();
		for (SysPermission Permission : permissionList)
		{
			permissionSet.add(Permission.getName());
		}

		info.setStringPermissions(permissionSet);
		return info;
	}

}