package com.foxconn.lamp.common.exception;

public enum ErrorCodes
{
	SCUUESS("1", "Successful"), 
	FAILED("-1", "Failed"),
	LOGIN_USER_OR_PASSWORD_FAIL("login-1000-01", "login.user.or.password.error"),
	
	
	LOGIN_BAN_LOFIN("login-1000-02", "login.user.ban"),
	LOGIN_BAN_LOFIN_TIMES("login-1000-03", "login.user.ban.times"),
	
	NOT_LOGIN("login-0000-01", "not.login"),
	
	
	USER_ID_EMPTY("user-1001-01", "user.id.empty"),
	USER_ID_ERROR("user-1001-02", "user.id.error"),
	USER_NAME_EMPTY("user-1001-03", "user.name.empty"),
	USER_PASSWORD_EMPTY("user-1001-04", "user.password.empty"),
	USER_MOBILE_EMPTY("user-1001-05", "mobile.empty"),
	USER_EMAIL_EMPTY("user-1001-06", "email.empty"),
	USER_DELETE_FAIL("user-1000-01", "delete.user.fail"),
	USER_UPDATE_FAIL("user-1000-02", "update.user.fail"),
	USER_ADD_FAIL("user-1000-03", "add.user.fail"),
	
	CAMERA_DEVICE_ID_EMPTY("camera-1000-01", "deviceId.empty"),
	
	CAMERA_ID_EMPTY("camera-1000-02", "camera.id.empty"),
	CAMERA_ID_ERROR("camera-1000-03", "camera.id.error"),
	
	CAMERA_TYPE_ID_EMPTY("camera-1000-04", "camera.type.id.empty"),
	CAMERA_TYPE_ID_ERROR("camera-1000-05", "camera.type.id.error"),
	
	
	
	CAMERA_TYPE_ADD_FAIL("camera-1000-06", "add.camera.type.fail"),
	CAMERA_NAME_EMPTY("camera-1000-07", "camera.name.empty"),
	
	CAMERA_ADD_FAIL("camera-1000-08", "add.camera.fail"),
	
	
	CAMERA_MARK_FAIL("camera-1000-09", "camera.mark.fail"),
	
	CAMERA_MARK_ID_EMPTY("camera-1000-10", "camera.mark.id.empty"),
	CAMERA_MARK_ID_ERROR("camera-1000-11", "camera.mark.id.error"),
	CAMERA_MARK_DELETE_FAIL("camera-1000-12", "delete.camera.mark.error"),
	
	CAMERA_UPDATE_MARK_FAIL("camera-1000-13", "camera.update.mark.fail"),
	
	CAMERA_UPDATE_FAIL("camera-1000-14", "update.camera.fail"),
	CAMERA_DELETE_FAIL("camera-1000-15", "delete.camera.fail"),
	
	LAMP_TYPE_ID_EMPTY("lamp-1000-01", "lamp.type.id.empty"),
	LAMP_TYPE_ID_ERROR("lamp-1000-02", "lamp.type.id.error"),
	
	
	LAMP_TYPE_ADD_FAIL("lamp-1000-03", "add.camera.type.fail"),
	
	
	
	AM_DM_EMPTY("thirdparty-1000-01", "thirdparty.am.dm.empty"),
	
	AM_DM_DATA_FAIL("thirdparty-1000-02", "thirdparty.am.dm.data.fail"),
	
	AM_DM_CODE_ERROR("thirdparty-1000-03", "thirdparty.am.dm.code.error"),
	
	
	SHINOBI_EMPTY("thirdparty-2000-01", "thirdparty.shinobi.empty"),
	SHINOBI_DEVICE_ID_EMPTY("thirdparty-2000-02", "thirdparty.shinobi.device.id.empty"),
	SHINOBI_IMAGE_EMPTY("thirdparty-2000-03", "thirdparty.image.empty"),
	
	STARLOGS_EMPTY("thirdparty-3000-01", "thirdparty.starlogs.empty"),
	
	SYSTEM_EXCEPTION("sys-1000-01", "system.exception")
	;
	private String code;
	private String desc;

	public String getCode()
	{
		return code;
	}

	public void setCode(String code)
	{
		this.code = code;
	}

	public String getDesc()
	{
		return desc;
	}

	public void setDesc(String desc)
	{
		this.desc = desc;
	}

	private ErrorCodes(String code, String desc)
	{
		this.code = code;
		this.desc = desc;
	}
}
