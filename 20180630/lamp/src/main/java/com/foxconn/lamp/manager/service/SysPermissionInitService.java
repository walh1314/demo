package com.foxconn.lamp.manager.service;

import java.util.List;

import com.foxconn.lamp.manager.domain.SysPermissionInit;

/**
 * 初始化权限服务实现类
 * 
 * @author liupingan
 */
public interface SysPermissionInitService
{
	List<SysPermissionInit> selectAll();
}
