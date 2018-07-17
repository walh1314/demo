package com.foxconn.lamp.manager.mapper;

import java.util.List;
import java.util.Map;

import com.foxconn.lamp.manager.domain.SysRolePermission;

public interface SysRolePermissionMapper
{

	Integer deleteById(Integer id);

	Integer insert(SysRolePermission record);

	List<SysRolePermission> selectByExample(Map<String, Object> map);

	SysRolePermission selectById(Integer id);

	Integer updateById(SysRolePermission record);
}