package com.foxconn.lamp.camera.mapper;

import java.util.List;
import java.util.Map;

import com.foxconn.lamp.camera.domain.CameraInfo;
import com.foxconn.lamp.vo.MarkLampsVo;

public interface CameraInfoMapper
{

	int deleteById(Integer id);

	int insert(CameraInfo record);

	List<CameraInfo> selectByMap(Map<String, Object> map);

	CameraInfo selectById(Integer id);

	int updateById(CameraInfo record);
	
	List<MarkLampsVo> selectByDeviceId(String  deviceId);
}