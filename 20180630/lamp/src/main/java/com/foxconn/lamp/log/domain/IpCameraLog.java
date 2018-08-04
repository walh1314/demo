package com.foxconn.lamp.log.domain;

import java.io.Serializable;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "ip_camera_log")
public class IpCameraLog implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String memberCode;
	private String sourceDeviceId;
	private String targetDeviceId;
	private String reportTime;
	private String deviceInfo;
	private String appVersion;
	private String model;
	private String action;
	private String actionTime;
}
