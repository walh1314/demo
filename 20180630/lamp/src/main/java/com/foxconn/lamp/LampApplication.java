package com.foxconn.lamp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.filter.DelegatingFilterProxy;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;

/**
 * Spring Boot 应用启动类
 *
 * Created by bysocket on 16/4/26.
 */
// Spring Boot 应用的标识
@SpringBootApplication
@EnableAutoConfiguration
public class LampApplication
{

	public static void main(String[] args)
	{
		// 程序启动入口
		// 启动嵌入式的 Tomcat 并初始化 Spring 环境及其各 Spring 组件
		SpringApplication.run(LampApplication.class, args);
	}

	@Bean
	public HttpMessageConverters fastJsonHttpMessageConverters()
	{
		// 1、需要先定义一个converter 转换器
		FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
		// 2、添加fastJson 的配置信息，比如：是否要格式化返回的json数据
		FastJsonConfig fastJsonConfig = new FastJsonConfig();
		fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);
		// 3、在convert 中添加配置信息
		fastConverter.setFastJsonConfig(fastJsonConfig);
		// 4、将convert 添加到converters当中
		HttpMessageConverter<?> converter = fastConverter;
		return new HttpMessageConverters(converter);
	}

	@Bean
	public FilterRegistrationBean<DelegatingFilterProxy> delegatingFilterProxy()
	{
		FilterRegistrationBean<DelegatingFilterProxy> filterRegistrationBean = new FilterRegistrationBean<>();
		DelegatingFilterProxy proxy = new DelegatingFilterProxy();
		proxy.setTargetFilterLifecycle(true);
		proxy.setTargetBeanName("shiroFilter");
		filterRegistrationBean.setFilter(proxy);
		return filterRegistrationBean;
	}

//	@Bean
//	public MessageProducer inbound()
//	{
//		MqttPahoMessageDrivenChannelAdapter adapter = new MqttPahoMessageDrivenChannelAdapter(config.getClientId(),
//				mqttClientFactory());
//		adapter.setCompletionTimeout(5000);
//		adapter.setConverter(new DefaultPahoMessageConverter());
//		adapter.setOutputChannel(mqttInputChannel());
//		for (String s : config.getTopics())
//		{
//			adapter.addTopic(s, config.getQos());
//		}
//		return adapter;
//	}
//
//	@Bean
//	public MqttPahoClientFactory mqttClientFactory()
//	{
//		DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
//
//		MqttConnectOptions options = new MqttConnectOptions();
//		factory.setConnectionOptions(options);
//		/*
//		 * factory.setServerURIs("tcp://localhost:1883");
//		 * factory.setUserName("guest"); factory.setPassword("guest");
//		 */
//		return factory;
//	}
//
//	@Bean
//	public MessageChannel mqttInputChannel()
//	{
//		return new DirectChannel();
//	}
//
//	@Bean
//	@ServiceActivator(inputChannel = "mqttInputChannel")
//	public MessageHandler handler()
//	{
//		return new MessageHandler()
//		{
//
//			@Override
//			public void handleMessage(Message<?> message) throws MessagingException
//			{
//				XRemoteDevice.getInstance().receive(message.getHeaders().get("mqtt_topic"), message.getPayload());
//
//			}
//		};
//	}
//
//	@Bean
//	@ServiceActivator(inputChannel = "mqttOutboundChannel")
//	public MessageHandler mqttOutbound()
//	{
//		MqttPahoMessageHandler messageHandler = new MqttPahoMessageHandler(config.getClientId(), mqttClientFactory());
//		messageHandler.setAsync(true);
//		messageHandler.setDefaultTopic("testTopic");
//		return messageHandler;
//	}
//
//	@Bean
//	public MessageChannel mqttOutboundChannel()
//	{
//		return new DirectChannel();
//	}
}
