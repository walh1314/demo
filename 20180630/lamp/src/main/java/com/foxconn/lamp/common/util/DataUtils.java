package com.foxconn.lamp.common.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author liupingan
 *
 */
@Slf4j
public class DataUtils
{

	/**
	 * Object field switch
	 * 
	 * @param source
	 * @param target
	 * @return
	 */
	public static Object ObjectInterconversion(Object source, Class<? extends Object> target)
	{
		if (!(source instanceof Object))
		{
			return null;
		}
		Object result = null;
		try
		{
			result = target.getConstructor().newInstance();

			Field[] fields = target.getDeclaredFields();
			Field targetField = null;
			Field sourceField = null;
			for (int i = 0; i < fields.length; i++)
			{// 遍历

				try
				{
					// 得到属性
					targetField = fields[i];
					// 打开私有访问
					targetField.setAccessible(true);
					// 获取属性
					String name = targetField.getName();
					if ("serialVersionUID".equals(name))
					{
						continue;
					}
					sourceField = source.getClass().getDeclaredField(name);
					if (sourceField != null && targetField.getType().equals(sourceField.getType()))
					{
						sourceField.setAccessible(true);
						targetField.set(result, sourceField.get(source));
					}
				} catch (IllegalAccessException e)
				{
					log.error(e.getMessage());
				} catch (NoSuchFieldException e)
				{
					log.error(e.getMessage());
				}
			}
		} catch (NoSuchMethodException e)
		{
			log.error(e.getMessage());
		} catch (SecurityException e)
		{
			log.error(e.getMessage());
		} catch (InstantiationException e1)
		{
			log.error(e1.getMessage());
		} catch (IllegalAccessException e1)
		{
			log.error(e1.getMessage());
		} catch (IllegalArgumentException e1)
		{
			log.error(e1.getMessage());
		} catch (InvocationTargetException e1)
		{
			log.error(e1.getMessage());
		}
		return result;
	}

}
