package com.foxconn.lamp.shiro.auth;

import org.springframework.stereotype.Component;

import com.foxconn.lamp.common.util.EncryptUtil;

@Component
public class SimpleUserToken implements UserToken
{
	@Override
	public String createToken(String... args)
	{
		// TODO Auto-generated method stub
		if (args != null && args.length >= 2)
		{
			String userName = args[0];
			String dateTime = args[1];
			String source = dateTime + userName;
			return EncryptUtil.encryptUserTokenDes(source);
		}
		return null;
	}
}
