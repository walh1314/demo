package com.foxconn.lamp.log.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.foxconn.lamp.log.domain.Syslog;

@Mapper
public interface OperateLogMapper 
{

	int insert(Syslog bean);
}
