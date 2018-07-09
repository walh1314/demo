package com.foxconn.lamp.shiro.config;

import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.Filter;

import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import com.foxconn.lamp.manager.domain.SysPermissionInit;
import com.foxconn.lamp.manager.service.SysPermissionInitService;
import com.foxconn.lamp.shiro.auth.CustomAuthorizingRealm;
import com.foxconn.lamp.shiro.auth.credential.CustomCredentialsMatcher;
import com.foxconn.lamp.shiro.web.filter.CustomFormAuthenticationFilter;
import com.foxconn.lamp.shiro.web.filter.KickoutSessionControlFilter;

/**
 * shiro的配置类
 * 
 * @author Administrator
 *
 */
@Configuration
public class ShiroConfiguration
{
	@Value("${spring.redis.host}")
	private String host;

	@Value("${spring.redis.port}")
	private int port;

	@Value("${spring.redis.timeout}")
	private int timeout;
	
	@Value("${spring.redis.password}")
	private String password;
	
	@Bean(name = "shiroFilter")
	public ShiroFilterFactoryBean shiroFilter(@Qualifier("securityManager") SecurityManager manager,@Qualifier("sysPermissionInitService") SysPermissionInitService sysPermissionInitService)
	{

		ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
		bean.setSecurityManager(manager);
		// 配置登录的url和登录成功的url
		bean.setLoginUrl("/login");
		LinkedHashMap<String, Filter> filters = new LinkedHashMap<>();
		filters.put("authc", new CustomFormAuthenticationFilter());
		filters.put("kickout", kickoutSessionControlFilter());
		bean.setFilters(filters);

		// 配置访问权限
		LinkedHashMap<String, String> filterChainDefinitionMap = new LinkedHashMap<>();

		List<SysPermissionInit> list = sysPermissionInitService.selectAll();

		for (SysPermissionInit sysPermissionInit : list)
		{
			filterChainDefinitionMap.put(sysPermissionInit.getUrl(), sysPermissionInit.getPermissionInit());
		}
		bean.setFilterChainDefinitionMap(filterChainDefinitionMap);

		return bean;
	}

	// 配置核心安全事务管理器
	@Bean(name = "securityManager")
	public SecurityManager securityManager(@Qualifier("authRealm") AuthorizingRealm authRealm)
	{

		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		// 设置realm.
		securityManager.setRealm(authRealm);
		// 自定义缓存实现 使用redis
		securityManager.setCacheManager(cacheManager());
		// 自定义session管理 使用redis
		securityManager.setSessionManager(sessionManager());
		// 注入记住我管理器;
		securityManager.setRememberMeManager(rememberMeManager());
		return securityManager;

	}

	/**
	 * cookie对象;
	 * 
	 * @return
	 */
	public SimpleCookie rememberMeCookie()
	{
		// 这个参数是cookie的名称，对应前端的checkbox的name = rememberMe
		SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
		// <!-- 记住我cookie生效时间30天 ,单位秒;-->
		simpleCookie.setMaxAge(2592000);
		return simpleCookie;
	}

	/**
	 * cookie管理对象;记住我功能
	 * 
	 * @return
	 */
	public CookieRememberMeManager rememberMeManager()
	{
		CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
		cookieRememberMeManager.setCookie(rememberMeCookie());
		// rememberMe cookie加密的密钥 建议每个项目都不一样 默认AES算法 密钥长度(128 256 512 位)
		cookieRememberMeManager.setCipherKey(Base64.getDecoder().decode("3AvVhmFLUs0KTA3Kprsdag=="));
		return cookieRememberMeManager;
	}

	// 配置自定义的权限登录器
	@Bean(name = "authRealm")
	public AuthorizingRealm authRealm(@Qualifier("credentialsMatcher") CredentialsMatcher matcher)
	{
		AuthorizingRealm authRealm = new CustomAuthorizingRealm();
		authRealm.setCredentialsMatcher(matcher);
		return authRealm;
	}

	// 配置自定义的密码比较器
	@Bean(name = "credentialsMatcher")
	public CredentialsMatcher credentialsMatcher()
	{
		return new CustomCredentialsMatcher();
	}

	@Bean
	public static LifecycleBeanPostProcessor lifecycleBeanPostProcessor()
	{
		return new LifecycleBeanPostProcessor();
	}

	@Bean
	public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator()
	{
		DefaultAdvisorAutoProxyCreator creator = new DefaultAdvisorAutoProxyCreator();
		creator.setProxyTargetClass(true);
		return creator;
	}

	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(
			@Qualifier("securityManager") SecurityManager manager)
	{
		AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
		advisor.setSecurityManager(manager);
		return advisor;
	}

	/**
	 * 配置shiro redisManager 使用的是shiro-redis开源插件
	 * 
	 * @return
	 */
	@Bean
	public RedisManager redisManager()
	{
		RedisManager redisManager = new RedisManager();
		redisManager.setHost(host);
		redisManager.setPort(port);
		redisManager.setExpire(1800);// 配置缓存过期时间
		redisManager.setTimeout(timeout);
		redisManager.setPassword(password);
		return redisManager;
	}

	/**
	 * cacheManager 缓存 redis实现 使用的是shiro-reds开源插件
	 * 
	 * @return
	 */
	public RedisCacheManager cacheManager()
	{
		RedisCacheManager redisCacheManager = new RedisCacheManager();
		redisCacheManager.setRedisManager(redisManager());
		return redisCacheManager;
	}

	/**
	 * RedisSessionDAO shiro sessionDao层的实现 通过redis 使用的是shiro-redis开源插件
	 */
	@Bean
	public RedisSessionDAO redisSessionDAO()
	{
		RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
		redisSessionDAO.setRedisManager(redisManager());
		return redisSessionDAO;
	}

	/**
	 * Session Manager 使用的是shiro-redis开源插件
	 */
	@Bean
	public DefaultWebSessionManager sessionManager()
	{
		DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
		sessionManager.setSessionDAO(redisSessionDAO());
		return sessionManager;
	}

	/**
	 * 限制同一账号登录同时登录人数控制
	 * 
	 * @return
	 */
	public KickoutSessionControlFilter kickoutSessionControlFilter()
	{
		KickoutSessionControlFilter kickoutSessionControlFilter = new KickoutSessionControlFilter();
		// 使用cacheManager获取相应的cache来缓存用户登录的会话；用于保存用户—会话之间的关系的；
		// 这里我们还是用之前shiro使用的redisManager()实现的cacheManager()缓存管理
		// 也可以重新另写一个，重新配置缓存时间之类的自定义缓存属性
		kickoutSessionControlFilter.setCacheManager(cacheManager());
		// 用于根据会话ID，获取会话进行踢出操作的；
		kickoutSessionControlFilter.setSessionManager(sessionManager());
		// 是否踢出后来登录的，默认是false；即后者登录的用户踢出前者登录的用户；踢出顺序。
		kickoutSessionControlFilter.setKickoutAfter(false);
		// 同一个用户最大的会话数，默认1；比如2的意思是同一个用户允许最多同时两个人登录；
		kickoutSessionControlFilter.setMaxSession(1);
		// 被踢出后重定向到的地址；
		kickoutSessionControlFilter.setKickoutForwardUrl("/kickout");
		kickoutSessionControlFilter.setKickoutRedirectUrl("/kickout");
		return kickoutSessionControlFilter;
	}

	public String getHost()
	{
		return host;
	}

	public void setHost(String host)
	{
		this.host = host;
	}

	public int getPort()
	{
		return port;
	}

	public void setPort(int port)
	{
		this.port = port;
	}

	public int getTimeout()
	{
		return timeout;
	}

	public void setTimeout(int timeout)
	{
		this.timeout = timeout;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
	   return new PropertySourcesPlaceholderConfigurer();
	}

	/*
	 * @Bean public FilterRegistrationBean<DelegatingFilterProxy>
	 * delegatingFilterProxy() { FilterRegistrationBean<DelegatingFilterProxy>
	 * filterRegistrationBean = new
	 * FilterRegistrationBean<DelegatingFilterProxy>(); DelegatingFilterProxy
	 * proxy = new DelegatingFilterProxy();
	 * proxy.setTargetFilterLifecycle(true);
	 * proxy.setTargetBeanName("shiroFilter");
	 * filterRegistrationBean.setFilter(proxy); return filterRegistrationBean; }
	 */
}