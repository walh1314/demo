package com.foxconn.lamp.camera.domain.mqtt;

import com.alibaba.fastjson.annotation.JSONField;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CameraMqttMessage
{

	@JSONField(name = "intent",ordinal=1)
	private String intent;// 意图

	@JSONField(name = "sourceTopic",ordinal=2)
	private String sourceTopic;// 原始topic

	@JSONField(name = "commandCode",ordinal=3)
	private Integer commandCode;// 指令code

	@JSONField(name = "data",ordinal=4)
	private String data;

	@JSONField(name = "status",ordinal=5)
	private Integer status;
}
