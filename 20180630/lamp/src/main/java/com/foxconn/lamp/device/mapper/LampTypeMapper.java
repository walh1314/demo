package com.foxconn.lamp.device.mapper;

import java.util.List;
import java.util.Map;

import com.foxconn.lamp.device.domain.LampType;

public interface LampTypeMapper {

    int deleteById(Integer id);

    int insert(LampType record);

    List<LampType> selectByMap(Map<String,Object> map);

    LampType selectById(Integer id);

    int updateById(LampType record);
}