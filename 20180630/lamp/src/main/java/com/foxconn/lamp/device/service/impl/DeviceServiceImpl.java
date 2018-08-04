package com.foxconn.lamp.device.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foxconn.lamp.camera.domain.CameraInfo;
import com.foxconn.lamp.camera.domain.CameraType;
import com.foxconn.lamp.camera.service.CameraService;
import com.foxconn.lamp.camera.service.CameraTypeService;
import com.foxconn.lamp.common.entity.ResultMap;
import com.foxconn.lamp.common.exception.BaseException;
import com.foxconn.lamp.common.exception.ErrorCodes;
import com.foxconn.lamp.common.util.SystemUtil;
import com.foxconn.lamp.device.service.DeviceService;
import com.foxconn.lamp.dto.DeviceDto;
import com.foxconn.lamp.thirdparty.ThirdpartyConstant;
import com.foxconn.lamp.thirdparty.am.AMDeviceInfo;
import com.foxconn.lamp.thirdparty.am.AMService;
import com.foxconn.lamp.thirdparty.dm.DMDeviceInfo;
import com.foxconn.lamp.thirdparty.dm.DMService;
import com.foxconn.lamp.vo.DeviceTypeInfoVo;
import com.foxconn.lamp.vo.DeviceVo;
import com.mysql.jdbc.StringUtils;

@Service(value = "deviceService")
public class DeviceServiceImpl implements DeviceService
{

	@Autowired
	private CameraService cameraService;

	@Autowired
	SystemUtil systemUtil;
	@Autowired
	private AMService amService;

	@Autowired
	private DMService dmService;

	@Autowired
	private CameraTypeService cameraTypeService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.foxconn.lamp.device.service.DeviceService#getRequireAddDeviceList(com
	 * .foxconn.lamp.dto.DeviceDto)
	 */
	@Override
	public ResultMap<List<DeviceVo>> getRequireAddDeviceList(DeviceDto bean)
	{
		ResultMap<List<DeviceVo>> result = new ResultMap<>();
		if (StringUtils.isNullOrEmpty(bean.getUserId()))
		{
			throw new BaseException(ErrorCodes.FAILED);
		}
		if (StringUtils.isNullOrEmpty(bean.getMobile()))
		{
			throw new BaseException(ErrorCodes.FAILED);
		}

		Long memberCode = amService.registerUser(bean.getUserId(), bean.getMobile());
		if (memberCode == null)
		{
			throw new BaseException(ErrorCodes.FAILED);
		}
		List<Integer> deviceTypeIds = new ArrayList<>();
		deviceTypeIds.add(ThirdpartyConstant.AM_DM_DEVICE_TYPE_CAMERA);

		List<AMDeviceInfo> listDeviceInfo = amService.getAvailableDevice(memberCode, deviceTypeIds);
		List<DeviceVo> deviceCVoList = null;
		if (listDeviceInfo != null && listDeviceInfo.size() > 0)
		{
			deviceCVoList = new ArrayList<>(listDeviceInfo.size());
			List<DMDeviceInfo> listTemp = null;
			DMDeviceInfo dmDeviceInfo = null;
			AMDeviceInfo amDeviceInfo = null;
			DeviceVo deviceVoTemp = null;
			DeviceTypeInfoVo deviceTypeInfoVo = null;
			Map<Integer, CameraType> cameraTypeMap = new HashMap<>(5);
			CameraType cameraType = null;
			for (int i = 0; i < listDeviceInfo.size(); i++)
			{
				amDeviceInfo = listDeviceInfo.get(i);
				deviceVoTemp = new DeviceVo();

				deviceVoTemp.setAlias(amDeviceInfo.getAlias());
				deviceVoTemp.setDeviceId(amDeviceInfo.getDeviceId());
				deviceVoTemp.setName(amDeviceInfo.getName());
				deviceVoTemp.setStatus(ThirdpartyConstant.AM_DM_DEVICE_STATUS_NOT_EXISTS);
				ResultMap<CameraInfo> cameraInfoResult = cameraService.findByDeviceId(amDeviceInfo.getDeviceId());
				if (cameraInfoResult != null && ErrorCodes.SCUUESS.getCode().equals(cameraInfoResult.getCode()))
				{
					CameraInfo cameraInfo = cameraInfoResult.getData();
					if (cameraInfo != null)
					{
						deviceVoTemp.setStatus(ThirdpartyConstant.AM_DM_DEVICE_STATUS_EXISTS);
					}
				}
				// 添加类型，如果类型不存在

				listTemp = dmService.getDeviceInfo(amDeviceInfo.getDeviceId());
				if (listTemp.size() > 0)
				{
					dmDeviceInfo = listTemp.get(0);
					if (dmDeviceInfo.getDeviceTypeInfo() != null && dmDeviceInfo.getDeviceTypeInfo().size() > 0)
					{
						deviceTypeInfoVo = new DeviceTypeInfoVo(dmDeviceInfo.getDeviceTypeInfo().get(0).getTypeId(),
								dmDeviceInfo.getDeviceTypeInfo().get(0).getTypeName());
						cameraType = new CameraType();
						cameraType.setId((int) deviceTypeInfoVo.getTypeId());
						cameraType.setName(deviceTypeInfoVo.getTypeName());
						cameraType.setCode(String.valueOf(deviceTypeInfoVo.getTypeId()));
						cameraType.setOrder(cameraType.getId());
						cameraTypeMap.put(cameraType.getId(), cameraType);
					}
					// 查询是否有添加,
					deviceVoTemp.setDeviceType(deviceTypeInfoVo);
					deviceVoTemp.setMacAddr(dmDeviceInfo.getMacAddr());
					deviceVoTemp.setManufacturer(dmDeviceInfo.getManufacturer());
					deviceVoTemp.setModelName(dmDeviceInfo.getModelName());
					deviceVoTemp.setSerialNo(dmDeviceInfo.getSerialNo());
					deviceVoTemp.setSku(dmDeviceInfo.getSku());
					deviceVoTemp.setTopic(dmDeviceInfo.getTopic());
				}
				deviceCVoList.add(deviceVoTemp);
			}
			if (cameraTypeMap != null && cameraTypeMap.size() > 0)
			{
				Collection<CameraType> valueCollection = cameraTypeMap.values();
				List<CameraType> valueList = new ArrayList<>(valueCollection);
				cameraTypeService.replaceInsertCameraTypeListAsync(valueList);
			}
		}
		result.setData(deviceCVoList);
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.foxconn.lamp.device.service.DeviceService#addDeviceList(com.foxconn.
	 * lamp.dto.DeviceDto)
	 */
	@Override
	public ResultMap<Map<String, Object>> addDeviceList(List<DeviceDto> beans)
	{
		ResultMap<Map<String, Object>> result = new ResultMap<>();
		if (beans != null && beans.size() > 0)
		{
			List<CameraInfo> insertList = new ArrayList<>(beans.size());
			CameraInfo cameraInfo = null;
			Map<String, Object> queryMap = null;
			List<CameraInfo> camreInfoList = null;
			for (int i = 0; i < beans.size(); i++)
			{
				queryMap = new HashMap<>(1);
				queryMap.put("serial", beans.get(i).getSerial());
				camreInfoList = cameraService.selectByMap(queryMap);
				if (camreInfoList != null && camreInfoList.size() > 0)
				{
					cameraInfo = camreInfoList.get(0);
					cameraInfo.setTopic(beans.get(i).getTopic());
					cameraInfo.setMacAddr(beans.get(i).getMacAddr());
					cameraInfo.setManufacturer(beans.get(i).getManufacturer());
				} else
				{
					cameraInfo = deviceDtoToCameraInfo(beans.get(i));
				}
				insertList.add(cameraInfo);
			}
			return cameraService.addCameraInfoList(insertList);
		}
		return result;
	}

	private CameraInfo deviceDtoToCameraInfo(DeviceDto bean)
	{
		CameraInfo result = new CameraInfo();
		result.setSerial(bean.getSerial());
		result.setName(bean.getAlias());
		result.setMacAddr(bean.getMacAddr());
		// result.setMa(bean.getManufacturer())
		result.setDeviceId(bean.getDeviceId());
		result.setTopic(bean.getTopic());
		result.setManufacturer( bean.getManufacturer());
		systemUtil.setCreaterAndModifier(result, true);
		result.setType(String.valueOf(bean.getDeviceType() != null ? bean.getDeviceType().getTypeId() : null));
		return result;
	}
}
