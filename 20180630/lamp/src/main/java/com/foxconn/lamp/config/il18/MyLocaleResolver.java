package com.foxconn.lamp.config.il18;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

public class MyLocaleResolver extends AcceptHeaderLocaleResolver
{
	private Locale myLocal;

	@Override
	public Locale resolveLocale(HttpServletRequest request)
	{
		return myLocal == null ? request.getLocale() : myLocal;
	}

	@Override
	public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale)
	{
		myLocal = locale;
	}
}