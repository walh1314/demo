package com.foxconn.lamp.common.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.foxconn.lamp.manager.domain.SysUser;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class SystemUtil
{

	public SysUser getCurrentUser()
	{

		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		HttpSession httpSession = null;
		if (request != null)
		{
			httpSession = request.getSession();
		}

		if (httpSession != null)
		{
			return (SysUser) httpSession.getAttribute("userInfo");
		}
		return null;
	}

	public void setCurrentUser(SysUser bean)
	{

		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		HttpSession httpSession = null;
		if (request != null)
		{
			httpSession = request.getSession();
		}

		if (httpSession != null)
		{
			httpSession.setAttribute("userInfo", bean);
		}
	}

	/**
	 * 设置创建人和修改人
	 * @param record
	 * @param isSetCreate
	 */
	public <T> void setCreaterAndModifier(T record, boolean isSetCreate)
	{
		SysUser currentUser = this.getCurrentUser();
		Method method = null;
		try
		{
			if (record != null)
			{
				if (currentUser != null)
				{

					method = record.getClass().getMethod("setModifier", String.class);

					if (method != null)
					{
						method.invoke(record, currentUser.getName());
					}
					// record.setModifier(currentUser.getName());
					if (isSetCreate)
					{
						method = record.getClass().getMethod("setCreater", String.class);
						if (method != null)
						{
							method.invoke(record, currentUser.getName());
						}

						method = record.getClass().getMethod("setCreateTime", Date.class);
						if (method != null)
						{
							method.invoke(record, new Date());
						}
					}

				}
				method = record.getClass().getMethod("setModifyTime", Date.class);
				if (method != null)
				{
					method.invoke(record, new Date());
				}
			}
		} catch (NoSuchMethodException e)
		{
			log.error(e.getMessage());
		} catch (SecurityException e)
		{
			log.error(e.getMessage());
		} catch (IllegalAccessException e)
		{
			log.error(e.getMessage());
		} catch (IllegalArgumentException e)
		{
			log.error(e.getMessage());
		} catch (InvocationTargetException e)
		{
			log.error(e.getMessage());
		}
	}
}
