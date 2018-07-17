package com.foxconn.lamp.rabbitmq.test;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Send
{

	private final static String queue_name="hello";
	
	public static void main(String[] args) throws IOException, TimeoutException
	{
		//创建一个连接
        ConnectionFactory factory = new ConnectionFactory();
        //连接本地，如果需要指定到服务，需在这里指定IP
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        //创建一个通道
        Channel channel = connection.createChannel();
        //申明通道发送消息的队列，把消息发送至消息队列‘hello’
        channel.queueDeclare(queue_name, false, false, false, null);
        String message = "Hello World!";
        //Declaring a queue is idempotent - 如果队列不存在则会创建一个队列 
        //消息内容为byte array, so可以自己随意编码
        channel.basicPublish("", queue_name, null, message.getBytes());
        System.out.println(" [x] Sent '" + message + "'");

        //消息发送完成后，关闭通道和连接
        channel.close();
        connection.close();
	}
}
