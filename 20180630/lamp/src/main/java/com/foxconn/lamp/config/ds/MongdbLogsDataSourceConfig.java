package com.foxconn.lamp.config.ds;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.MongoClient;

@Configuration
@EnableMongoRepositories(basePackages = MongdbLogsDataSourceConfig.PACKAGE, mongoTemplateRef = MongdbLogsDataSourceConfig.MONGO_TEMPLATE)
public class MongdbLogsDataSourceConfig
{
	protected static final String MONGO_TEMPLATE = "primaryMongoTemplate";

	static final String PACKAGE = "com.foxconn.lamp.**.repository";

	@Value("${mongdb.logs.datasource.uri}")
	private String uri;

	@Value("${mongdb.logs.datasource.database}")
	private String database;

	@Value("${mongdb.logs.datasource.username}")
	private String user;

	@Value("${mongdb.logs.datasource.password}")
	private String password;

	@Value("${mongdb.logs.datasource.driverClassName}")
	private String driverClass;

	@Value("mongdb.logs.pagehelper.helperDialect}")
	private String helperDialect;

	@Value("mongdb.logs.pagehelper.offsetAsPageNum}")
	private String offsetAsPageNum;

	@Bean
	public MongoDbFactory primaryFactory() throws Exception
	{
		return new SimpleMongoDbFactory(new MongoClient(uri), database);
	}

	@Bean(name = MongdbLogsDataSourceConfig.MONGO_TEMPLATE)
	public MongoTemplate primaryMongoTemplate() throws Exception
	{

		// 移除_class
		MappingMongoConverter converter = new MappingMongoConverter(new DefaultDbRefResolver(primaryFactory()),
				new MongoMappingContext());
		converter.setTypeMapper(new DefaultMongoTypeMapper(null));

		MongoTemplate mongoTemplate = new MongoTemplate(primaryFactory(), converter);

		return mongoTemplate;

	}

}