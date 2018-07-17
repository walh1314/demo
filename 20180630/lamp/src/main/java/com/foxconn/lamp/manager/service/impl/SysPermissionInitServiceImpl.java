package com.foxconn.lamp.manager.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foxconn.lamp.manager.domain.SysPermissionInit;
import com.foxconn.lamp.manager.mapper.SysPermissionInitMapper;
import com.foxconn.lamp.manager.service.SysPermissionInitService;

/**
 * 初始化权限服务实现类
 * 
 * @author liupingan
 */
@Service(value="sysPermissionInitService")
public class SysPermissionInitServiceImpl implements SysPermissionInitService
{
	@Autowired
	private SysPermissionInitMapper sysPermissionInitMapper;

	public List<SysPermissionInit> selectAll()
	{
		return sysPermissionInitMapper.selectAll();
	}

}
