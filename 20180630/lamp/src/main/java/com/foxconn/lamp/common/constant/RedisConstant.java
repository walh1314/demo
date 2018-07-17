package com.foxconn.lamp.common.constant;

public interface RedisConstant
{

	// 用户登录次数计数 redisKey 前缀
	String SHIRO_LOGIN_COUNT = "SHIRO_LOGIN_COUNT_";

	// 用户登录是否被锁定 一小时 redisKey 前缀
	String SHIRO_IS_LOCK = "SHIRO_IS_LOCK_";

	String SHIRO_USER_LOGIN_TOKEN = "SHIRO_USER_LOGIN_TOKEN_";
	
	String SHIRO_USER_INFO = "SHIRO_USER_INFO_";

}
