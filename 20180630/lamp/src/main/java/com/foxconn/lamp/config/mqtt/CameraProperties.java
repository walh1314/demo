package com.foxconn.lamp.config.mqtt;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "com.mqtt.camera")
public class CameraProperties
{
	private String topic;
	private Integer qos = 2;
	public String getTopic()
	{
		return topic;
	}
	public void setTopic(String topic)
	{
		this.topic = topic;
	}
	public Integer getQos()
	{
		return qos;
	}
	public void setQos(Integer qos)
	{
		this.qos = qos;
	}
}
