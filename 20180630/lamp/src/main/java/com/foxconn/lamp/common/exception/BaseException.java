package com.foxconn.lamp.common.exception;

/**
 * 
 * @author liupingan
 *
 */
public class BaseException extends RuntimeException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7882913178538047286L;
	private String code;
	private String msg;

	public BaseException(String code, String msg, Throwable t)
	{
		super("ErrorCode: " + code + ",ErrorMsg:\r\n" + msg, t);
	}

	public BaseException(ErrorCodes error, Throwable t)
	{
		super("ErrorCode: " + error.getCode() + ",ErrorMsg:\r\n" + error.getDesc(), t);
	}

	public BaseException(ErrorCodes error)
	{
		super("ErrorCode: " + error.getCode() + ",ErrorMsg:\r\n" + error.getDesc());
	}

	public BaseException(String msg, Throwable t)
	{
		super("ErrorMsg:\r\n" + msg, t);
	}

	public BaseException(String msg)
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
