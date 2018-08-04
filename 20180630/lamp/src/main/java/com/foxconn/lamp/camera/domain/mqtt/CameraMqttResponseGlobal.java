package com.foxconn.lamp.camera.domain.mqtt;

import com.alibaba.fastjson.annotation.JSONField;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CameraMqttResponseGlobal
{

	@JSONField(name = "MODE")
	private Integer mode;
}
