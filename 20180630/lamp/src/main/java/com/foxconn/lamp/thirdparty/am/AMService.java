package com.foxconn.lamp.thirdparty.am;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.foxconn.lamp.common.exception.BaseException;
import com.foxconn.lamp.common.exception.ErrorCodes;
import com.foxconn.lamp.common.util.HttpClientUtil;
import com.foxconn.lamp.thirdparty.ThirdpartyConstant;
import com.foxconn.lamp.thirdparty.config.AmProperties;
import com.mysql.jdbc.StringUtils;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class AMService
{
	private Map<String, Object> headers;

	@Autowired
	private AmProperties amProperties;

	private static int size = 1024;

	public AMService()
	{
		headers = new HashMap<>();
		headers.put("Content-Type", "application/json");
		headers.put("Accept", "application/json");
	}

	public AMService(Map<String, Object> headers)
	{
		this.headers = headers;
	}

	/**
	 * 获取用户信息
	 * 
	 * @param memberCode
	 * @return
	 */
	public UserProfile getUserData(String memberCode)
	{
		String url = amProperties.getUrl() + ThirdpartyConstant.AM_GET_USER_DATA;
		HttpEntity httpEntity = HttpClientUtil.httpPost(url, headers, memberCode);
		if (httpEntity == null)
		{
			throw new BaseException(ErrorCodes.AM_DM_EMPTY);
		}
		try
		{
			InputStream input = httpEntity.getContent();
			byte[] byteArrays = new byte[size];
			StringBuffer buffer = new StringBuffer();
			while (input.read(byteArrays) != -1)
			{
				input.read(byteArrays);
				buffer.append(new String(byteArrays, StandardCharsets.UTF_8));
			}
			if (StringUtils.isNullOrEmpty(buffer.toString()))
			{
				throw new BaseException(ErrorCodes.AM_DM_EMPTY);
			}
			JSONObject jsonObject = JSONObject.parseObject(buffer.toString());
			if (jsonObject.containsKey(ThirdpartyConstant.AM_DM_ERR_CODE))
			{
				String errorCode = jsonObject.getString(ThirdpartyConstant.AM_DM_ERR_CODE);
				if (!ThirdpartyConstant.AM_DM_SUCCESS.equals(errorCode))
				{
					throw new BaseException(ErrorCodes.AM_DM_CODE_ERROR.getCode(), errorCode + ":" + jsonObject.getString(ThirdpartyConstant.AM_DM_ERR_MSG));
				}

				if (jsonObject.containsKey(ThirdpartyConstant.AM_DM_USER_PROFILE))
				{
					return jsonObject.getObject(ThirdpartyConstant.AM_DM_USER_PROFILE, UserProfile.class);
				}
			} else
			{
				throw new BaseException(ErrorCodes.AM_DM_DATA_FAIL);
			}
		} catch (UnsupportedOperationException e)
		{
			log.error(e.getMessage());
		} catch (IOException e)
		{
			log.error(e.getMessage());
		}
		return null;
	}

	public Long registerUser(String userId, String mobile)
	{
		String url = amProperties.getUrl() + ThirdpartyConstant.AM_REGISTER_USER;
		RegisterUserRequest requestBody = new RegisterUserRequest(userId, mobile);
		// this.headers = new HashMap<String,Object>();
		headers.remove("Accept");
		HttpEntity httpEntity = HttpClientUtil.httpPost(url, headers, JSONObject.toJSONString(requestBody));
		if (httpEntity == null)
		{
			throw new BaseException(ErrorCodes.AM_DM_EMPTY);
		}
		try
		{
			InputStream input = httpEntity.getContent();
			byte[] byteArrays = new byte[size];
			StringBuffer buffer = new StringBuffer();
			while (input.read(byteArrays) != -1)
			{
				buffer.append(new String(byteArrays, StandardCharsets.UTF_8));
			}
			if (StringUtils.isNullOrEmpty(buffer.toString()))
			{
				throw new BaseException(ErrorCodes.AM_DM_EMPTY);
			}
			JSONObject jsonObject = JSONObject.parseObject(buffer.toString());
			if (jsonObject.containsKey(ThirdpartyConstant.AM_DM_ERR_CODE))
			{
				String errorCode = jsonObject.getString(ThirdpartyConstant.AM_DM_ERR_CODE);
				if (!ThirdpartyConstant.AM_DM_SUCCESS.equals(errorCode))
				{
					throw new BaseException(ErrorCodes.AM_DM_CODE_ERROR.getCode(), errorCode + ":" + jsonObject.getString(ThirdpartyConstant.AM_DM_ERR_MSG));
				}

				if (jsonObject.containsKey(ThirdpartyConstant.AM_DM_MEMBER_CODE))
				{
					return jsonObject.getLong(ThirdpartyConstant.AM_DM_MEMBER_CODE);
				}
			} else
			{
				throw new BaseException(ErrorCodes.AM_DM_DATA_FAIL);
			}
		} catch (UnsupportedOperationException e)
		{
			log.error(e.getMessage());
		} catch (IOException e)
		{
			log.error(e.getMessage());
		}
		return null;
	}

	public List<AMDeviceInfo> getAvailableDevice(Long memberCode, List<Integer> deviceTypeIds)
	{

		String url = amProperties.getUrl() + ThirdpartyConstant.AM_GET_AVAILABLE_DEVICE;
		AvailableDeviceUserRequest requestBody = new AvailableDeviceUserRequest(memberCode, deviceTypeIds);
		HttpEntity httpEntity = HttpClientUtil.httpPost(url, headers, JSONObject.toJSONString(requestBody));
		if (httpEntity == null)
		{
			throw new BaseException(ErrorCodes.AM_DM_EMPTY);
		}
		try
		{
			InputStream input = httpEntity.getContent();
			byte[] byteArrays = new byte[size];
			StringBuffer buffer = new StringBuffer();
			while (input.read(byteArrays) != -1)
			{
				input.read(byteArrays);
				buffer.append(new String(byteArrays, StandardCharsets.UTF_8));
			}
			if (StringUtils.isNullOrEmpty(buffer.toString()))
			{
				throw new BaseException(ErrorCodes.AM_DM_EMPTY);
			}
			JSONObject jsonObject = JSONObject.parseObject(buffer.toString());
			if (jsonObject.containsKey(ThirdpartyConstant.AM_DM_ERR_CODE))
			{
				String errorCode = jsonObject.getString(ThirdpartyConstant.AM_DM_ERR_CODE);
				if (!ThirdpartyConstant.AM_DM_SUCCESS.equals(errorCode))
				{
					throw new BaseException(ErrorCodes.AM_DM_CODE_ERROR.getCode(),
							errorCode + ":" + jsonObject.getString(ThirdpartyConstant.AM_DM_ERR_MSG));
				}

				if (jsonObject.containsKey(ThirdpartyConstant.AM_DM_AVAILABLE_DEVICEINFO))
				{

					List<AMDeviceInfo> result = null;
					JSONArray jsonArray = jsonObject.getJSONArray(ThirdpartyConstant.AM_DM_AVAILABLE_DEVICEINFO);
					if (jsonArray == null || jsonArray.size() <= 0)
					{
						return result;
					}
					result = new ArrayList<>(jsonArray.size());
					for (int i = 0; i < jsonArray.size(); i++)
					{
						result.add(jsonArray.getObject(i, AMDeviceInfo.class));
					}
					return result;
				}
			} else
			{
				throw new BaseException(ErrorCodes.AM_DM_DATA_FAIL);
			}
		} catch (UnsupportedOperationException e)
		{
			log.error(e.getMessage());
		} catch (IOException e)
		{
			log.error(e.getMessage());
		}
		return null;
	}

	public Map<String, Object> getHeaders()
	{
		return headers;
	}

	public void setHeaders(Map<String, Object> headers)
	{
		this.headers = headers;
	}
}
