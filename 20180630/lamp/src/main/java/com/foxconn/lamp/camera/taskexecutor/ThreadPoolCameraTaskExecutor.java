package com.foxconn.lamp.camera.taskexecutor;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

@Component("cameraTaskExecutor")
public class ThreadPoolCameraTaskExecutor extends ThreadPoolTaskExecutor
{

	@Autowired
	private CameraTaskExecutorConfig cameraTaskExecutorConfig;
	/**
	 * 
	 */
	private static final long serialVersionUID = -4226379536213261422L;

	public ThreadPoolCameraTaskExecutor(){
		super();
	}
	
	/**
	 * 初始化方法注解
	 */
	@PostConstruct
	public void init(){
		this.setCorePoolSize(cameraTaskExecutorConfig.getCorePoolSize());
		this.setKeepAliveSeconds(cameraTaskExecutorConfig.getKeepAliveSeconds());
		this.setMaxPoolSize(cameraTaskExecutorConfig.getMaxPoolSize());
		this.setQueueCapacity(cameraTaskExecutorConfig.getQueueCapacity());
		this.initialize();
	}
}
