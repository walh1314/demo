package com.foxconn.lamp.camera.service;

import java.util.List;
import java.util.Map;

import com.foxconn.lamp.camera.domain.CameraInfo;
import com.foxconn.lamp.common.entity.FrontPage;
import com.foxconn.lamp.common.entity.PageResult;
import com.foxconn.lamp.common.entity.ResultMap;
import com.foxconn.lamp.dto.CameraInfoDto;
import com.foxconn.lamp.dto.MarkLampDto;
import com.foxconn.lamp.vo.MarkLampsVo;

public interface CameraService
{
	ResultMap<CameraInfo> findByDeviceId(String deviceId);

	ResultMap<PageResult<CameraInfo>> selectPage(FrontPage<CameraInfo> page, CameraInfoDto bean);

	List<CameraInfo> selectByMap(Map<String, Object> map);

	ResultMap<CameraInfo> selectDetailById(String id);

	ResultMap<Map<String, Object>> updateById(CameraInfo record);

	ResultMap<Map<String, Object>> addCameraInfo(CameraInfo record);
	
	ResultMap<Map<String, Object>> addLamp(MarkLampDto record);
	
	ResultMap<Map<String, Object>> updateMarkLamp(MarkLampDto record);
	
	ResultMap<Map<String, Object>> deleteMarkLamp(String id);

	ResultMap<Map<String, Object>> deleteById(String id);
	
	ResultMap<List<MarkLampsVo>> getMarkLamps(MarkLampDto record);
}
