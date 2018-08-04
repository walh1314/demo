package com.foxconn.lamp.camera.domain.mqtt;

import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CameraMqttRequestMessageData
{
	@JSONField(name = "MSG",ordinal=1)
	private CameraMqttMessageDataMsg msg;
	
	@JSONField(serialize=false,deserialize=false)
	private List<CameraMqttResponseLamp> lamps;
	
	//CameraMqttResponseLamp
/*	@JSONField(name = "0")
	private CameraMqttResponseLamp zero;
	
	@JSONField(name = "1")
	private CameraMqttResponseLamp one;
	
	@JSONField(name = "2")
	private CameraMqttResponseLamp two;
	
	@JSONField(name = "3")
	private CameraMqttResponseLamp three;
	
	@JSONField(name = "4")
	private CameraMqttResponseLamp four;*/
	
	@JSONField(name = "RTLS_SRV",ordinal=2)
	private CameraMqttResponseRTLSServer rtlsServer;
	
	@JSONField(name = "GLOBAL",ordinal=3)
	private CameraMqttResponseGlobal global;

}
