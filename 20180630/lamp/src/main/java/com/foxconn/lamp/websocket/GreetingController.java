package com.foxconn.lamp.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.user.SimpUser;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.util.HtmlUtils;

@Controller
public class GreetingController
{

	@Autowired
	private SimpMessagingTemplate messagingTemplate;

	@Autowired
	private SimpUserRegistry userRegistry;

	@MessageMapping("/hello")
	@SendTo("/topic/greetings")
	public Greeting greeting(HelloMessage message) throws Exception
	{
		Thread.sleep(1000); // simulated delay
		System.out.println(HtmlUtils.htmlEscape(message.getName()));
		return new Greeting("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!");
	}

	@MessageMapping(value = "/templateTest")
	public void templateTest()
	{
		System.out.println("当前在线人数:" + userRegistry.getUserCount());
		int i = 1;
		for (SimpUser user : userRegistry.getUsers())
		{
			System.out.println("用户" + i++ + "---" + user);
		}
		// 发送消息给指定用户
		messagingTemplate.convertAndSendToUser("d62307d5d3d8cb6407547dc9d08b2024", "topic/d62307d5d3d8cb6407547dc9d08b2024", "1111111");
		// 发送消息给指定用户
		//messagingTemplate.convertAndSendToUser("ccccc", "topic/d62307d5d3d8cb6407547dc9d08b2024", "1111111");
	}

}
