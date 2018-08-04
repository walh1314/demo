package com.foxconn.lamp.camera.domain.mqtt;

import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CameraMqttMessageDataMsg
{

	@JSONField(name = "TYPE")
	private String type;
	
	@JSONField(name = "ACT")
	private String act;
	
	@JSONField(name = "CMD")
	private String cmd;
	
	@JSONField(name = "COUNTS")
	private String counts;
	
	@JSONField(name = "MAXWIDTH")
	private String maxWidth;
	
	@JSONField(name = "MAXHEIGHT")
	private String maxHeight;
	
	@JSONField(name = "UPDATELIST")
	private List<String> updateList;
}
