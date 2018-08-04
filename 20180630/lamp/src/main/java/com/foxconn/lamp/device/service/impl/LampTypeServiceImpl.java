package com.foxconn.lamp.device.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foxconn.lamp.common.entity.ResultMap;
import com.foxconn.lamp.common.exception.BaseException;
import com.foxconn.lamp.common.exception.ErrorCodes;
import com.foxconn.lamp.common.util.SystemUtil;
import com.foxconn.lamp.device.domain.LampType;
import com.foxconn.lamp.device.mapper.LampTypeMapper;
import com.foxconn.lamp.device.service.LampTypeService;
import com.foxconn.lamp.dto.LampTypeDto;
import com.foxconn.lamp.manager.domain.SysUser;
import com.mysql.jdbc.StringUtils;

import lombok.extern.slf4j.Slf4j;

@Service(value = "lampTypeService")
@Slf4j
public class LampTypeServiceImpl implements LampTypeService
{

	@Autowired
	private LampTypeMapper lampTypeMapper;
	
	@Autowired
	private SystemUtil systemUtil;

	@Override
	public ResultMap<List<LampType>> selectListByMap(LampTypeDto bean)
	{
		ResultMap<List<LampType>> result = new ResultMap<>();
		Map<String, Object> map = new HashMap<>(1);
		map.put("name", bean.getName());
		List<LampType> list = lampTypeMapper.selectByMap(map);
		result.setData(list);
		return result;
	}

	@Override
	public List<LampType> selectByMap(Map<String, Object> map)
	{
		List<LampType> list = lampTypeMapper.selectByMap(map);
		return list;
	}

	@Override
	public ResultMap<LampType> selectDetailById(String id)
	{
		ResultMap<LampType> result = new ResultMap<>();
		if (StringUtils.isNullOrEmpty(id))
		{
			result.setCode(ErrorCodes.LAMP_TYPE_ID_EMPTY.getCode());
			result.setCode(ErrorCodes.LAMP_TYPE_ID_EMPTY.getDesc());
			return result;
		}

		try
		{
			LampType bean = lampTypeMapper.selectById(Integer.valueOf(id));
			result.setData(bean);
			return result;
		} catch (NumberFormatException e)
		{
			result.setCode(ErrorCodes.LAMP_TYPE_ID_ERROR.getCode());
			result.setCode(ErrorCodes.LAMP_TYPE_ID_ERROR.getDesc());
			log.error(e.getMessage());
			return result;
		}
	}

	@Override
	public ResultMap<Map<String, Object>> updateById(LampType record)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultMap<Map<String, Object>> addLampType(LampType record)
	{
		
		ResultMap<Map<String, Object>> result = new ResultMap<>();

		setCreaterAndModifier(record, true);

		Integer updateResult = lampTypeMapper.insert(record);
		if (updateResult != 1)
		{
			throw new BaseException(ErrorCodes.LAMP_TYPE_ADD_FAIL);
		}
		return result;
	}

	@Override
	public ResultMap<Map<String, Object>> deleteById(String id)
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	private void setCreaterAndModifier(LampType record, boolean isSetCreate)
	{
		SysUser currentUser = systemUtil.getCurrentUser();
		if (record != null)
		{
			if (currentUser != null)
			{
				record.setModifier(currentUser.getName());
				if (isSetCreate)
				{
					record.setCreater(currentUser.getName());
					record.setCreateTime(new Date());
				}
			}
			record.setModifyTime(new Date());
		}
	}
}
