package org.spring.springboot.controller;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.spring.springboot.domain.SystemInfo;
import org.spring.springboot.dto.Ac2CheckData;
import org.spring.springboot.dto.Ac2CheckResult;
import org.spring.springboot.dto.Ac2LoginRedis;
import org.spring.springboot.dto.Ac2LoginResult;
import org.spring.springboot.service.SytemInfoService;
import org.spring.springboot.service.UserService;
import org.spring.springboot.utils.Encrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Spring Boot HelloWorld 案例
 *
 * Created by bysocket on 16/4/26.
 */
@RestController(value = "")
public class AC2Controller
{

	@Autowired
	private UserService userService;

	@Autowired
	private SytemInfoService sytemInfoService;
	
	
	@Autowired
	RedisTemplate<Object, Object> redisTemplate;
	
	@Resource(name = "redisTemplate")
	ValueOperations<Object, Object> valOpsObj;


	@RequestMapping(value = "/api/ac/login", method =
	{ RequestMethod.POST, RequestMethod.GET },produces="application/json;charset=UTF-8")
	public Ac2LoginResult acLogin(HttpServletRequest request, HttpServletResponse response)
	{
		Ac2LoginResult result = new Ac2LoginResult();
		result.setStatus("False");
		boolean check = false;

		String userName = request.getParameter("username");
		String password = request.getParameter("password");

		String pid = request.getParameter("PID");

		if (!StringUtils.isEmpty(userName) && !StringUtils.isEmpty(pid) && !StringUtils.isEmpty(password))
		{
			// 验证pid
			SystemInfo systemInfo = sytemInfoService.findByPid(pid);
			if (systemInfo == null || systemInfo.getId() == null)
			{
				return result;
			}
			if (userService.login(userName, new String(Base64.getDecoder().decode(password),StandardCharsets.UTF_8)))
			{
				check = true;
			}
		}
		
		if (check)
		{
			String token = Encrypt.MD5(pid + "-" + System.nanoTime() + "-" + userName);// 用户名，pid，加时间作为token
			
			Ac2LoginRedis value = new Ac2LoginRedis() ;
			value.setPid(pid);
			value.setToken(token);
			valOpsObj.set("TOKEN="+token+";PID="+pid, userName);
			result.setStatus("True");
			result.settoken(token);
		}
		return result;
	}

	// { "TOKEN": "f5db3a14-edcc-4693-bc48-ed358771816c", "status": "True" }

	@RequestMapping(value = "/api/ac/check_token", method =
	{ RequestMethod.POST, RequestMethod.GET })
	public Ac2CheckResult checkToken(HttpServletRequest request, HttpServletResponse response)
	{
		Ac2CheckResult result = new Ac2CheckResult();
		result.setStatus("False");
		
		boolean check = false;

		String csrToken = request.getHeader("X-CSRFToken");
		if(StringUtils.isEmpty(csrToken)){
			return result;
		} else if (redisTemplate.hasKey(csrToken)){
			check = true;
			/*String[] csrTokenArrays = csrToken.split(";");
			if(csrTokenArrays != null && csrTokenArrays.length == 2){
				String token = csrTokenArrays[0].split("=")[0];
				String pid = csrTokenArrays[1].split("=")[0];
				
			}*/
		}
		
		if (check)
		{
			result.setStatus("True");
			Ac2CheckData data = new Ac2CheckData();
			data.setUserName(valOpsObj.get(csrToken).toString());
			result.setData(data);
		}
		
		return result;
	}
}
