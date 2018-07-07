package com.foxconn.lamp.common.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FFmpegUtil
{
	public static void screenshot()
	{
		ProcessBuilder builder = new ProcessBuilder();
		builder.command("D://liupingan/software/ffmpeg-3.3.1-win32-static/bin/ffmpeg.exe", "-i",
				"rtsp://184.72.239.149/vod/mp4://BigBuckBunny_175k.mov", "-f", "image2", "-ss", "4", "-vframes", "1",
				"-s", "1024*768", "d://a-1012.jpg");
		builder.redirectErrorStream(false);
		try
		{
			Process process = builder.start();
			InputStream in = process.getInputStream();
			log.info("正在进行截图，请稍候=======================");
			convertStreamToString(in);
			InputStream errorStream = process.getErrorStream();
			if (errorStream != null && errorStream.read() > 0)
			{
				convertStreamToString(errorStream);
			}
			in.close();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * 读取流信息
	 * @param is
	 * @return
	 */
	public static String convertStreamToString(InputStream is)
	{
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));

		StringBuilder sb = new StringBuilder();

		String line = null;

		try
		{
			while ((line = reader.readLine()) != null)
			{
				System.out.println(line);
				sb.append(line + "/n");
			}
		} catch (IOException e)
		{
			e.printStackTrace();

		} finally
		{
			try
			{
				is.close();
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		return sb.toString();

	}
}
