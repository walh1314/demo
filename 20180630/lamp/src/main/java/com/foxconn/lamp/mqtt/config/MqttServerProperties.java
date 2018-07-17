package com.foxconn.lamp.mqtt.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "mqtt.server")
@PropertySource("classpath:/mqtt.yml")
public class MqttServerProperties
{

	// tcp://MQTT安装的服务器地址:MQTT定义的端口号
	private String host;
	// 定义一个主题
	private String topic;
	// 定义MQTT的ID，可以在MQTT服务配置中指定
	private String clientid;
	private String userName;
	private String passWord;

	public String getHost()
	{
		return host;
	}

	public void setHost(String host)
	{
		this.host = host;
	}

	public String getTopic()
	{
		return topic;
	}

	public void setTopic(String topic)
	{
		this.topic = topic;
	}

	public String getClientid()
	{
		return clientid;
	}

	public void setClientid(String clientid)
	{
		this.clientid = clientid;
	}

	public String getUserName()
	{
		return userName;
	}

	public void setUserName(String userName)
	{
		this.userName = userName;
	}

	public String getPassWord()
	{
		return passWord;
	}

	public void setPassWord(String passWord)
	{
		this.passWord = passWord;
	}

}
