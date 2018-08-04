package com.foxconn.lamp.camera.service.mqtt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.toolkit.StringUtils;
import com.foxconn.lamp.camera.domain.mqtt.CameraMqttMessage;
import com.foxconn.lamp.camera.domain.mqtt.CameraMqttMessageDataMsg;
import com.foxconn.lamp.camera.domain.mqtt.CameraMqttRequestMessageData;
import com.foxconn.lamp.camera.domain.mqtt.CameraMqttResponseGlobal;
import com.foxconn.lamp.camera.domain.mqtt.CameraMqttResponseLamp;
import com.foxconn.lamp.camera.domain.mqtt.CameraMqttResponseRTLSServer;
import com.foxconn.lamp.config.mqtt.CameraProperties;
import com.foxconn.lamp.device.domain.LampInfo;
import com.foxconn.lamp.mqtt.MqttConstant;
import com.foxconn.lamp.mqtt.spring.MqttGateway;

@Service
public class CameraProducerService
{

	// 调用mqtt协议
	private MqttGateway mqttGateway;

	@Autowired
	private CameraProperties cameraProperties;

	private CameraMqttMessage cameraMqttMessage;

	private CameraMqttMessageDataMsg cameraMqttMessageDataMsg;

	private CameraMqttRequestMessageData cameraMqttRequestMessageData;

	public void addonUpdate(String topic, LampInfo lampInfo)
	{

	}

	public void addonUpdateDeleteSN(String topic, String deleteList)
	{
		// cameraMqttMessage.setData(data);
	}

	public void addonUpdate(String topic, List<LampInfo> updateList)
	{
		// 组装cameraMqttRequestMessageData
		CameraMqttDataBeforeSerializer cameraMqttDataBeforeSerializer = new CameraMqttDataBeforeSerializer();
		List<CameraMqttResponseLamp> lamps = null;

		List<String> updateLampListString = null;
		if (updateList != null && updateList.size() > 0)
		{
			lamps = new ArrayList<>(updateList.size());
			updateLampListString = new ArrayList<>(updateList.size());
			for (int i = 0; i < updateList.size(); i++)
			{
				lamps.add(lampInfoToCameraMqttResponseLamp(updateList.get(i)));
				updateLampListString.add(String.valueOf(i));
			}
		}
		updateLampListString.add("RTLS_SRV");
		cameraMqttMessageDataMsg.setUpdateList(updateLampListString);
		
		cameraMqttRequestMessageData.setMsg(cameraMqttMessageDataMsg);
		cameraMqttRequestMessageData.setLamps(lamps);
		
		CameraMqttResponseRTLSServer rtlsServer = new CameraMqttResponseRTLSServer();
		rtlsServer.setApi("/Staff_management/Home/Malfunction/sendWarnMsg");
		rtlsServer.setHost("http://10.167.195.186:8080");
		cameraMqttRequestMessageData.setRtlsServer(rtlsServer);
		CameraMqttResponseGlobal cameraMqttResponseGlobal = new CameraMqttResponseGlobal();
		cameraMqttResponseGlobal.setMode(0);
		cameraMqttRequestMessageData.setGlobal(cameraMqttResponseGlobal);
		
		//"\\\"RTLS_SRV\\\":{\\\"HOST\\\":\\\"http://10.167.195.186:8080\\\",\\\"API\\\":\\\"/Staff_management/Home/Malfunction/sendWarnMsg\\\"},\\\"GLOBAL\\\":{\\\"MODE\\\":0}}\","+
		cameraMqttMessage.setData(JSONObject.toJSONString(cameraMqttRequestMessageData, cameraMqttDataBeforeSerializer));
		
		mqttGateway.sendToMqtt(topic, JSONObject.toJSONString(cameraMqttMessage));
	}

	public void addonUpdate(String topic, List<LampInfo> updateList, List<LampInfo> deleteList)
	{

	}

	public void addonUpdate(String topic, List<LampInfo> updateList, String[] deleteList)
	{

	}

	private CameraMqttResponseLamp lampInfoToCameraMqttResponseLamp(LampInfo bean)
	{
		CameraMqttResponseLamp result = new CameraMqttResponseLamp();
		result.setName(bean.getName());
		result.setSerial(bean.getSerial());
		result.setType(Integer.valueOf(bean.getType()));

		String[] threshold = bean.getThreshold() == null ? null : bean.getThreshold().split(",");
		result.setThreshold(threshold == null ? Arrays.asList(new Integer[]{190,224,222}) : Arrays.asList(stringToInteger(threshold)));
		List<List<Integer>> points = null;
		if (bean.getPoints() != null && StringUtils.isNotEmpty(bean.getPoints()))
		{
			JSONArray jsonArray = JSONObject.parseArray(bean.getPoints());
			points = new ArrayList<>(jsonArray.size());
			List<Integer> point = null;
			JSONObject jsonObject = null;
			for (int i = 0; i < jsonArray.size(); i++)
			{
				point = new ArrayList<>(2);
				jsonObject = jsonArray.getJSONObject(i);

				point.add(Integer.valueOf(jsonObject.getString("x")));
				point.add(Integer.valueOf(jsonObject.getString("y")));

				points.add(point);
			}
			result.setPoints(points);
		}
		return result;
	}

	public Integer[] stringToInteger(String[] arrs)
	{
		if (arrs == null)
		{
			return null;
		}
		Integer[] ints = new Integer[arrs.length];
		for (int i = 0; i < arrs.length; i++)
		{
			ints[i] = Integer.parseInt(arrs[i]);
		}
		return ints;
	}

	/**
	 * 默认初始化
	 */
	@PostConstruct
	public void init()
	{
		cameraMqttMessage = new CameraMqttMessage();
		cameraMqttMessage.setIntent(MqttConstant.MQTT_CAMERA_ANDON_STATE_REQUEST_INTENT);
		cameraMqttMessage.setSourceTopic(cameraProperties.getTopic());
		cameraMqttMessage.setStatus(MqttConstant.MQTT_CAMERA_ANDON_UPDATE_REQUEST_STATUS);
		cameraMqttMessage.setCommandCode(MqttConstant.MQTT_CAMERA_ANDON_UPDATE_REQUEST_COMMAND_CODE);

		cameraMqttMessageDataMsg = new CameraMqttMessageDataMsg();
		cameraMqttMessageDataMsg.setAct(MqttConstant.MQTT_CAMERA_ANDON_UPDATE_REQUEST_DATA_MSG_ACT);
		cameraMqttMessageDataMsg.setCmd(MqttConstant.MQTT_CAMERA_ANDON_UPDATE_REQUEST_DATA_MSG_CMD);
		cameraMqttMessageDataMsg.setType(MqttConstant.MQTT_CAMERA_ANDON_UPDATE_REQUEST_DATA_MSG_TYPE);

		cameraMqttRequestMessageData = new CameraMqttRequestMessageData();
	}

	public static void main(String[] args)
	{
		CameraMqttDataBeforeSerializer cameraMqttDataBeforeSerializer = new CameraMqttDataBeforeSerializer();

		CameraMqttMessage cameraMqttMessage = new CameraMqttMessage();
		cameraMqttMessage.setIntent(MqttConstant.MQTT_CAMERA_ANDON_STATE_REQUEST_INTENT);
		cameraMqttMessage.setStatus(MqttConstant.MQTT_CAMERA_ANDON_UPDATE_REQUEST_STATUS);
		cameraMqttMessage.setCommandCode(MqttConstant.MQTT_CAMERA_ANDON_UPDATE_REQUEST_COMMAND_CODE);
		System.out.println(JSONObject.toJSONString(cameraMqttMessage, cameraMqttDataBeforeSerializer));
	}
}
