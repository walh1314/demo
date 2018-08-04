package com.foxconn.lamp.camera.service;

import java.util.List;
import java.util.Map;

import org.springframework.scheduling.annotation.Async;

import com.foxconn.lamp.camera.domain.CameraType;
import com.foxconn.lamp.common.entity.ResultMap;
import com.foxconn.lamp.dto.CameraTypeDto;

public interface CameraTypeService
{
	ResultMap<List<CameraType>> selectListByMap(CameraTypeDto bean);

	List<CameraType> selectByMap(Map<String, Object> map);

	ResultMap<CameraType> selectDetailById(String id);

	ResultMap<Map<String, Object>> updateById(CameraType record);

	ResultMap<Map<String, Object>> addCameraType(CameraType record);
	
	@Async
	void replaceInsertCameraTypeListAsync(List<CameraType> record);

	ResultMap<Map<String, Object>> deleteById(String id);
}
