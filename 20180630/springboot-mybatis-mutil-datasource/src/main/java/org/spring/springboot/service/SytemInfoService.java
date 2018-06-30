package org.spring.springboot.service;

import org.spring.springboot.domain.SystemInfo;

/**
 * @author liupingan
 */
public interface SytemInfoService {

    SystemInfo findByPid(String pid);
}
