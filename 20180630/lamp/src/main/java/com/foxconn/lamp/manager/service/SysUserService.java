package com.foxconn.lamp.manager.service;

import java.util.List;
import java.util.Map;

import com.foxconn.lamp.common.entity.FrontPage;
import com.foxconn.lamp.common.entity.PageResult;
import com.foxconn.lamp.common.entity.ResultMap;
import com.foxconn.lamp.dto.UserDto;
import com.foxconn.lamp.manager.domain.SysUser;

/**
 * 用户业务实现层
 */
public interface SysUserService
{

	SysUser findByName(String userName);

	ResultMap<Map<String, Object>> login(String userName, String password);

	ResultMap<PageResult<SysUser>> selectPage(FrontPage<SysUser> page, UserDto bean);
	
	List<SysUser> selectByMap(Map<String,Object> map);
	
	ResultMap<SysUser> selectDetailById(String id);
	
	ResultMap<Map<String,Object>> updateById(SysUser record);
	
	ResultMap<Map<String,Object>> addUserInfo(SysUser record);
	
	ResultMap<Map<String,Object>> deleteById(String id);
}
