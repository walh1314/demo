package com.foxconn.lamp.manager.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foxconn.lamp.manager.domain.SysRole;
import com.foxconn.lamp.manager.mapper.SysRoleMapper;
import com.foxconn.lamp.manager.service.SysRoleService;

@Service
public class SysRoleServiceImpl implements SysRoleService
{

	@Autowired
	private SysRoleMapper sysRoleMapper;
	
	@Override
	public List<SysRole> selectByMap(Map<String, Object> map)
	{
		// TODO Auto-generated method stub
		return sysRoleMapper.selectByMap(map);
	}

}
