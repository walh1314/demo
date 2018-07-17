package com.foxconn.lamp.camera.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foxconn.lamp.camera.domain.CameraType;
import com.foxconn.lamp.camera.mapper.CameraTypeMapper;
import com.foxconn.lamp.camera.service.CameraTypeService;
import com.foxconn.lamp.common.entity.ResultMap;
import com.foxconn.lamp.common.exception.BaseException;
import com.foxconn.lamp.common.exception.ErrorCodes;
import com.foxconn.lamp.common.util.SystemUtil;
import com.foxconn.lamp.dto.CameraTypeDto;
import com.foxconn.lamp.manager.domain.SysUser;
import com.mysql.jdbc.StringUtils;

import lombok.extern.slf4j.Slf4j;

@Service(value = "cameraTypeService")
@Slf4j
public class CameraTypeServiceImpl implements CameraTypeService
{

	@Autowired
	private CameraTypeMapper cameraTypeMapper;
	
	@Autowired
	private SystemUtil systemUtil;

	@Override
	public ResultMap<List<CameraType>> selectListByMap(CameraTypeDto bean)
	{
		ResultMap<List<CameraType>> result = new ResultMap<>();
		Map<String, Object> map = new HashMap<>(1);
		map.put("name", bean.getName());

		List<CameraType> list = cameraTypeMapper.selectByMap(map);
		result.setData(list);
		return result;
	}

	@Override
	public List<CameraType> selectByMap(Map<String, Object> map)
	{
		List<CameraType> list = cameraTypeMapper.selectByMap(map);
		return list;
	}

	@Override
	public ResultMap<CameraType> selectDetailById(String id)
	{
		ResultMap<CameraType> result = new ResultMap<>();
		if (StringUtils.isNullOrEmpty(id))
		{
			result.setCode(ErrorCodes.CAMERA_TYPE_ID_EMPTY.getCode());
			result.setCode(ErrorCodes.CAMERA_TYPE_ID_EMPTY.getDesc());
			return result;
		}

		try
		{
			CameraType bean = cameraTypeMapper.selectById(Integer.valueOf(id));
			result.setData(bean);
			return result;
		} catch (NumberFormatException e)
		{
			result.setCode(ErrorCodes.CAMERA_TYPE_ID_ERROR.getCode());
			result.setCode(ErrorCodes.CAMERA_TYPE_ID_ERROR.getDesc());
			log.error(e.getMessage());
			return result;
		}
	}

	@Override
	public ResultMap<Map<String, Object>> updateById(CameraType record)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultMap<Map<String, Object>> addCameraType(CameraType record)
	{
		
		ResultMap<Map<String, Object>> result = new ResultMap<>();

		setCreaterAndModifier(record, true);

		Integer updateResult = cameraTypeMapper.insert(record);
		if (updateResult != 1)
		{
			throw new BaseException(ErrorCodes.CAMERA_TYPE_ADD_FAIL);
		}
		return result;
	}

	@Override
	public ResultMap<Map<String, Object>> deleteById(String id)
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	private void setCreaterAndModifier(CameraType record, boolean isSetCreate)
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
