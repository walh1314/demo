package com.foxconn.lamp.manager.mapper;

import java.util.List;
import java.util.Map;

import com.foxconn.lamp.manager.domain.SysPermission;

public interface SysPermissionMapper
{

	Integer deleteById(Integer id);

	Integer insert(SysPermission record);

	List<SysPermission> selectByMap(Map<String, Object> map);

	SysPermission selectById(Integer id);

	Integer updateById(SysPermission record);
}