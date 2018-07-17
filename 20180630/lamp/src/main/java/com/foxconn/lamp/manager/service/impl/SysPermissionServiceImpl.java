package com.foxconn.lamp.manager.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foxconn.lamp.manager.domain.SysPermission;
import com.foxconn.lamp.manager.mapper.SysPermissionMapper;
import com.foxconn.lamp.manager.service.SysPermissionService;

/**
 * 
 * @author liupingan
 *
 */
@Service
public class SysPermissionServiceImpl implements SysPermissionService
{

	@Autowired
	private SysPermissionMapper sysPermissionMapper;
	@Override
	public List<SysPermission> selectByMap(Map<String, Object> map)
	{
		// TODO Auto-generated method stub
		return sysPermissionMapper.selectByMap(map);
	}

}
