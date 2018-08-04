package com.foxconn.lamp.vo;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.toolkit.StringUtils;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author liupingan
 */
@Slf4j
@Setter
@Getter
public class MarkLampsVo implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1790780492798256420L;

	private String id;
	
	private String name;
	
	private String deviceId;

	private String maintainer;

	private String type;

	private String desc;
	
	private Long status;

	@JSONField(name = "sn")
	private String serail;

	private List<PointVo> points;

	@JSONField(serialize = false)
	private String pointsString;// 新添加的字段
	
	private List<Integer> threshold;// 新添加的字段
	
	@JSONField(serialize = false)
	private String thresholdString;// 新添加的字段
	
	private Long width;
	
	private Long height;

	public void setPointsString(String pointsString)
	{
		this.pointsString = pointsString;
        try {  
            if(pointsString != null && StringUtils.isNotEmpty(pointsString)){
            	 List<PointVo> list = JSONArray.parseArray(pointsString,PointVo.class);  
                setPoints(list);//调用setStar方法  
            }
        } catch (Exception e) {  
           log.error(e.getMessage());
        } 
	}
	
	public void setThresholdString(String thresholdString)
	{
		this.thresholdString = thresholdString;
        try {  
            if(thresholdString != null && StringUtils.isNotEmpty(thresholdString)){
            	 List<Integer> list = JSONArray.parseArray(thresholdString,Integer.class);  
            	 setThreshold(list);//调用setStar方法  
            }
        } catch (Exception e) {  
           log.error(e.getMessage());
        } 
	}

}
