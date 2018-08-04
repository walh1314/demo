package com.foxconn.lamp.thirdparty;

public interface ThirdpartyConstant
{

	static String AM_BASE = "/api/am";

	static String AM_REGISTER_USER = "/api/providers/fii/registerUser";

	static String AM_GET_USER_DATA = AM_BASE + "/getUserData";

	static String AM_GET_AVAILABLE_DEVICE = AM_BASE + "/device/getAvailableDevice";

	static String DM_BASE = "/api/dm";

	static String DM_GET_DEVICE_INFO = DM_BASE + "/getDeviceInfo";

	// static String AM_GET_USER_DATA = AM_BASE + "/getUserData";

	static String AM_DM_ERR_CODE = "errCode";
	static String AM_DM_ERR_MSG = "errMsg";
	static String AM_DM_ERR_DETAIL = "errDetail";

	static String AM_DM_USER_PROFILE = "userProfile";

	static String AM_DM_MEMBER_CODE = "memberCode";

	static String AM_DM_AVAILABLE_DEVICEINFO = "deviceInfo";

	static String AM_DM_INFO_DEVICEINFO = "deviceInfo";

	static String AM_DM_SUCCESS = "00";

	static Integer AM_DM_DEVICE_TYPE_CAMERA = 1008;

	static String SHINOBI_SNAPSHOT = "/snapShot";

	static Integer AM_DM_DEVICE_STATUS_EXISTS = 1;
	static Integer AM_DM_DEVICE_STATUS_NOT_EXISTS = 0;
	
	static String STARTLOGS_BASE = "/api/logs";

	static String STARTLOGS_IPCAM_GET_LOG_BY_USER = "/ipcam/getLogByUser";
	
	static String STARTLOGS_LOGS = "logs";
	
}
