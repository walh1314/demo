package com.foxconn.lamp.device.mapper;

import java.util.List;

import com.foxconn.lamp.device.domain.CameraLampReal;

public interface CameraLampRealMapper
{

	int deleteById(Integer id);

	int insert(CameraLampReal record);

	List<CameraLampReal> selectByExample(CameraLampReal record);

	CameraLampReal selectById(Integer id);

	int updateById(CameraLampReal record);

}