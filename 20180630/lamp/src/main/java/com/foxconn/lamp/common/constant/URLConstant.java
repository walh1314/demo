package com.foxconn.lamp.common.constant;

public interface URLConstant
{

	static String SYS_LOGIN = "/login";
	static String SYS_LOGIN_FAIL = "/loginfail";
	
	static String SYS_EXCPEITON_HANDLE = "/exceptionHandle";
	static String SYS_LOGOUT = "/logout";
	static String SYS_KICKOUT = "/kickout";

	static String SYS_GET_GIFCODE = "/getGifCode";

	static String USER_BASE = "/user";

	static String USER_PAGE_LIST = "/pageList";
	static String USER_DETAIL = "/detail/{id}";
	static String USER_UPDATE = "/update/{id}";
	static String USER_ADD = "/add";
	static String USER_DEL = "/del/{id}";

	static String CAMERA_BASE = "/camera";

	static String CAMERA_PAGE_LIST = "/pageList";
	static String CAMERA_DETAIL = "/detail/{id}";
	static String CAMERA_UPDATE = "/update/{id}";
	static String CAMERA_ADD = "/add";
	static String CAMERA_DEL = "/del/{id}";
	
	static String CAMERA_ADD_MARK_LAMP = "/addMarkLamp";
	
	static String CAMERA_UPDATE_MARK_LAMP = "/updateMarkLamp";
	
	static String CAMERA_DEL_MARK_LAMP = "/delMarkLamp/{id}";
	
	static String CAMERA_GET_MARK_LAMPS = "/getMarkLamps";
	
	static String CAMERA_GET_MARK_IMAGE = "/getMarkImage";
	
	static String CAMERA_TYPE_BASE = "/camera/type";
	
	static String CAMERA_TYPE_LIST = "/list";
	static String CAMERA_TYPE_DETAIL = "/detail/{id}";
	static String CAMERA_TYPE_ADD = "/add";
	
	
	static String LAMP_TYPE_BASE = "/lamp/type";
	
	static String LAMP_TYPE_LIST = "/list";
	static String LAMP_TYPE_DETAIL = "/detail/{id}";
	static String LAMP_TYPE_ADD = "/add";
	
	
	static String DEVICE_BASE = "/device";

	static String DEVICE_REQUIRE_ADD_LIST = "/requireAddList";
	static String DEVICE_ADD_LIST = "/addList";
	
	
	static String LOGS_BASE = "/logs";
	static String LOGS_GET_LOG_USER = "/getLogByUser";
	
}
