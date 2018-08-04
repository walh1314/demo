package com.foxconn.lamp.thirdparty.shinobi;

import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.compress.utils.IOUtils;
import org.apache.http.HttpEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.foxconn.lamp.common.exception.BaseException;
import com.foxconn.lamp.common.exception.ErrorCodes;
import com.foxconn.lamp.common.util.HttpClientUtil;
import com.foxconn.lamp.thirdparty.ThirdpartyConstant;
import com.foxconn.lamp.thirdparty.config.ShinobiProperties;
import com.mysql.jdbc.StringUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author liupingan
 *
 */
@Component
@Slf4j
public class ShinobiService
{
	private Map<String, Object> headers;

	@Autowired
	private ShinobiProperties shinobiProperties;

	public ShinobiService()
	{
		
	}

	@PostConstruct
	public void init()
	{
		headers = new HashMap<>();
		headers.put("Content-Type", "application/json");
		headers.put("Accept", "application/json");
		headers.put("ke", shinobiProperties.getHeaderKe());
		headers.put("auth", shinobiProperties.getHeaderAuth());

	}

	public ShinobiService(Map<String, Object> headers)
	{
		this.headers = headers;
	}

	public String getSnapShot(String deviceId)
	{
		String url = shinobiProperties.getUrl() + ThirdpartyConstant.SHINOBI_SNAPSHOT + "/" + deviceId;
		HttpEntity httpEntity = HttpClientUtil.httpGet(url, headers);
		if (httpEntity == null)
		{
			throw new BaseException(ErrorCodes.SHINOBI_EMPTY);
		}
		try
		{
			InputStream input = httpEntity.getContent();
			byte[] byteArrays = IOUtils.toByteArray(input);
			StringBuffer buffer = new StringBuffer();
			buffer.append(Base64.getEncoder().encodeToString(byteArrays));
			if (StringUtils.isNullOrEmpty(buffer.toString()))
			{
				throw new BaseException(ErrorCodes.SHINOBI_EMPTY);
			}
			return buffer.toString();
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
