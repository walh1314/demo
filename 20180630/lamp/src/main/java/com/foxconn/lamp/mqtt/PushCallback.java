package com.foxconn.lamp.mqtt;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import lombok.extern.slf4j.Slf4j;

/**
 * 发布消息的回调类
 * 必须实现MqttCallback的接口并实现对应的相关接口方法CallBack 类将实现 MqttCallBack。
 * 每个客户机标识都需要一个回调实例。在此示例中，构造函数传递客户机标识以另存为实例数据。 在回调中，将它用来标识已经启动了该回调的哪个实例。
 * 必须在回调类中实现三个方法：
 * 
 * public void messageArrived(MqttTopic topic, MqttMessage message)接收已经预订的发布。
 * 
 * public void connectionLost(Throwable cause)在断开连接时调用。
 * 
 * public void deliveryComplete(MqttDeliveryToken token)) 接收到已经发布的 QoS 1 或 QoS 2
 * 消息的传递令牌时调用。 由 MqttClient.connect 激活此回调。
 * 
 */
@Slf4j
public class PushCallback implements MqttCallback
{

	public void connectionLost(Throwable cause)
	{
		log.error(cause.getMessage());
	}

	public void deliveryComplete(IMqttDeliveryToken token)
	{
		//token.getMessage()
		//log.error(cause.getMessage());
		try
		{
			System.out.println(token.getMessage());
		} catch (MqttException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void messageArrived(String topic, MqttMessage message) throws Exception
	{
		// subscribe后得到的消息会执行到这里面
		System.out.println("接收消息主题 : " + topic);
		System.out.println("接收消息Qos : " + message.getQos());
		System.out.println("接收消息内容 : " + new String(message.getPayload()));
		System.out.println(message.isRetained());
		message.setRetained(true);
	}
}