package com.foxconn.lamp.shiro.web.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.springframework.stereotype.Component;

@Component("customFormAuthenticationFilter")
public class CustomFormAuthenticationFilter extends FormAuthenticationFilter
{

	@Override
	protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request,
			ServletResponse response) throws Exception
	{
		// TODO Auto-generated method stub
		System.out.println("==================11111111111111===============");
		return super.onLoginSuccess(token, subject, request, response);
	}

	@Override
	protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request,
			ServletResponse response)
	{
		System.out.println();
		// TODO Auto-generated method stub
		System.out.println("==================22222222222222===============");
		return super.onLoginFailure(token, e, request, response);
	}

	
}
