package com.foxconn.lamp.device.mapper;

import java.util.List;
import java.util.Map;

import com.foxconn.lamp.device.domain.LampInfo;

public interface LampInfoMapper {

    int deleteById(Integer id);
    
    int insert(LampInfo record);

    List<LampInfo> selectByMap(Map<String,Object> map);

    LampInfo selectById(Integer id);
    
    LampInfo selectBySerail(String Serail);
    
    List<LampInfo> selectByDeviceId(String Serail);
   
    int updateById(LampInfo record);
    
    int updateBySerail(LampInfo record);
    
    int batchReplaceInsert(List<LampInfo> list);

}