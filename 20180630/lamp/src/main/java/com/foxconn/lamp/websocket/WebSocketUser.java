package com.foxconn.lamp.websocket;

import java.security.Principal;

/**
 * 
 * @ClassName: WebSocketUser
 * @Description: 客户端用户
 * @author liupingan
 */
public final class WebSocketUser implements Principal
{

	private final String name;

	public WebSocketUser(String name)
	{
		this.name = name;
	}

	@Override
	public String getName()
	{
		return name;
	}
}
