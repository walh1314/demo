package com.foxconn.lamp.thirdparty.am;

/**
 * 请求参数
 * 
 * @author liupingan
 *
 */
public class RegisterUserRequest
{

	private String userId;
	private String mobile;

	public RegisterUserRequest()
	{

	}

	public RegisterUserRequest(String userId, String mobile)
	{
		this.userId = userId;
		this.mobile = mobile;
	}

	public String getUserId()
	{
		return userId;
	}

	public void setUserId(String userId)
	{
		this.userId = userId;
	}

	public String getMobile()
	{
		return mobile;
	}

	public void setMobile(String mobile)
	{
		this.mobile = mobile;
	}

}
