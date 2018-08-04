package com.foxconn.lamp.camera.mapper;

import java.util.List;
import java.util.Map;

import com.foxconn.lamp.camera.domain.CameraInfo;
import com.foxconn.lamp.vo.MarkLampsVo;

public interface CameraInfoMapper
{

	Integer deleteById(Integer id);

	Integer insert(CameraInfo record);
	
	Integer batchInsert(List<CameraInfo> record);
	
	Integer batchReplaceInsert(List<CameraInfo> record);
	
	List<CameraInfo> selectByMap(Map<String, Object> map);

	CameraInfo selectById(Integer id);

	Integer updateById(CameraInfo record);
	
	List<MarkLampsVo> selectByDeviceId(String  deviceId);
}