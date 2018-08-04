package com.foxconn.lamp.camera.taskexecutor;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class CameraMqttAsyncTaskService
{

	@Async
    public void executeAysncTask(Integer i){
        System.out.println("执行异步任务："+i);
    }
}


