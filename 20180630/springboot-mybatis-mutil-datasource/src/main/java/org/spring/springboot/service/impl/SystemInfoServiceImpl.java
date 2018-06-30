package org.spring.springboot.service.impl;

import org.spring.springboot.dao.master.SystemInfoDao;
import org.spring.springboot.domain.SystemInfo;
import org.spring.springboot.service.SytemInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author liupingan
 */
@Service
public class SystemInfoServiceImpl implements SytemInfoService {

    @Autowired
    private SystemInfoDao systemInfoDao; // 主数据源

    @Override
    public SystemInfo findByPid(String pid) {
    	return systemInfoDao.findByPid(pid);
    }
}
