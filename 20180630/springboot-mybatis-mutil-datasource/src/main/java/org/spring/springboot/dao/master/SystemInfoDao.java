package org.spring.springboot.dao.master;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.spring.springboot.domain.SystemInfo;

/**
 * @author liupingan
 */
@Mapper
public interface SystemInfoDao {

    /**
     * 根据pid获取系统信息
     *
     * @param pid
     * @return
     */
	SystemInfo findByPid(@Param("pid") String pid);
}
