package com.foxconn.lamp.manager.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.foxconn.lamp.manager.domain.SysPermissionInit;
import com.foxconn.lamp.manager.mapper.SysPermissionInitMapper;

/**
 * 初始化权限服务实现类
 * 
 * @author liupingan
 */
@Service
public class SysPermissionInitService extends ServiceImpl<SysPermissionInitMapper, SysPermissionInit>
{
	public List<SysPermissionInit> selectAll()
	{
		return baseMapper.selectAll();
	}

}
