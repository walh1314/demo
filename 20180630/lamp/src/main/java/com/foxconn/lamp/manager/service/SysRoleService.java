package com.foxconn.lamp.manager.service;

import java.util.List;
import java.util.Map;

import com.foxconn.lamp.manager.domain.SysRole;

public interface SysRoleService
{

	List<SysRole> selectByMap(Map<String,Object> map);
}
