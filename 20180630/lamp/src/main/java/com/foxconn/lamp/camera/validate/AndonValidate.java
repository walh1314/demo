package com.foxconn.lamp.camera.validate;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.foxconn.lamp.camera.domain.mqtt.CameraMqttMessage;
import com.foxconn.lamp.camera.domain.mqtt.CameraMqttMessageDataMsg;
import com.foxconn.lamp.camera.domain.mqtt.CameraMqttResponseMessageData;
import com.foxconn.lamp.mqtt.MqttConstant;
import com.mysql.jdbc.StringUtils;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class AndonValidate
{
	public static final String SUCCESS = "true";
	public static final String FAIL = "false";

	public static final String TYPE = "type";

	public static final String ADDON_STATUS = "ADDON_STATUS";

	public static final String ADDON_UPDATE = "ANDON_UPDATE";

	public static final String VALIDATE = "validate";

	public Map<String, String> validateAndon(CameraMqttMessage cameraMqttMessage)
	{
		Map<String, String> result = new HashMap<>(1);
		result.put(VALIDATE, FAIL);
		String responseData = null;
		CameraMqttMessageDataMsg cameraMqttMessageDataMsg = null;
		CameraMqttResponseMessageData cameraMqttResponseMessageData = null;
		JSONObject josonData = null;
		if (cameraMqttMessage == null)
		{
			return result;
		}
		if (!MqttConstant.MQTT_CAMERA_ANDON_STATE_RESPONSE_INTENT.equals(cameraMqttMessage.getIntent()))
		{
			log.error("mqtt intent is error");
			return result;
		}
		responseData = cameraMqttMessage.getData();
		if (StringUtils.isNullOrEmpty(responseData))
		{
			return result;
		}

		try
		{
			cameraMqttResponseMessageData = JSONObject.parseObject(responseData, CameraMqttResponseMessageData.class);
			josonData = JSONObject.parseObject(responseData);
		} catch (Exception e)
		{
			josonData = null;
			cameraMqttResponseMessageData = null;
			log.error(e.getLocalizedMessage());
		}

		if (cameraMqttResponseMessageData == null)
		{
			return result;
		}

		cameraMqttMessageDataMsg = cameraMqttResponseMessageData.getMsg();

		if (MqttConstant.MQTT_CAMERA_ANDON_STATE_RESPONSE_DATA_MSG_CMD.equals(cameraMqttMessageDataMsg.getCmd()))
		{
			return validateAndonState(cameraMqttMessage, cameraMqttResponseMessageData, josonData);
		} else
		{
			return validateAndonUpdate(cameraMqttMessage, cameraMqttResponseMessageData, josonData);
		}

	}

	private boolean commonValidate(CameraMqttMessageDataMsg cameraMqttMessageDataMsg)
	{
		boolean result = false;
		if (cameraMqttMessageDataMsg == null
				|| (!MqttConstant.MQTT_CAMERA_ANDON_STATE_RESPONSE_DATA_MSG_TYPE
						.equals(cameraMqttMessageDataMsg.getType())
						&& !MqttConstant.MQTT_CAMERA_ANDON_STATE_RESPONSE_DATA_MSG_ACT
								.equals(cameraMqttMessageDataMsg.getAct()))
				|| (!MqttConstant.MQTT_CAMERA_ANDON_UPDATE_RESPONSE_DATA_MSG_ACT
						.equals(cameraMqttMessageDataMsg.getAct())
						&& !MqttConstant.MQTT_CAMERA_ANDON_UPDATE_RESPONSE_DATA_MSG_TYPE
								.equals(cameraMqttMessageDataMsg.getType())))
		{

			return result;
		}

		result = true;
		return result;
	}

	public Map<String, String> validateAndonState(CameraMqttMessage cameraMqttMessage,
			CameraMqttResponseMessageData cameraMqttResponseMessageData, JSONObject josonData)
	{
		Map<String, String> result = new HashMap<>(2);
		result.put(VALIDATE, FAIL);
		result.put(TYPE, ADDON_STATUS);
		CameraMqttMessageDataMsg cameraMqttMessageDataMsg = cameraMqttResponseMessageData.getMsg();
		if (!commonValidate(cameraMqttMessageDataMsg))
		{
			return result;
		}
		result.put(VALIDATE, SUCCESS);
		return result;
	}

	public Map<String, String> validateAndonUpdate(CameraMqttMessage cameraMqttMessage,
			CameraMqttResponseMessageData cameraMqttResponseMessageData, JSONObject josonData)
	{
		Map<String, String> result = new HashMap<>(2);
		result.put(VALIDATE, FAIL);
		result.put(TYPE, ADDON_UPDATE);
		CameraMqttMessageDataMsg cameraMqttMessageDataMsg = cameraMqttResponseMessageData.getMsg();
		if (!commonValidate(cameraMqttMessageDataMsg))
		{
			return result;
		}
		result.put(VALIDATE, SUCCESS);
		return result;
	}
}
