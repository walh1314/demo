package com.foxconn.lamp.log.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.foxconn.lamp.log.domain.IpCameraLog;

public interface IpCameraLogRepository extends  MongoRepository<IpCameraLog,String> 
{

}
