package com.foxconn.lamp.camera.taskexecutor;

import org.springframework.scheduling.annotation.AsyncConfigurer;

/*@Configuration
@EnableAsync*/
public class CameraMqttTaskExecutorConfig implements AsyncConfigurer
{/*
	@Override
	public Executor getAsyncExecutor()
	{
		ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
		taskExecutor.setCorePoolSize(5);
		taskExecutor.setMaxPoolSize(10);
		taskExecutor.setQueueCapacity(25);
		taskExecutor.initialize();
		return taskExecutor;
	}

	@Override
	public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler()
	{
		return null;
	}
*/
}
