package com.foxconn.lamp.config.il18;

import java.util.Locale;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class I18nConfig
{
	@Bean(name = "localeResolver")
	public MyLocaleResolver myLocaleResolver()
	{
		MyLocaleResolver myLocaleResolver = new MyLocaleResolver();
		myLocaleResolver.setDefaultLocale(Locale.SIMPLIFIED_CHINESE);
		return myLocaleResolver;
	}
}
