package com.foxconn.lamp.manager.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.foxconn.lamp.manager.domain.SysPermissionInit;

@Mapper
public interface SysPermissionInitMapper extends BaseMapper<SysPermissionInit> {

	List<SysPermissionInit> selectAll();

}