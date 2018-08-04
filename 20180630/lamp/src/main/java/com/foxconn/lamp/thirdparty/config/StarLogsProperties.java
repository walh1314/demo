package com.foxconn.lamp.thirdparty.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix="thirdparty.starlogs")
@Configuration
public class StarLogsProperties
{
	private String url;
	public String getUrl()
	{
		return url;
	}
	public void setUrl(String url)
	{
		this.url = url;
	}
}
