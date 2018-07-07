package com.foxconn.lamp.controller.base;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.bind.annotation.RestController;

import com.foxconn.lamp.common.entity.ResultMap;
import com.mysql.jdbc.StringUtils;

@RestController
public class BaseController
{
	private MessageSource messageSource;

	@Value("${spring.messages.basename}")
	private String basename;

	@Value("${spring.messages.cache-duration}")
	private int cacheSeconds;

	@Value("${spring.messages.encoding}")
	private String encoding;

	/**
	 * 初始化
	 * 
	 * @return
	 */
	private MessageSource initMessageSource()
	{
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasename(basename);
		messageSource.setDefaultEncoding(encoding);
		messageSource.setCacheSeconds(cacheSeconds);
		return messageSource;
	}

	/**
	 * 设置当前的返回信息
	 * 
	 * @param request
	 * @param code
	 * @return
	 */
	public ResultMap<? extends Object> getMessage(HttpServletRequest request, ResultMap<? extends Object> result)
	{
		if (messageSource == null)
		{
			messageSource = initMessageSource();
		}
		String acceptLanguage = request.getHeader("Accept-Language");
		// 默认没有就是请求地区的语言
		Locale locale = null;

		locale = getLauage(acceptLanguage);
		if (locale == null)
		{
			locale = request.getLocale();
		}
		if (result != null)
		{
			List<String> args = null;
			if (result.getArgs() != null)
			{
				args = new ArrayList<>(result.getArgs().length);
				for (int i = 0; i < result.getArgs().length; i++)
				{
					// args 国际化
					args.add(messageSource.getMessage(result.getArgs()[i], null, locale));
				}
			}

			if (StringUtils.isNullOrEmpty(result.getMsg()))
			{
				if (!StringUtils.isNullOrEmpty(result.getCode()))
				{
					result.setMsg(messageSource.getMessage(result.getCode(), args.toArray(), locale));
				}
			} else
			{
				result.setMsg(messageSource.getMessage(result.getMsg(), args.toArray(), locale));
			}
		}
		return result;
	}

	/**
	 * @param acceptLanguage
	 * @return
	 */
	private Locale getLauage(String acceptLanguage)
	{
		Locale result = null;
		if (StringUtils.isNullOrEmpty(acceptLanguage))
		{
			return result;
		} else
		{
			String[] languages = acceptLanguage.split("([,;]{1})|(q=[0-9][.]?[0-9]?)");
			String[] locales = null;
			for (int i = 0; i < languages.length; i++)
			{
				if (StringUtils.isNullOrEmpty(languages[i]))
				{
					continue;
				}
				locales = languages[i].split("-");
				result = new Locale(locales[0].trim(), locales.length > 1 ? locales[1].trim() : null,
						locales.length > 2 ? locales[2].trim() : null);
			}
		}
		return result;
	}
}
