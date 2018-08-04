package com.foxconn.lamp.camera.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class CameraWebSocket
{
	private String type;
	
	private String code;
	
	private String msg;
}
