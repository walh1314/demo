package com.foxconn.lamp.manager.service;

import java.util.List;
import java.util.Map;

import com.foxconn.lamp.manager.domain.SysPermission;

/**
 * 
 * @author liupingan
 *
 */
public interface SysPermissionService
{
	List<SysPermission> selectByMap(Map<String, Object> map);
}
