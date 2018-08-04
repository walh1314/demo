package com.foxconn.lamp.common.entity;

import java.util.HashMap;
import java.util.Map;

public enum MediaTypeEnum
{

	ImageJpg("jpg", "image/jpeg", "FFD8FF"), 
	ImageGif("gif", "image/gif", "47494638"), 
	ImagePng("png", "image/png", "89504E47"),
	ImageWebp("webp", "image/webp", "52494646");

	private final String ext;
	private final String mime;
	private final String magic;

	MediaTypeEnum(String ext, String mime, String magic)
	{
		this.ext = ext;
		this.mime = mime;
		this.magic = magic;
	}

	private static Map<String, MediaTypeEnum> map;
	static
	{
		map = new HashMap<>(4);
		for (MediaTypeEnum e : values())
		{
			map.put(e.getExt(), e);
		}
	}

	public static MediaTypeEnum getEnum(String type)
	{
		if (type == null)
		{
			return ImageJpg;
		}
		MediaTypeEnum e = map.get(type.toLowerCase());
		return e == null ? ImageJpg : e;
	}

	private String getExt()
	{
		return ext;
	}

	public String getMime()
	{
		return mime;
	}

	public String getMagic()
	{
		return magic;
	}

}
