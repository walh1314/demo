package com.foxconn.lamp.test;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

public class GeneratorSqlmap
{
	public void generator() throws Exception
	{

		List<String> warnings = new ArrayList<String>();
		boolean overwrite = true;
		ClassLoader classLoader = getClass().getClassLoader();
		URL url = classLoader.getResource("generatorConfig.xml");
		// System.out.println(url.getPath());
		// System.out.println(Paths.get("generatorConfig.xml").getFileName());
		// 指定 逆向工程配置文件
		File configFile = new File(url.getFile());
		System.out.println();
		ConfigurationParser cp = new ConfigurationParser(warnings);
		Configuration config = cp.parseConfiguration(configFile);
		DefaultShellCallback callback = new DefaultShellCallback(overwrite);
		MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
		myBatisGenerator.generate(null);

	}

	public static void main(String[] args) throws Exception
	{
		try
		{
			GeneratorSqlmap generatorSqlmap = new GeneratorSqlmap();
			generatorSqlmap.generator();
		} catch (Exception e)
		{
			e.printStackTrace();
		}

	}
}
