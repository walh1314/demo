package com.foxconn.lamp.common.constant;

public interface CommonConstant
{

	static final String SYSTEM_ACCOUNT = "system";
	
	//等待更新,如果有请求mqtt消息成功,则等待更新
	static final Integer LAMP_INFO_UPDATE_STATUS_WAIT = 1;
	
	//更新成功，默认为成功
	static final Integer LAMP_INFO_UPDATE_STATUS_COMPLETE = 0;
	
	
	
	static final Integer LAMP_INFO_STATUS_EFFECTIVE = 1;
	
	//更新成功，默认为成功
	static final Integer LAMP_INFO_STATUS_INVALID = 0;
	
}
