package com.foxconn.lamp.common.exception;

public enum ErrorCodes
{
	SCUUESS("1", "Successful"), 
	FAILED("-1", "Failed"),
	LOGIN_USER_OR_PASSWORD_FAIL("login-1000-01", "login.user.or.password.error"),
	
	
	LOGIN_BAN_LOFIN("login-1000-02", "login.user.ban"),
	LOGIN_BAN_LOFIN_TIMES("login-1000-02", "login.user.ban.times"),
	
	USER_ID_EMPTY("user-1001-01", "user id is empty"),
	USER_NAME_EMPTY("user-1001-03", "user.name.empty"),
	USER_PASSWORD_EMPTY("user-1001-04", "user.password.empty"),
	USER_MOBILE_EMPTY("user-1001-05", "mobile id is empty"),
	USER_DELETE_FAIL("user-1000-01", "delete user fail"),
	USER_UPDATE_FAIL("user-1000-02", "update user fail"),
	USER_ADD_FAIL("user-1000-03", "update user fail"),
	SYSTEM_EXCEPTION("sys-1000-01", "system.exception")
	;
	private String code;
	private String desc;

	public String getCode()
	{
		return code;
	}

	public void setCode(String code)
	{
		this.code = code;
	}

	public String getDesc()
	{
		return desc;
	}

	public void setDesc(String desc)
	{
		this.desc = desc;
	}

	private ErrorCodes(String code, String desc)
	{
		this.code = code;
		this.desc = desc;
	}
}
