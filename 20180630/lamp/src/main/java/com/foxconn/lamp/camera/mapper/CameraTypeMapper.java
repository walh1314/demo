package com.foxconn.lamp.camera.mapper;

import java.util.List;
import java.util.Map;

import com.foxconn.lamp.camera.domain.CameraType;

public interface CameraTypeMapper
{

	int deleteByCode(String code);

	int deleteById(Integer id);

	int insert(CameraType record);

	List<CameraType> selectByMap(Map<String, Object> map);

	CameraType selectById(Integer id);

	int updateById(CameraType record);
}