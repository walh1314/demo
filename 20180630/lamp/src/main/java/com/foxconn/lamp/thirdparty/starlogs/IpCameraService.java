package com.foxconn.lamp.thirdparty.starlogs;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.compress.utils.IOUtils;
import org.apache.http.HttpEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.foxconn.lamp.common.exception.BaseException;
import com.foxconn.lamp.common.exception.ErrorCodes;
import com.foxconn.lamp.common.util.HttpClientUtil;
import com.foxconn.lamp.thirdparty.ThirdpartyConstant;
import com.foxconn.lamp.thirdparty.config.StarLogsProperties;
import com.mysql.jdbc.StringUtils;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class IpCameraService
{

	
	private Map<String, Object> headers;

	@Autowired
	private StarLogsProperties starLogsProperties;

	public IpCameraService()
	{
		
	}

	@PostConstruct
	public void init()
	{
		headers = new HashMap<>();
		headers.put("Content-Type", "application/json");
		headers.put("Accept", "application/json");

	}

	public IpCameraService(Map<String, Object> headers)
	{
		this.headers = headers;
	}

	public JSONObject getLogByUser(Long memberCode)
	{
		JSONObject result = null;
		
		String url = starLogsProperties.getUrl() + ThirdpartyConstant.STARTLOGS_BASE +ThirdpartyConstant.STARTLOGS_IPCAM_GET_LOG_BY_USER ;
		GetLogByUserRequest requestData = new GetLogByUserRequest(memberCode);
		HttpEntity httpEntity = HttpClientUtil.httpPost(url, headers,new String(JSONObject.toJSONString(requestData).getBytes(),StandardCharsets.UTF_8));
		if (httpEntity == null)
		{
			throw new BaseException(ErrorCodes.STARLOGS_EMPTY);
		}
		try
		{
			InputStream input = httpEntity.getContent();
			byte[] byteArrays = IOUtils.toByteArray(input);
			StringBuffer buffer = new StringBuffer();
			buffer.append(new String(byteArrays,StandardCharsets.UTF_8));
			if (StringUtils.isNullOrEmpty(buffer.toString()))
			{
				throw new BaseException(ErrorCodes.STARLOGS_EMPTY);
			}
			result = JSONObject.parseObject(buffer.toString());
			return result;
		} catch (UnsupportedOperationException e)
		{
			log.error(e.getMessage());
		} catch (IOException e)
		{
			log.error(e.getMessage());
		}
		return null;
	}
}
