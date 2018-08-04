package com.foxconn.lamp.mqtt;

public interface MqttConstant
{

	static final String MQTT_RECEIVED_TOPIC = "mqtt_receivedTopic";

	static final String MQTT_CLOSE = "close";

	static final String MQTT_CAMERA_ANDON_STATE_REQUEST_INTENT = "com.foxconn.ipcam.settings";

	static final String MQTT_CAMERA_ANDON_STATE_RESPONSE_INTENT = "com.foxconn.ipcam.settings";

	static final Integer MQTT_CAMERA_ANDON_STATE_REQUEST_COMMAND_CODE = 0x79;

	static final Integer MQTT_CAMERA_ANDON_STATE_RESPONSE_COMMAND_CODE = 0x79;

	static final Integer MQTT_CAMERA_ANDON_STATE_RESPONSE_STATUS = 1;

	static final Integer MQTT_CAMERA_ANDON_STATE_REQUEST_STATUS = 0;

	static final String MQTT_CAMERA_ANDON_STATE_REQUEST_DATA_MSG_TYPE = "REQ";

	static final String MQTT_CAMERA_ANDON_STATE_RESPONSE_DATA_MSG_TYPE = "RESP";

	static final String MQTT_CAMERA_ANDON_STATE_REQUEST_DATA_MSG_ACT = "GET";
	
	static final String MQTT_CAMERA_ANDON_STATE_RESPONSE_DATA_MSG_ACT = "GET";

	static final String MQTT_CAMERA_ANDON_STATE_REQUEST_DATA_MSG_CMD = "ANDON_STATE";

	static final String MQTT_CAMERA_ANDON_STATE_RESPONSE_DATA_MSG_CMD = "ANDON_STATE";
	
	
	static final String MQTT_CAMERA_ANDON_UPDATE_REQUEST_INTENT = "com.foxconn.ipcam.settings";

	static final String MQTT_CAMERA_ANDON_UPDATE_RESPONSE_INTENT = "com.foxconn.ipcam.settings";

	static final Integer MQTT_CAMERA_ANDON_UPDATE_REQUEST_COMMAND_CODE = 0xbf;

	static final Integer MQTT_CAMERA_ANDON_UPDATE_RESPONSE_COMMAND_CODE = 0xbf;

	static final Integer MQTT_CAMERA_ANDON_UPDATE_RESPONSE_STATUS = 1;

	static final Integer MQTT_CAMERA_ANDON_UPDATE_REQUEST_STATUS = 1;

	static final String MQTT_CAMERA_ANDON_UPDATE_REQUEST_DATA_MSG_TYPE = "REQ";

	static final String MQTT_CAMERA_ANDON_UPDATE_RESPONSE_DATA_MSG_TYPE = "RESP";

	static final String MQTT_CAMERA_ANDON_UPDATE_REQUEST_DATA_MSG_ACT = "SET";
	
	static final String MQTT_CAMERA_ANDON_UPDATE_RESPONSE_DATA_MSG_ACT = "SET";

	static final String MQTT_CAMERA_ANDON_UPDATE_REQUEST_DATA_MSG_CMD = "ANDON_UPDATE";

	static final String MQTT_CAMERA_ANDON_UPDATE_RESPONSE_DATA_MSG_CMD = "ANDON_UPDATE";
	
	
	

	/*
	 * "TYPE": "RESP", "ACT": "GET", "CMD": "ANDON_STATE", "COUNTS": "5",
	 * "MAXWIDTH": "1280", "MAXHEIGHT": "720"
	 */

	/*
	 * t(String sendData){ String sendMsg =
	 * "{\"intent\":\"com.foxconn.ipcam.settings\"," +
	 * "\"sourceTopic\":\"large_image_data_tricolor_lamp_camera_revice_01\"," +
	 * "\"commandCode\":121," +
	 * "\"data\":\"{\\\"MSG\\\":{\\\"TYPE\\\":\\\"REQ\\\"," +
	 * "\\\"ACT\\\":\\\"GET\\\",\\\"CMD\\\":\\\"ANDON_STATE\\\"}}\"," +
	 * "\"status\":0}";
	 */

	// static final String mqttProperties.getInbound().getTopics()
}
