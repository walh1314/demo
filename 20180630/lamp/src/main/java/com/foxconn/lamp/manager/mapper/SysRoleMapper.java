package com.foxconn.lamp.manager.mapper;

import java.util.List;
import java.util.Map;

import com.foxconn.lamp.manager.domain.SysRole;

public interface SysRoleMapper {


	Integer deleteById(Integer id);


	Integer insert(SysRole record);

    List<SysRole> selectByMap(Map<String,Object> map);
    
    SysRole selectById(Integer id);


	Integer updateById(SysRole record);
}