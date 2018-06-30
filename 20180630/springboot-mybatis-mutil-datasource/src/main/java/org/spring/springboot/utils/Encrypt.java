package org.spring.springboot.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Encrypt
{

	/**
	 * SHA-256
	 * 
	 * @param strText
	 * @return
	 */

	public static String SHA256(final String strText)
	{
		return SHA(strText, "SHA-256");
	}

	/**
	 * SHA-512
	 * 
	 * @param strText
	 * @return
	 */
	public static String SHA512(final String strText)
	{
		return SHA(strText, "SHA-512");
	}

	public static String MD5(final String strText)
	{
		MessageDigest md;
		try
		{
			md = MessageDigest.getInstance("MD5");
			// 计算md5函数
			md.update(strText.getBytes());
			return new BigInteger(1, md.digest()).toString(16);
		} catch (NoSuchAlgorithmException e)
		{
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * SHA
	 * 
	 * @param strSourceText
	 * @return
	 */
	private static String SHA(final String strText, final String strType)
	{
		String strResult = null;

		if (strText != null && strText.length() > 0)
		{
			try
			{
				MessageDigest messageDigest = MessageDigest.getInstance(strType);
				messageDigest.update(strText.getBytes());
				byte byteBuffer[] = messageDigest.digest();

				StringBuffer strHexString = new StringBuffer();
				for (int i = 0; i < byteBuffer.length; i++)
				{
					String hex = Integer.toHexString(0xff & byteBuffer[i]);
					if (hex.length() == 1)
					{
						strHexString.append('0');
					}
					strHexString.append(hex);
				}
				strResult = strHexString.toString();
			} catch (NoSuchAlgorithmException e)
			{
				e.printStackTrace();
			}
		}

		return strResult;
	}
}