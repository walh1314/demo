package com.foxconn.lamp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.foxconn.lamp.log.domain.IpCameraLog;
import com.foxconn.lamp.log.repository.IpCameraLogRepository;
import com.foxconn.lamp.mqtt.spring.MqttGateway;

@RestController
@RequestMapping("/test")
public class TestController
{
	@Autowired
	private MqttGateway mqttGateway;
	
	@Autowired
	private IpCameraLogRepository repository;

	@RequestMapping("/sendMqtt.do")
	public String sendMqtt(String sendData)
	{
		String sendMsg = "{\"intent\":\"com.foxconn.ipcam.settings\","
				+ "\"sourceTopic\":\"large_image_data_tricolor_lamp_camera_revice_01\"," + "\"commandCode\":121,"
				+ "\"data\":\"{\\\"MSG\\\":{\\\"TYPE\\\":\\\"REQ\\\","
				+ "\\\"ACT\\\":\\\"GET\\\",\\\"CMD\\\":\\\"ANDON_STATE\\\"}}\"," + "\"status\":0}";
		// mqttGateway.sendToMqtt(sendMsg,"1f8dc50d57eb9bcea327158cc5a69f59");
		//mqttGateway.sendToMqtt(sendMsg,"d62307d5d3d8cb6407547dc9d08b2024");

		mqttGateway.sendToMqtt("d62307d5d3d8cb6407547dc9d08b2024", 2, sendMsg);
		return "OK";
	}

	@RequestMapping("/sendUpdateMqtt.do")
    public String sendUpdateMqtt(String  sendData){
    	String sendMsg = "{ "+
    		    "\"intent\": \"com.foxconn.ipcam.settings\","+
    		    "\"sourceTopic\": \"large_image_data_tricolor_lamp_camera_revice_01\","+
    		    "\"commandCode\": 191,"+
    		    "\"data\": \"{\\\"MSG\\\":{\\\"TYPE\\\":\\\"REQ\\\",\\\"ACT\\\":\\\"SET\\\",\\\"CMD\\\":\\\"ANDON_UPDATE\\\",\\\"UPDATELIST\\\":[ \\\"1\\\", \\\"2\\\", \\\"3\\\", \\\"4\\\"]},"+
    			//"\\\"0\\\":{\\\"TYPE\\\":1,\\\"POINTS\\\":[[30, 415], [35, 406], [52, 415], [45, 424]],\\\"SN\\\":\\\"gggg\\\",\\\"NAME\\\":\\\"gggg001\\\",\\\"CUSTOM\\\":[120, 112, 170]},"+
    			"\\\"1\\\":{\\\"TYPE\\\":1,\\\"POINTS\\\":[[369, 58], [376, 47], [393, 72], [385, 81]],\\\"SN\\\":\\\"eeeaaaaee\\\",\\\"NAME\\\":\\\"eeee000\\\",\\\"CUSTOM\\\":[180, 181, 120]},"+
    			"\\\"2\\\":{\\\"TYPE\\\":0,\\\"POINTS\\\":[[121, 121], [456, 121], [456, 456], [123, 576]],\\\"SN\\\":\\\"sn_test_009991\\\",\\\"NAME\\\":\\\"sn_test_009991\\\",\\\"CUSTOM\\\":[133, 151, 180]},"+
    			"\\\"3\\\":{\\\"TYPE\\\":0,\\\"POINTS\\\":[[111, 111], [542, 112], [345, 456], [132, 678]],\\\"SN\\\":\\\"sn_test_01dd001\\\",\\\"NAME\\\":\\\"sn_test_01dd001\\\",\\\"CUSTOM\\\":[140, 115, 180]},"+
    			"\\\"4\\\":{\\\"TYPE\\\":0,\\\"POINTS\\\":[[222, 444], [283, 444], [67, 234], [123, 24]],\\\"SN\\\":\\\"sn_test_01dd00\\\",\\\"NAME\\\":\\\"sn_test_01dd00\\\",\\\"CUSTOM\\\":[120, 35, 80]},"+
    			//"\\\"RTLS_SRV\\\":{\\\"HOST\\\":\\\"http://10.167.195.186:8080\\\",\\\"API\\\":\\\"/Staff_management/Home/Malfunction/sendWarnMsg\\\"},"+
    			"\\\"GLOBAL\\\":{\\\"MODE\\\":0}}\","+
    		    "\"status\": 1"+
    		"}";
    	// mqttGateway.sendToMqtt(sendMsg,"1f8dc50d57eb9bcea327158cc5a69f59");
    	IpCameraLog ipCameraLog = new IpCameraLog();
    	ipCameraLog.setAction("1111111");
    	ipCameraLog.setAppVersion("45tgfdfg");
    	repository.save(ipCameraLog);
    	// mqttGateway.sendToMqtt("1f8dc50d57eb9bcea327158cc5a69f59",2,sendMsg);
         return "OK";
    }
}
