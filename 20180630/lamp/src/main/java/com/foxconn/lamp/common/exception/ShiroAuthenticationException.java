package com.foxconn.lamp.common.exception;

import org.apache.shiro.authc.AuthenticationException;

public class ShiroAuthenticationException extends AuthenticationException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 9175079213792775440L;
	private String code;
	private String msg;

	public ShiroAuthenticationException(String code, String msg, Throwable t)
	{
		super("ErrorCode: " + code + ",ErrorMsg:\r\n" + msg, t);
		this.code = code;
		this.msg = msg;
	}

	public ShiroAuthenticationException(ErrorCodes error, Throwable t)
	{
		super("ErrorCode: " + error.getCode() + ",ErrorMsg:\r\n" + error.getDesc(), t);
		this.code = error.getCode();
		this.msg = error.getDesc();
	}

	public ShiroAuthenticationException(ErrorCodes error)
	{
		super("ErrorCode: " + error.getCode() + ",ErrorMsg:\r\n" + error.getDesc());
		this.code = error.getCode();
		this.msg = error.getDesc();
	}

	public ShiroAuthenticationException(String msg, Throwable t)
	{
		super("ErrorMsg:\r\n" + msg, t);
	}

	public ShiroAuthenticationException(String msg)
	{
		super("ErrorMsg:\r\n" + msg);
	}

	public String getCode()
	{
		return code;
	}

	public void setCode(String code)
	{
		this.code = code;
	}

	public String getMsg()
	{
		return msg;
	}

	public void setMsg(String msg)
	{
		this.msg = msg;
	}
}
