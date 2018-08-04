package com.foxconn.lamp.camera.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.foxconn.lamp.camera.domain.CameraInfo;
import com.foxconn.lamp.camera.domain.CameraWebSocket;
import com.foxconn.lamp.camera.domain.mqtt.CameraMqttMessage;
import com.foxconn.lamp.camera.domain.mqtt.CameraMqttMessageDataMsg;
import com.foxconn.lamp.camera.domain.mqtt.CameraMqttResponseLamp;
import com.foxconn.lamp.camera.domain.mqtt.CameraMqttResponseMessageData;
import com.foxconn.lamp.camera.mapper.CameraInfoMapper;
import com.foxconn.lamp.camera.service.CameraService;
import com.foxconn.lamp.camera.service.mqtt.CameraProducerService;
import com.foxconn.lamp.camera.validate.AndonValidate;
import com.foxconn.lamp.common.constant.CommonConstant;
import com.foxconn.lamp.common.entity.FrontPage;
import com.foxconn.lamp.common.entity.PageResult;
import com.foxconn.lamp.common.entity.ResultMap;
import com.foxconn.lamp.common.exception.BaseException;
import com.foxconn.lamp.common.exception.ErrorCodes;
import com.foxconn.lamp.common.util.SystemUtil;
import com.foxconn.lamp.device.domain.LampInfo;
import com.foxconn.lamp.device.mapper.LampInfoMapper;
import com.foxconn.lamp.dto.CameraInfoDto;
import com.foxconn.lamp.dto.MarkLampDto;
import com.foxconn.lamp.dto.PointDto;
import com.foxconn.lamp.vo.MarkLampsVo;
import com.foxconn.lamp.vo.PointVo;
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
	private SystemUtil systemUtil;

	@Autowired
	private AndonValidate andonValidate;

	@Autowired
	private CameraProducerService cameraProducerService;

	@Autowired
	private SimpMessagingTemplate messagingTemplate;

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
		return cameraInfoMapper.selectByMap(map);
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
		ResultMap<Map<String, Object>> result = new ResultMap<>();

		if (StringUtils.isNullOrEmpty(id))
		{
			result.setCode(ErrorCodes.CAMERA_ID_EMPTY.getCode());
			result.setMsg(ErrorCodes.CAMERA_ID_EMPTY.getDesc());
			return result;
		}
		// 插入灯信息
		int count = cameraInfoMapper.deleteById(Integer.valueOf(id));
		if (count != 1)
		{
			throw new BaseException(ErrorCodes.CAMERA_DELETE_FAIL);
		}
		return result;
	}

	@Override
	public ResultMap<Map<String, Object>> addLamp(MarkLampDto record)
	{
		ResultMap<Map<String, Object>> result = new ResultMap<>();

		LampInfo lampInfo = markLampDtoToLampInfo(record, true, true);
		// 插入灯信息
		int count = lampInfoMapper.insert(lampInfo);
		if (count != 1)
		{
			throw new BaseException(ErrorCodes.CAMERA_MARK_FAIL);
		}
		Map<String, Object> map = new HashMap<>(1);
		map.put("lampInfo", lampInfo);
		
		result.setData(map);
		return result;
	}

	private LampInfo markLampDtoToLampInfo(MarkLampDto bean, boolean isSetCreate)
	{
		return markLampDtoToLampInfo(bean, isSetCreate, false);
	}

	private LampInfo markLampDtoToLampInfo(MarkLampDto bean, boolean isSetCreate, boolean isSwitch)
	{
		LampInfo lampInfo = new LampInfo();
		systemUtil.setCreaterAndModifier(lampInfo, isSetCreate);
		lampInfo.setMaintainer(bean.getMaintainer());
		lampInfo.setSerial(bean.getSerail());
		lampInfo.setStatus(1L);
		lampInfo.setType(bean.getType());

		lampInfo.setDeviceId(bean.getDeviceId());
		lampInfo.setPoints((bean.getPoints() != null && bean.getPoints().size() > 0)
				? JSONObject.toJSONString(isSwitch ? swtichPoint(bean.getPoints(), bean.getScale()) : bean.getPoints())
				: "");
		lampInfo.setThreshold((bean.getThreshold() != null && bean.getThreshold().size() > 0)
				? JSONObject.toJSONString(bean.getThreshold())
				: "");
		lampInfo.setName(bean.getSerail());
		return lampInfo;
	}

	/**
	 * 转换数据
	 * 
	 * @param list
	 * @param scale
	 * @return
	 */
	private List<PointDto> swtichPoint(List<PointDto> list, double scale)
	{
		if (list == null || list.size() == 0)
			return null;
		List<PointDto> result = new ArrayList<>(list.size());
		PointDto pointDto = null;
		Integer x = null;
		Integer y = null;
		for (int i = 0; i < list.size(); i++)
		{
			pointDto = list.get(i);
			x = StringUtils.isNullOrEmpty(pointDto.getX()) ? null : Integer.valueOf(pointDto.getX());
			y = StringUtils.isNullOrEmpty(pointDto.getY()) ? null : Integer.valueOf(pointDto.getY());
			x = (x == null) ? null : ((int) Math.round(x / scale));
			y = (y == null) ? null : ((int) Math.round(y / scale));
			pointDto.setX(x == null ? null : String.valueOf(x));
			pointDto.setY(y == null ? null : String.valueOf(y));
			result.add(pointDto);
		}
		return result;
	}

	@Override
	public ResultMap<List<MarkLampsVo>> getMarkLamps(MarkLampDto record)
	{
		ResultMap<List<MarkLampsVo>> result = new ResultMap<>();
		List<MarkLampsVo> list = cameraInfoMapper.selectByDeviceId(record.getDeviceId());
		List<MarkLampsVo> listData = null;

		if (list != null && list.size() > 0)
		{
			MarkLampsVo temp = null;
			listData = new ArrayList<>(list.size());
			List<PointVo> listPoint = null;
			PointVo pointVo = null;
			Integer x = null;
			Integer y = null;
			// 处理获取到的数据
			for (int i = 0; i < list.size(); i++)
			{
				temp = list.get(i);
				listPoint = temp.getPoints();
				if (listPoint != null && listPoint.size() > 0)
				{
					for (int m = 0; m < listPoint.size(); m++)
					{
						pointVo = listPoint.get(m);
						x = StringUtils.isNullOrEmpty(pointVo.getX()) ? null : Integer.valueOf(pointVo.getX());
						y = StringUtils.isNullOrEmpty(pointVo.getY()) ? null : Integer.valueOf(pointVo.getY());
						x = (x == null) ? null : ((int) Math.round(x * record.getScale()));
						y = (y == null) ? null : ((int) Math.round(y * record.getScale()));
						pointVo.setX(x == null ? null : String.valueOf(x));
						pointVo.setY(y == null ? null : String.valueOf(y));
						//// pointVo.setX(Integer.valueOf(StringUtils.isNullOrEmpty(pointVo.getX())?null:pointVo.getX()));
					}
					temp.setPoints(listPoint);
				}
				listData.add(temp);
			}
		}
		result.setData(listData);
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
			LampInfo lampInfo = lampInfoMapper.selectById(Integer.valueOf(id));
			if (lampInfo == null || StringUtils.isNullOrEmpty(lampInfo.getSerial()))
			{
				throw new BaseException(ErrorCodes.CAMERA_MARK_DELETE_FAIL);
			}
			Integer deleteResult = lampInfoMapper.deleteById(Integer.valueOf(id));
			if (deleteResult != 1)
			{
				throw new BaseException(ErrorCodes.CAMERA_MARK_DELETE_FAIL);
			}

			// List<LampInfo> selectByDeviceId
			List<LampInfo> listLampInfo = lampInfoMapper.selectByDeviceId(lampInfo.getDeviceId());
			Map<String, Object> queryMap = new HashMap<>(1);
			queryMap.put("deviceId", lampInfo.getDeviceId());
			List<CameraInfo> cameraInfoList = cameraInfoMapper.selectByMap(queryMap);
			if (cameraInfoList != null && cameraInfoList.size() > 0)
			{
				cameraProducerService.addonUpdate(cameraInfoList.get(0).getTopic(), listLampInfo);
			} else
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

		LampInfo lampInfo = markLampDtoToLampInfo(record, false, true);
		// 插入灯信息
		int count = lampInfoMapper.updateBySerail(lampInfo);
		if (count != 1)
		{
			throw new BaseException(ErrorCodes.CAMERA_UPDATE_MARK_FAIL);
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.foxconn.lamp.camera.service.CameraService#addCameraInfoList(java.util
	 * .List)
	 */
	@Override
	public ResultMap<Map<String, Object>> addCameraInfoList(List<CameraInfo> beans)
	{
		ResultMap<Map<String, Object>> result = new ResultMap<>();
		if (beans == null || beans.size() <= 0)
		{
			throw new BaseException(ErrorCodes.CAMERA_ADD_FAIL);
		}
		if (beans != null && beans.size() > 0)
		{
			cameraInfoMapper.batchReplaceInsert(beans);
		}

		return result;
	}

	@Override
	public boolean addonStateLamps(String data)
	{
		boolean result = false;
		CameraMqttMessage cameraMqttMessage = null;
		String responseData = null;
		CameraMqttMessageDataMsg cameraMqttMessageDataMsg = null;
		Integer counts = null;
		CameraMqttResponseMessageData cameraMqttResponseMessageData = null;
		JSONObject josonData = null;
		List<CameraMqttResponseLamp> lamps = null;
		CameraMqttResponseLamp cameraMqttResponseLamp = null;

		try
		{
			cameraMqttMessage = JSONObject.parseObject(data, CameraMqttMessage.class);
		} catch (Exception e)
		{
			cameraMqttMessage = null;
			log.error(e.getLocalizedMessage());
		}
		Map<String, String> validateMap = andonValidate.validateAndon(cameraMqttMessage);
		// 验证成功
		if (AndonValidate.FAIL.equals(validateMap.get(AndonValidate.VALIDATE)))
		{
			return false;
		}

		responseData = cameraMqttMessage.getData();
		cameraMqttResponseMessageData = JSONObject.parseObject(responseData, CameraMqttResponseMessageData.class);
		josonData = JSONObject.parseObject(responseData);

		cameraMqttMessageDataMsg = cameraMqttResponseMessageData.getMsg();
		// 每次查询如果需要更新就更新,如果不需要更新就不处理
		if (AndonValidate.ADDON_STATUS.equals(validateMap.get(AndonValidate.TYPE)))
		{
			// 查询成功，则更新数据库表
			if (cameraMqttMessageDataMsg != null && !StringUtils.isNullOrEmpty(cameraMqttMessageDataMsg.getCounts()))
			{
				try
				{
					counts = Integer.valueOf(cameraMqttMessageDataMsg.getCounts());
				} catch (NumberFormatException e)
				{
					counts = null;
					log.error(e.getLocalizedMessage());
				}
			} else
			{
				counts = null;
			}

			if (counts != null && counts > 0)
			{
				lamps = new ArrayList<>(counts);
				for (int j = 0; j < counts; j++)
				{
					cameraMqttResponseLamp = null;
					if (josonData.containsKey(String.valueOf(j)))
					{
						cameraMqttResponseLamp = josonData.getObject(String.valueOf(j), CameraMqttResponseLamp.class);
					}
					if (cameraMqttResponseLamp != null)
					{
						lamps.add(cameraMqttResponseLamp);
					}
				}
				cameraMqttResponseMessageData.setLamps(lamps);
			}
			// 根绝得到的数据，转换为需要处理的数据
			String topic = cameraMqttMessage.getSourceTopic();
			if (StringUtils.isNullOrEmpty(topic))
			{
				return false;
			}
			// 获取他deviceId
			Map<String, Object> queryMap = new HashMap<>(1);
			queryMap.put("topic", topic);
			List<CameraInfo> listCameraInfo = cameraInfoMapper.selectByMap(queryMap);
			// String deviceId = null;
			if (listCameraInfo == null || listCameraInfo.size() == 0)
			{
				return false;
			}
			String deviceId = listCameraInfo.get(0).getDeviceId();
			// messagingTemplate.convertAndSendToUser(deviceId.trim(),
			// "topic/"+deviceId, new CameraWebSocket("update","SuccessFul"));

			LampInfo lampInfo = null;
			List<LampInfo> insertList = new ArrayList<>();
			String serail = null;
			Date date = new Date();
			List<PointDto> listPoints = null;
			PointDto pointDto = null;
			List<Integer> pointsList = null;

			for (int i = 0; i < lamps.size(); i++)
			{
				cameraMqttResponseLamp = lamps.get(i);
				serail = cameraMqttResponseLamp.getSerial();
				if (StringUtils.isNullOrEmpty(serail))
				{// 如果序列号为空，则不需要处理
					continue;
				}
				lampInfo = lampInfoMapper.selectBySerail(serail);
				// 如果不为null，则为更新
				if (lampInfo != null)
				{
					if (CommonConstant.LAMP_INFO_UPDATE_STATUS_WAIT.intValue() != lampInfo.getUpdateStatus().intValue())
					{// 如果不是等待更新状态,则不处理
						continue;
					}
				} else
				{
					lampInfo = new LampInfo();

					lampInfo.setCreateTime(date);
					lampInfo.setCreater(CommonConstant.SYSTEM_ACCOUNT);
					lampInfo.setModifier(CommonConstant.SYSTEM_ACCOUNT);
					lampInfo.setName(cameraMqttResponseLamp.getName());
					lampInfo.setSerial(cameraMqttResponseLamp.getSerial());
					lampInfo.setType(String.valueOf(cameraMqttResponseLamp.getType()));
					lampInfo.setStatus(Long.valueOf(CommonConstant.LAMP_INFO_STATUS_EFFECTIVE));

				}
				/*
				 * if(!deviceId.equals(lampInfo.getDeviceId())){ lampInfo.setId(
				 * null); }
				 */
				lampInfo.setWidth(Long.valueOf(cameraMqttMessageDataMsg.getMaxWidth()));
				lampInfo.setHeight(Long.valueOf(cameraMqttMessageDataMsg.getMaxHeight()));
				lampInfo.setUpdateStatus(Long.valueOf(CommonConstant.LAMP_INFO_UPDATE_STATUS_COMPLETE));
				lampInfo.setModifyTime(date);
				//
				lampInfo.setDeviceId(deviceId);
				if (cameraMqttResponseLamp.getPoints() != null && cameraMqttResponseLamp.getPoints().size() > 0)
				{
					listPoints = new ArrayList<>(cameraMqttResponseLamp.getPoints().size());
					for (int m = 0; m < cameraMqttResponseLamp.getPoints().size(); m++)
					{
						pointDto = new PointDto();
						pointsList = cameraMqttResponseLamp.getPoints().get(m);
						if (pointsList != null && pointsList.size() >= 2)
						{
							pointDto.setX(String.valueOf(pointsList.get(0)));
							pointDto.setY(String.valueOf(pointsList.get(1)));
							listPoints.add(pointDto);
						}
					}
				} else
				{
					listPoints = null;
				}
				lampInfo.setPoints(listPoints != null ? JSONObject.toJSONString(listPoints) : null);
				// 设置临界值
				lampInfo.setThreshold((cameraMqttResponseLamp.getThreshold() == null
						|| cameraMqttResponseLamp.getThreshold().size() == 0) ? null
								: JSONObject.toJSONString(cameraMqttResponseLamp.getThreshold()));
				insertList.add(lampInfo);
			}

			if (insertList != null && insertList.size() > 0)
			{
				lampInfoMapper.batchReplaceInsert(insertList);
				/*
				 * if (insertResult != insertList.size()) { throw new
				 * BaseException(ErrorCodes.CAMERA_UPDATE_MARK_FAIL); }
				 */
			}
			// 没有任何异常表示成功
			result = true;
		} else if (AndonValidate.ADDON_UPDATE.equals(validateMap.get(AndonValidate.TYPE))) // update返回处理
		{
			// 查询成功，则更新数据库表
			// 查询数据库，获取需要更新的serial

			String deviceId = cameraMqttMessage.getSourceTopic();
			if (StringUtils.isNullOrEmpty(deviceId))
			{
				return false;
			}

			List<LampInfo> lampInfoList = lampInfoMapper.selectByDeviceId(deviceId);
			if (lampInfoList == null || lampInfoList.size() == 0)
			{// 如果为空，则表示没有
				return false;
			}
			counts = lampInfoList.size();
			Map<String, LampInfo> lampInfoMap = new HashMap<>();
			for (int i = 0; i < lampInfoList.size(); i++)
			{
				lampInfoMap.put(lampInfoList.get(i).getSerial(), lampInfoList.get(i));
			}

			if (counts != null && counts > 0)
			{
				lamps = new ArrayList<>(counts);
				for (int j = 0; j < counts; j++)
				{
					cameraMqttResponseLamp = null;
					if (josonData.containsKey(String.valueOf(j)))
					{
						cameraMqttResponseLamp = josonData.getObject(String.valueOf(j), CameraMqttResponseLamp.class);
					}
					if (cameraMqttResponseLamp != null)
					{
						lamps.add(cameraMqttResponseLamp);
					}
				}
				cameraMqttResponseMessageData.setLamps(lamps);
			}
			// 根绝得到的数据，转换为需要处理的数据

			LampInfo lampInfo = null;
			List<LampInfo> insertList = new ArrayList<>();
			String serail = null;
			Date date = new Date();
			List<PointDto> listPoints = null;
			PointDto pointDto = null;
			List<Integer> pointsList = null;
			for (int i = 0; i < lamps.size(); i++)
			{
				cameraMqttResponseLamp = lamps.get(i);
				serail = cameraMqttResponseLamp.getSerial();
				if (StringUtils.isNullOrEmpty(serail))
				{// 如果序列号为空，则不需要处理
					continue;
				}
				lampInfo = lampInfoMap.get(serail);
				// 如果不为null，则为更新
				if (lampInfo != null)
				{
					if (CommonConstant.LAMP_INFO_UPDATE_STATUS_WAIT.intValue() != lampInfo.getUpdateStatus().intValue())
					{// 如果不是等待更新状态,则不处理
						continue;
					}
				} else
				{
					lampInfo = new LampInfo();
					lampInfo.setCreateTime(date);
					lampInfo.setCreater(CommonConstant.SYSTEM_ACCOUNT);
					lampInfo.setModifier(CommonConstant.SYSTEM_ACCOUNT);
					lampInfo.setName(cameraMqttResponseLamp.getName());
					lampInfo.setStatus(Long.valueOf(CommonConstant.LAMP_INFO_STATUS_EFFECTIVE));
					lampInfo.setDeviceId(deviceId);
				}
				lampInfo.setUpdateStatus(Long.valueOf(CommonConstant.LAMP_INFO_UPDATE_STATUS_COMPLETE));
				lampInfo.setModifyTime(date);
				if (cameraMqttResponseLamp.getPoints() != null && cameraMqttResponseLamp.getPoints().size() > 0)
				{
					listPoints = new ArrayList<>(cameraMqttResponseLamp.getPoints().size());
					for (int m = 0; m < cameraMqttResponseLamp.getPoints().size(); m++)
					{
						pointDto = new PointDto();
						pointsList = cameraMqttResponseLamp.getPoints().get(m);
						if (pointsList != null && pointsList.size() >= 2)
						{
							pointDto.setX(String.valueOf(pointsList.get(0)));
							pointDto.setY(String.valueOf(pointsList.get(1)));
						}
					}
				} else
				{
					listPoints = null;
				}
				lampInfo.setPoints(listPoints != null ? JSONObject.toJSONString(listPoints) : null);
				// 设置临界值
				lampInfo.setThreshold((cameraMqttResponseLamp.getThreshold() == null
						|| cameraMqttResponseLamp.getThreshold().size() == 0) ? null
								: JSONObject.toJSONString(cameraMqttResponseLamp.getThreshold()));
				insertList.add(lampInfo);
			}
			if (insertList != null && insertList.size() > 0)
			{
				int insertResult = lampInfoMapper.batchReplaceInsert(insertList);
				if (insertResult != insertList.size())
				{
					throw new BaseException(ErrorCodes.CAMERA_UPDATE_MARK_FAIL);
				}
			}
			messagingTemplate.convertAndSendToUser(deviceId.trim(), "topic/" + deviceId,
					new CameraWebSocket("update", "1", "SuccessFul"));
			result = true;
		}
		return result;
	}
}
