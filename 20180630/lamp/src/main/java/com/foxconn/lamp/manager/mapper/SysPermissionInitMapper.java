package com.foxconn.lamp.manager.mapper;

import java.util.List;
import java.util.Map;

import com.foxconn.lamp.manager.domain.SysPermissionInit;

public interface SysPermissionInitMapper
{

	Integer deleteById(Integer id);

	Integer insert(SysPermissionInit record);

	List<SysPermissionInit> selectByMap(Map<String, Object> map);

	SysPermissionInit selectById(Integer id);

	Integer updateById(SysPermissionInit record);

	List<SysPermissionInit> selectAll();
}