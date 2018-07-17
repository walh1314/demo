package com.foxconn.lamp.camera.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.foxconn.lamp.camera.domain.CameraInfo;
import com.foxconn.lamp.camera.mapper.CameraInfoMapper;
import com.foxconn.lamp.camera.service.CameraService;
import com.foxconn.lamp.common.entity.FrontPage;
import com.foxconn.lamp.common.entity.PageResult;
import com.foxconn.lamp.common.entity.ResultMap;
import com.foxconn.lamp.common.exception.BaseException;
import com.foxconn.lamp.common.exception.ErrorCodes;
import com.foxconn.lamp.common.util.SystemUtil;
import com.foxconn.lamp.device.domain.CameraLampReal;
import com.foxconn.lamp.device.domain.LampInfo;
import com.foxconn.lamp.device.mapper.CameraLampRealMapper;
import com.foxconn.lamp.device.mapper.LampInfoMapper;
import com.foxconn.lamp.dto.CameraInfoDto;
import com.foxconn.lamp.dto.MarkLampDto;
import com.foxconn.lamp.vo.MarkLampsVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mysql.jdbc.StringUtils;

import lombok.extern.slf4j.Slf4j;

@Service(value = "cameraService")
@Slf4j
public class CameraServiceImpl implements CameraService
{

	@Autowired
	private CameraInfoMapper cameraInfoMapper;

	@Autowired
	private LampInfoMapper lampInfoMapper;

	@Autowired
	private CameraLampRealMapper cameraLampRealMapper;

	@Autowired
	SystemUtil systemUtil;

	@Override
	public ResultMap<CameraInfo> findByDeviceId(String deviceId)
	{

		ResultMap<CameraInfo> result = new ResultMap<>();
		if (StringUtils.isNullOrEmpty(deviceId))
		{
			result.setCode(ErrorCodes.CAMERA_DEVICE_ID_EMPTY.getCode());
			result.setMsg(ErrorCodes.CAMERA_DEVICE_ID_EMPTY.getDesc());
			return result;
		}
		Map<String, Object> selectMap = new HashMap<>(1);
		selectMap.put("deviceId", deviceId);
		List<CameraInfo> cameraInfoList = cameraInfoMapper.selectByMap(selectMap);
		if (cameraInfoList != null && cameraInfoList.size() > 0)
		{
			result.setData(cameraInfoList.get(0));
		}
		return result;
	}

	@Override
	public ResultMap<PageResult<CameraInfo>> selectPage(FrontPage<CameraInfo> page, CameraInfoDto bean)
	{
		ResultMap<PageResult<CameraInfo>> result = new ResultMap<>();
		Map<String, Object> map = new HashMap<>(1);
		map.put("name", bean.getName());
		PageHelper.startPage(page.getCurrentPage(), page.getPageSize());

		List<CameraInfo> pageList = cameraInfoMapper.selectByMap(map);
		PageInfo<CameraInfo> pageInfo = new PageInfo<>(pageList);
		PageResult<CameraInfo> pageResult = new PageResult<>(pageInfo);
		result.setData(pageResult);
		return result;
	}

	@Override
	public List<CameraInfo> selectByMap(Map<String, Object> map)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultMap<CameraInfo> selectDetailById(String id)
	{
		ResultMap<CameraInfo> result = new ResultMap<>();
		if (StringUtils.isNullOrEmpty(id))
		{
			result.setCode(ErrorCodes.CAMERA_ID_EMPTY.getCode());
			result.setMsg(ErrorCodes.CAMERA_ID_EMPTY.getDesc());
			return result;
		}

		try
		{
			CameraInfo bean = cameraInfoMapper.selectById(Integer.valueOf(id));
			result.setData(bean);
			return result;
		} catch (NumberFormatException e)
		{
			result.setCode(ErrorCodes.CAMERA_ID_ERROR.getCode());
			result.setMsg(ErrorCodes.CAMERA_ID_ERROR.getDesc());
			log.error(e.getMessage());
			return result;
		}
	}

	@Override
	public ResultMap<Map<String, Object>> updateById(CameraInfo record)
	{
		ResultMap<Map<String, Object>> result = new ResultMap<>();
		if (StringUtils.isNullOrEmpty(record.getName()))
		{
			result.setCode(ErrorCodes.CAMERA_NAME_EMPTY.getCode());
			result.setMsg(ErrorCodes.CAMERA_NAME_EMPTY.getDesc());
			return result;
		}
		
		if (record.getId() == null)
		{
			result.setCode(ErrorCodes.CAMERA_ID_EMPTY.getCode());
			result.setMsg(ErrorCodes.CAMERA_ID_EMPTY.getDesc());
			return result;
		}

		systemUtil.setCreaterAndModifier(record, false);

		Integer updateResult = cameraInfoMapper.updateById(record);
		if (updateResult != 1)
		{
			throw new BaseException(ErrorCodes.CAMERA_UPDATE_FAIL);
		}
		return result;
	}

	@Override
	public ResultMap<Map<String, Object>> addCameraInfo(CameraInfo record)
	{
		ResultMap<Map<String, Object>> result = new ResultMap<>();
		if (StringUtils.isNullOrEmpty(record.getName()))
		{
			result.setCode(ErrorCodes.CAMERA_NAME_EMPTY.getCode());
			result.setMsg(ErrorCodes.CAMERA_NAME_EMPTY.getDesc());
			return result;
		}

		systemUtil.setCreaterAndModifier(record, true);

		Integer updateResult = cameraInfoMapper.insert(record);
		if (updateResult != 1)
		{
			throw new BaseException(ErrorCodes.CAMERA_ADD_FAIL);
		}
		return result;
	}

	@Override
	public ResultMap<Map<String, Object>> deleteById(String id)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultMap<Map<String, Object>> addLamp(MarkLampDto record)
	{
		ResultMap<Map<String, Object>> result = new ResultMap<>();

		LampInfo lampInfo = markLampDtoToLampInfo(record,true);
		// 插入灯信息
		int count = lampInfoMapper.insert(lampInfo);
		if (count != 1)
		{
			throw new BaseException(ErrorCodes.CAMERA_MARK_FAIL);
		}
		CameraLampReal cameraLampReal = markLampDtoToCameraLampReal(record,true);
		count = cameraLampRealMapper.insert(cameraLampReal);
		Map<String,Object> map = new HashMap<>(2);
		map.put("cameraLampReal", cameraLampReal);
		map.put("lampInfo", lampInfo);
		result.setData(map);
		if (count != 1)
		{
			throw new BaseException(ErrorCodes.CAMERA_MARK_FAIL);
		}
		return result;
	}

	private LampInfo markLampDtoToLampInfo(MarkLampDto bean,boolean isSetCreate)
	{
		LampInfo lampInfo = new LampInfo();
		systemUtil.setCreaterAndModifier(lampInfo, isSetCreate);
		lampInfo.setMaintainer(bean.getMaintainer());
		lampInfo.setSerial(bean.getSerail());
		lampInfo.setStatus(1L);
		lampInfo.setType(bean.getType());

		lampInfo.setName(bean.getSerail());
		return lampInfo;
	}

	private CameraLampReal markLampDtoToCameraLampReal(MarkLampDto bean,boolean isSetCreate)
	{
		CameraLampReal result = new CameraLampReal();
		systemUtil.setCreaterAndModifier(result, true);
		result.setDeviceId(bean.getDeviceId());
		result.setLampSerial(bean.getSerail());
		result.setPoints((bean.getPoints() != null && bean.getPoints().size() > 0)
				? JSONObject.toJSONString(bean.getPoints()) : "");
		result.setStatus(1L);
		return result;
	}

	@Override
	public ResultMap<List<MarkLampsVo>> getMarkLamps(MarkLampDto record)
	{
		ResultMap<List<MarkLampsVo>> result = new ResultMap<>();
		List<MarkLampsVo> list = cameraInfoMapper.selectByDeviceId(record.getDeviceId());
		result.setData(list);
		return result;
	}

	@Override
	public ResultMap<Map<String, Object>> deleteMarkLamp(String id)
	{
		ResultMap<Map<String, Object>> result = new ResultMap<>();
		if (StringUtils.isNullOrEmpty(id))
		{
			result.setCode(ErrorCodes.CAMERA_MARK_ID_EMPTY.getCode());
			result.setMsg(ErrorCodes.CAMERA_MARK_ID_EMPTY.getDesc());
			return result;
		}

		try
		{
			Integer deleteResult = cameraLampRealMapper.deleteById(Integer.valueOf(id));
			if (deleteResult != 1)
			{
				throw new BaseException(ErrorCodes.CAMERA_MARK_DELETE_FAIL);
			}
			return result;
		} catch (NumberFormatException e)
		{
			result.setCode(ErrorCodes.CAMERA_MARK_ID_ERROR.getCode());
			result.setMsg(ErrorCodes.CAMERA_MARK_ID_ERROR.getDesc());
			log.error(e.getMessage());
			return result;
		}
	}

	@Override
	public ResultMap<Map<String, Object>> updateMarkLamp(MarkLampDto record)
	{
		ResultMap<Map<String, Object>> result = new ResultMap<>();

		LampInfo LampInfo = markLampDtoToLampInfo(record,false);
		// 插入灯信息
		int count = lampInfoMapper.updateBySerail(LampInfo);
		if (count != 1)
		{
			throw new BaseException(ErrorCodes.CAMERA_UPDATE_MARK_FAIL);
		}
		CameraLampReal cameraLampReal = markLampDtoToCameraLampReal(record,false);
		try
		{
			cameraLampReal.setId(Integer.valueOf(record.getId()));
		} catch (NumberFormatException e)
		{
			result.setCode(ErrorCodes.CAMERA_MARK_ID_ERROR.getCode());
			result.setMsg(ErrorCodes.CAMERA_MARK_ID_ERROR.getDesc());
			log.error(e.getMessage());
			return result;
		}
		count = cameraLampRealMapper.updateById(cameraLampReal);
		if (count != 1)
		{
			throw new BaseException(ErrorCodes.CAMERA_UPDATE_MARK_FAIL);
		}
		return result;
	}
}
