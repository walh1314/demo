package com.foxconn.lamp.log.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.foxconn.lamp.log.domain.Syslog;

@Mapper
public interface OperateLogMapper  extends BaseMapper<Syslog> 
{

}
