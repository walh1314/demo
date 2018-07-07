package com.foxconn.lamp.test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LogbackTest
{
	    public static void main(String[] args) {
	    	log.trace("logback的--tra1111ce日志--输出了");
	        log.debug("logback的--debug日志--输出了");
	        log.info("logback的--info日志--输出了");
	        log.warn("logback的--warn日志--输出了");
	        log.error("logback的--error日志--输出了");
	    }
}
