package com.foxconn.lamp.camera.service.mqtt;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.BeforeFilter;
import com.foxconn.lamp.camera.domain.mqtt.CameraMqttRequestMessageData;
import com.foxconn.lamp.camera.domain.mqtt.CameraMqttResponseLamp;

/**
 * 序列化特殊处理
 * @author liupingan
 *
 */
public class CameraMqttDataBeforeSerializer extends BeforeFilter
{

	@Override
	public void writeBefore(Object object)
	{
		if (object == null)
		{
			return;
		}
		List<CameraMqttResponseLamp> list = null;
		if (object instanceof CameraMqttRequestMessageData)
		{

			CameraMqttRequestMessageData cameraMqttRequestMessageData = (CameraMqttRequestMessageData) object;
			list = cameraMqttRequestMessageData.getLamps();
			if (list != null && list.size() > 0)
			{
				for (int i = 0; i < list.size(); i++)
				{
					writeKeyValue(String.valueOf(i),JSONObject.toJSON(list.get(i)));
				}
			}
		}
	}
}
