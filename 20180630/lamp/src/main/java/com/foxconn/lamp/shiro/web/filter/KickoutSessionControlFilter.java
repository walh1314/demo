package com.foxconn.lamp.shiro.web.filter;

import java.io.IOException;
import java.io.Serializable;
import java.util.Deque;
import java.util.LinkedList;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.DefaultSessionKey;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import com.foxconn.lamp.common.exception.BaseException;
import com.foxconn.lamp.manager.domain.SysUser;

import lombok.extern.slf4j.Slf4j;

/**
 * @author liupingan
 * 1.读取当前登录用户名，获取在缓存中的sessionId队列
 * 2.判断队列的长度，大于最大登录限制的时候，按踢出规则  将之前的sessionId中的session域中存入kickout：true，并更新队列缓存
 * 3.判断当前登录的session域中的kickout如果为true， 想将其做退出登录处理，然后再重定向到踢出登录提示页面
 */
@Slf4j
@Configuration
public class KickoutSessionControlFilter extends AccessControlFilter
{

	@Value("${kickout.redirect.url}")
	private String kickoutRedirectUrl; // 踢出后到的地址
	
	@Value("${kickout.forward.url}")
	private String kickoutForwardUrl; // 踢出后到的地址
	
	
	@Value("${kickout.login.fail.forward.url}")
	private String kickoutLoginFailForwardUrl; // 登录Forward地址
	
	@Value("${kickout.login.fail.redirect.url}")
	private String kickoutLoginFailRedirectUrl; // 登录redirect地址
	
	@Value("${kickout.after}")
	private boolean kickoutAfter = false; // 踢出之前登录的/之后登录的用户 默认踢出之前登录的用户
	
	@Value("${kickout.max.session}")
	private int maxSession = 1; // 同一个帐号最大会话数 默认1

	private SessionManager sessionManager;

	private Cache<String, Deque<Serializable>> cache;

	public String getKickoutLoginFailForwardUrl()
	{
		return kickoutLoginFailForwardUrl;
	}

	public void setKickoutLoginFailForwardUrl(String kickoutLoginFailForwardUrl)
	{
		this.kickoutLoginFailForwardUrl = kickoutLoginFailForwardUrl;
	}

	public String getKickoutLoginFailRedirectUrl()
	{
		return kickoutLoginFailRedirectUrl;
	}

	public void setKickoutLoginFailRedirectUrl(String kickoutLoginFailRedirectUrl)
	{
		this.kickoutLoginFailRedirectUrl = kickoutLoginFailRedirectUrl;
	}

	public boolean isKickoutAfter()
	{
		return kickoutAfter;
	}

	public int getMaxSession()
	{
		return maxSession;
	}

	public SessionManager getSessionManager()
	{
		return sessionManager;
	}

	public String getKickoutRedirectUrl()
	{
		return kickoutRedirectUrl;
	}

	public void setKickoutRedirectUrl(String kickoutRedirectUrl)
	{
		this.kickoutRedirectUrl = kickoutRedirectUrl;
	}

	public String getKickoutForwardUrl()
	{
		return kickoutForwardUrl;
	}

	public void setKickoutForwardUrl(String kickoutForwardUrl)
	{
		this.kickoutForwardUrl = kickoutForwardUrl;
	}

	public void setKickoutAfter(boolean kickoutAfter)
	{
		this.kickoutAfter = kickoutAfter;
	}

	public void setMaxSession(int maxSession)
	{
		this.maxSession = maxSession;
	}

	public void setSessionManager(SessionManager sessionManager)
	{
		this.sessionManager = sessionManager;
	}

	// 设置Cache的key的前缀
	public void setCacheManager(CacheManager cacheManager)
	{
		this.cache = cacheManager.getCache("shiro_redis_cache");
	}

	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)
			throws Exception
	{
		return false;
	}

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception
	{
		Subject subject = getSubject(request, response);
		if (!subject.isAuthenticated() && !subject.isRemembered())
		{
			HttpServletRequest httpRequest = (HttpServletRequest) request;
			if(this.getLoginUrl().endsWith(httpRequest.getRequestURI().substring(httpRequest.getContextPath().length()))){
				return true;
			}
			saveRequestAndRedirectToLogin(request,response);
			return false;
		}

		Session session = subject.getSession();
		SysUser user = (SysUser) subject.getPrincipal();
		String username = user.getName();
		Serializable sessionId = session.getId();

		// 读取缓存 没有就存入
		Deque<Serializable> deque = cache.get(username);

		// 如果此用户没有session队列，也就是还没有登录过，缓存中没有
		// 就new一个空队列，不然deque对象为空，会报空指针
		if (deque == null)
		{
			deque = new LinkedList<Serializable>();
		}

		// 如果队列里没有此sessionId，且用户没有被踢出；放入队列
		if (!deque.contains(sessionId) && session.getAttribute("kickout") == null)
		{
			// 将sessionId存入队列
			deque.push(sessionId);
			// 将用户的sessionId队列缓存
			cache.put(username, deque);
		}
		// 如果队列里的sessionId数超出最大会话数，开始踢人
		while (deque.size() > maxSession)
		{
			Serializable kickoutSessionId = null;
			if (kickoutAfter)
			{ // 如果踢出后者
				kickoutSessionId = deque.removeFirst();
				// 踢出后再更新下缓存队列
				cache.put(username, deque);
			} else
			{ // 否则踢出前者
				kickoutSessionId = deque.removeLast();
				// 踢出后再更新下缓存队列
				cache.put(username, deque);
			}
			try
			{
				// 获取被踢出的sessionId的session对象
				Session kickoutSession = sessionManager.getSession(new DefaultSessionKey(kickoutSessionId));
				if (kickoutSession != null)
				{
					// 设置会话的kickout属性表示踢出了
					kickoutSession.setAttribute("kickout", true);
				}
			} catch (Exception e)
			{// ignore exception
				log.error(e.getMessage());
			}
		}

		// 如果被踢出了，直接退出，重定向到踢出后的地址
		if ((Boolean) session.getAttribute("kickout") != null && (Boolean) session.getAttribute("kickout") == true)
		{
			// 会话被踢出了
			try
			{
				// 退出登录
				subject.logout();
			} catch (Exception e)
			{ // ignore
				log.error(e.getMessage());
			}
			saveRequest(request);
			// 判断是不是Ajax请求
			if ("XMLHttpRequest".equalsIgnoreCase(((HttpServletRequest) request).getHeader("X-Requested-With")))
			{
				request.getRequestDispatcher(kickoutForwardUrl).forward(request, response);
			} else
			{
				WebUtils.issueRedirect(request, response, kickoutRedirectUrl);
			}
			return false;
		}
		return true;
	}

	@Override
	protected void saveRequestAndRedirectToLogin(ServletRequest request, ServletResponse response) throws IOException
	{
		// TODO Auto-generated method stub
		
		 saveRequest(request);
		// 如果没有登录，直接进行之后的流程
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		String isRedirect = httpRequest.getParameter("isRedirect");
		
		if(this.getLoginUrl().endsWith(httpRequest.getRequestURI().substring(httpRequest.getContextPath().length()))){
			//super.saveRequestAndRedirectToLogin(request, response);
			return;
		}
		if("true".equalsIgnoreCase(isRedirect)){
			HttpServletResponse httpResponse = (HttpServletResponse) request;
			httpResponse.sendRedirect(kickoutLoginFailRedirectUrl);
		} else {
			try
			{
				httpRequest.getRequestDispatcher(kickoutLoginFailForwardUrl).forward(request, response);
			} catch (ServletException e)
			{
				log.error(e.getMessage());
				throw new BaseException("-1",e) ;
			}
		}
		 
	}
}
