package com.foxconn.lamp.mqtt.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(MqttProperties.class)
public class MqttPropertiesConfiguration
{

}