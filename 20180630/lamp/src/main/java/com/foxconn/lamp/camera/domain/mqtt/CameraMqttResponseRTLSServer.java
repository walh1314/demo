package com.foxconn.lamp.camera.domain.mqtt;

import com.alibaba.fastjson.annotation.JSONField;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CameraMqttResponseRTLSServer
{

	@JSONField(name = "API")
	private String api;

	@JSONField(name = "HOST")
	private String host;
}
