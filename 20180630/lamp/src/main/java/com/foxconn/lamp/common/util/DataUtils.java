package com.foxconn.lamp.common.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

/**
 * 
 * @author liupingan
 *
 */
public class DataUtils
{

	/**
	 * Object field switch
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
					e.printStackTrace();
				} catch (NoSuchFieldException e)
				{
					e.printStackTrace();
				}
			}
		} catch (NoSuchMethodException e)
		{
			e.printStackTrace();
		} catch (SecurityException e)
		{
			e.printStackTrace();
		} catch (InstantiationException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IllegalAccessException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IllegalArgumentException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InvocationTargetException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return result;
	}

}
