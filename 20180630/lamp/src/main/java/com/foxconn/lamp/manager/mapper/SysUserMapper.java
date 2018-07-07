package com.foxconn.lamp.manager.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.foxconn.lamp.manager.domain.SysUser;

@Mapper
public interface SysUserMapper extends BaseMapper<SysUser>
{

	int deleteById(Integer id);
	
	Integer insert(SysUser record);

	List<SysUser> selectByMap(Map<String, Object> map);

	SysUser selectByName(String name);

	SysUser selectById(Integer id);

	Integer updateById(SysUser record);
}