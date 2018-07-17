package com.foxconn.lamp.manager.mapper;

import java.util.List;
import java.util.Map;

import com.foxconn.lamp.manager.domain.SysUserRole;

public interface SysUserRoleMapper
{

	Integer deleteById(Integer id);

	Integer insert(SysUserRole record);

	List<SysUserRole> selectByExample(Map<String, Object> map);

	SysUserRole selectById(Integer id);

	Integer updateById(SysUserRole record);
}