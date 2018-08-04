package com.foxconn.lamp.camera.domain.mqtt;

import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CameraMqttResponseLamp
{

	@JSONField(name = "TYPE")
	private Integer type;
	
	@JSONField(name = "POINTS")
	private List<List<Integer>> points;

	@JSONField(name = "SN")
	private String serial;

	@JSONField(name = "NAME")
	private String name;

	@JSONField(name = "CUSTOM")
	private List<Integer> threshold;// 临界值
}
