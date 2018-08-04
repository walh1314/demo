package com.foxconn.lamp.thirdparty.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix="thirdparty.shinobi")
@Configuration
public class ShinobiProperties
{
	private String url;
	private String headerKe;
	private String headerAuth;
	public String getUrl()
	{
		return url;
	}
	public void setUrl(String url)
	{
		this.url = url;
	}
	public String getHeaderKe()
	{
		return headerKe;
	}
	public void setHeaderKe(String headerKe)
	{
		this.headerKe = headerKe;
	}
	public String getHeaderAuth()
	{
		return headerAuth;
	}
	public void setHeaderAuth(String headerAuth)
	{
		this.headerAuth = headerAuth;
	}
}
