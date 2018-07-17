package com.foxconn.lamp.device.service;

import java.util.List;
import java.util.Map;

import com.foxconn.lamp.common.entity.ResultMap;
import com.foxconn.lamp.device.domain.LampType;
import com.foxconn.lamp.dto.LampTypeDto;

public interface LampTypeService
{
	ResultMap<List<LampType>> selectListByMap(LampTypeDto bean);

	List<LampType> selectByMap(Map<String, Object> map);

	ResultMap<LampType> selectDetailById(String id);

	ResultMap<Map<String, Object>> updateById(LampType record);

	ResultMap<Map<String, Object>> addLampType(LampType record);

	ResultMap<Map<String, Object>> deleteById(String id);
}
