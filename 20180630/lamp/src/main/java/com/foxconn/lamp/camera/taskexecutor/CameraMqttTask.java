package com.foxconn.lamp.camera.taskexecutor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foxconn.lamp.camera.service.CameraService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CameraMqttTask implements Runnable
{

	private String data;

	@Autowired
	private CameraService cameraService;

	@Override
	public void run()
	{
		boolean result = cameraService.addonStateLamps(data);
		if (!result)
		{
			log.error("CameraMqttTask recive add state error");
		}
	}

	public String getData()
	{
		return data;
	}

	public void setData(String data)
	{
		this.data = data;
	}

	public CameraService getCameraService()
	{
		return cameraService;
	}

	public void setCameraService(CameraService cameraService)
	{
		this.cameraService = cameraService;
	}
}
