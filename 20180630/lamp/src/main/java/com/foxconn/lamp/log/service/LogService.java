package com.foxconn.lamp.log.service;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.foxconn.lamp.log.MethodLog;
import com.foxconn.lamp.log.domain.Syslog;
import com.foxconn.lamp.log.mapper.OperateLogMapper;
import com.foxconn.lamp.manager.domain.SysUser;


/**
 * 日志切面实现
 */

@Component
@Aspect
public class LogService {

    @Autowired
    private OperateLogMapper operateLogMapper;

    public LogService() {
        System.out.println("Aop");
    }

    /**
     * 切点
     */
    @Pointcut("@annotation(org.triber.portal.service.logAop.MethodLog)")
    public void methodCachePointcut() { }


    /**
     * 切面
     *
     * @param point
     * @return
     * @throws Throwable
     */
    @Around("methodCachePointcut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes()).getRequest();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss E");
        Calendar ca = Calendar.getInstance();
        String operDate = df.format(ca.getTime());
        String ip = getIp(request);
        SysUser sysUser = (SysUser) request.getSession().getAttribute("currentUser");
        String loginName;
        if (sysUser != null) {
            loginName = sysUser.getName();
        } else {
            loginName = "匿名用户";
        }
        String methodRemark = getMthodRemark(point);
        String methodName = point.getSignature().getName();
        String packages = point.getThis().getClass().getName();
        if (packages.indexOf("$$EnhancerByCGLIB$$") > -1) { // 如果是CGLIB动态生成的类
            try {
                packages = packages.substring(0, packages.indexOf("$$"));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        Object[] method_param = null;

        Object object;
        try {
            method_param = point.getArgs(); //获取方法参数
            object = point.proceed();
        } catch (Exception e) {
            throw e;
        }
        Syslog sysLog = new Syslog();
        sysLog.setLoginName(loginName);
        sysLog.setIpAddress(ip);
        sysLog.setMethodName(packages + "." + methodName);
        sysLog.setMethodRemark(methodRemark);
        sysLog.setOptDate(operDate);

        /**
         * 空参数
         */
        if("".equals(methodRemark) || null == methodRemark){
            sysLog.setOperatingcontent("操作参数: " + method_param[0]);
        }

        operateLogMapper.insert(sysLog);
        return object;

    }

    /**
     * 方法异常时调用
     *
     * @param ex
     */
    public void afterThrowing(Exception ex) {
        System.out.println("afterThrowing");
        System.out.println(ex);
    }

    /**
     * 获取方法中的中文备注
     *
     * @param joinPoint
     * @return
     * @throws Exception
     */
    public static String getMthodRemark(ProceedingJoinPoint joinPoint) throws Exception {

        String targetName = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        Object[] arguments = joinPoint.getArgs();

        Class<? extends Object> targetClass = Class.forName(targetName);
        Method[] method = targetClass.getMethods();
        String methode = "";
        for (Method m : method) {
            if (m.getName().equals(methodName)) {
                Class<? extends Object>[] tmpCs = m.getParameterTypes();
                if (tmpCs.length == arguments.length) {
                    MethodLog methodCache = m.getAnnotation(MethodLog.class);
                    if (methodCache != null) {
                        methode = methodCache.remark();
                    }
                    break;
                }
            }
        }
        return methode;
    }

    /**
     * 获取请求ip
     *
     * @param request
     * @return
     */
    public static String getIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (!StringUtils.isEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
            int index = ip.indexOf(",");
            if (index != -1) {
                return ip.equals("0:0:0:0:0:0:0:1")?"127.0.0.1":ip.substring(0, index);
            } else {
                return ip.equals("0:0:0:0:0:0:0:1")?"127.0.0.1":ip;
            }
        }
        ip = request.getHeader("X-Real-IP");
        if (!StringUtils.isEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
            return ip;
        }
        return request.getRemoteAddr().equals("0:0:0:0:0:0:0:1")?"127.0.0.1":ip;
    }


}