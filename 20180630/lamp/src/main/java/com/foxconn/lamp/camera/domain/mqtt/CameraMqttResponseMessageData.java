package com.foxconn.lamp.camera.domain.mqtt;

import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CameraMqttResponseMessageData
{

	@JSONField(name = "MSG")
	private CameraMqttMessageDataMsg msg;
	
	@JSONField(serialize=false,deserialize=false)
	private List<CameraMqttResponseLamp> lamps;
	
	@JSONField(name = "RTLS_SRV")
	private CameraMqttResponseRTLSServer rtlsServer;
	
	@JSONField(name = "GLOBAL")
	private CameraMqttResponseGlobal global;
}
