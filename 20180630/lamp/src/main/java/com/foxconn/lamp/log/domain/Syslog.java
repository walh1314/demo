package com.foxconn.lamp.log.domain;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * 操作日志实体类
 */
public class Syslog implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7059816372831006709L;
	@Setter
	@Getter
	private String optId;
	@Setter
	@Getter
	private String loginId;
	@Setter
	@Getter
	private String loginName;
	@Setter
	@Getter
	private String ipAddress;
	@Setter
	@Getter
	private String methodName;
	@Setter
	@Getter
	private String methodRemark;
	@Setter
	@Getter
	private String operatingcontent;
	@Setter
	@Getter
	private String optDate;
	// 模糊条件
	@Setter
	@Getter
	private String serchCondition;

}