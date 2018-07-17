package com.foxconn.lamp.device.domain;

import java.io.Serializable;
import java.util.Date;

public class CameraLampReal implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4783557060860937109L;

	/**
	 *
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column camera_lamp_real.id
	 *
	 * @mbg.generated Thu Jul 05 17:35:25 CST 2018
	 */
	private Integer id;

	/**
	 *
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column camera_lamp_real.device_id
	 *
	 * @mbg.generated Thu Jul 05 17:35:25 CST 2018
	 */
	private String deviceId;

	/**
	 *
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column camera_lamp_real.lamp_serial
	 *
	 * @mbg.generated Thu Jul 05 17:35:25 CST 2018
	 */
	private String lampSerial;

	/**
	 *
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column camera_lamp_real.points
	 *
	 * @mbg.generated Thu Jul 05 17:35:25 CST 2018
	 */
	private String points;

	/**
	 *
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column camera_lamp_real.status
	 *
	 * @mbg.generated Thu Jul 05 17:35:25 CST 2018
	 */
	private Long status;

	/**
	 *
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column camera_lamp_real.desc
	 *
	 * @mbg.generated Thu Jul 05 17:35:25 CST 2018
	 */
	private String desc;

	/**
	 *
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column camera_lamp_real.creater
	 *
	 * @mbg.generated Thu Jul 05 17:35:25 CST 2018
	 */
	private String creater;

	/**
	 *
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column camera_lamp_real.modifier
	 *
	 * @mbg.generated Thu Jul 05 17:35:25 CST 2018
	 */
	private String modifier;

	/**
	 *
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column camera_lamp_real.modify_time
	 *
	 * @mbg.generated Thu Jul 05 17:35:25 CST 2018
	 */
	private Date modifyTime;

	/**
	 *
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column camera_lamp_real.create_time
	 *
	 * @mbg.generated Thu Jul 05 17:35:25 CST 2018
	 */
	private Date createTime;

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column camera_lamp_real.id
	 *
	 * @return the value of camera_lamp_real.id
	 *
	 * @mbg.generated Thu Jul 05 17:35:25 CST 2018
	 */
	public Integer getId()
	{
		return id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column camera_lamp_real.id
	 *
	 * @param id
	 *            the value for camera_lamp_real.id
	 *
	 * @mbg.generated Thu Jul 05 17:35:25 CST 2018
	 */
	public void setId(Integer id)
	{
		this.id = id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column camera_lamp_real.device_id
	 *
	 * @return the value of camera_lamp_real.device_id
	 *
	 * @mbg.generated Thu Jul 05 17:35:25 CST 2018
	 */
	public String getDeviceId()
	{
		return deviceId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column camera_lamp_real.device_id
	 *
	 * @param deviceId
	 *            the value for camera_lamp_real.device_id
	 *
	 * @mbg.generated Thu Jul 05 17:35:25 CST 2018
	 */
	public void setDeviceId(String deviceId)
	{
		this.deviceId = deviceId == null ? null : deviceId.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column camera_lamp_real.lamp_serial
	 *
	 * @return the value of camera_lamp_real.lamp_serial
	 *
	 * @mbg.generated Thu Jul 05 17:35:25 CST 2018
	 */
	public String getLampSerial()
	{
		return lampSerial;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column camera_lamp_real.lamp_serial
	 *
	 * @param lampSerial
	 *            the value for camera_lamp_real.lamp_serial
	 *
	 * @mbg.generated Thu Jul 05 17:35:25 CST 2018
	 */
	public void setLampSerial(String lampSerial)
	{
		this.lampSerial = lampSerial == null ? null : lampSerial.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column camera_lamp_real.points
	 *
	 * @return the value of camera_lamp_real.points
	 *
	 * @mbg.generated Thu Jul 05 17:35:25 CST 2018
	 */
	public String getPoints()
	{
		return points;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column camera_lamp_real.points
	 *
	 * @param points
	 *            the value for camera_lamp_real.points
	 *
	 * @mbg.generated Thu Jul 05 17:35:25 CST 2018
	 */
	public void setPoints(String points)
	{
		this.points = points == null ? null : points.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column camera_lamp_real.status
	 *
	 * @return the value of camera_lamp_real.status
	 *
	 * @mbg.generated Thu Jul 05 17:35:25 CST 2018
	 */
	public Long getStatus()
	{
		return status;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column camera_lamp_real.status
	 *
	 * @param status
	 *            the value for camera_lamp_real.status
	 *
	 * @mbg.generated Thu Jul 05 17:35:25 CST 2018
	 */
	public void setStatus(Long status)
	{
		this.status = status;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column camera_lamp_real.desc
	 *
	 * @return the value of camera_lamp_real.desc
	 *
	 * @mbg.generated Thu Jul 05 17:35:25 CST 2018
	 */
	public String getDesc()
	{
		return desc;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column camera_lamp_real.desc
	 *
	 * @param desc
	 *            the value for camera_lamp_real.desc
	 *
	 * @mbg.generated Thu Jul 05 17:35:25 CST 2018
	 */
	public void setDesc(String desc)
	{
		this.desc = desc == null ? null : desc.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column camera_lamp_real.creater
	 *
	 * @return the value of camera_lamp_real.creater
	 *
	 * @mbg.generated Thu Jul 05 17:35:25 CST 2018
	 */
	public String getCreater()
	{
		return creater;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column camera_lamp_real.creater
	 *
	 * @param creater
	 *            the value for camera_lamp_real.creater
	 *
	 * @mbg.generated Thu Jul 05 17:35:25 CST 2018
	 */
	public void setCreater(String creater)
	{
		this.creater = creater == null ? null : creater.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column camera_lamp_real.modifier
	 *
	 * @return the value of camera_lamp_real.modifier
	 *
	 * @mbg.generated Thu Jul 05 17:35:25 CST 2018
	 */
	public String getModifier()
	{
		return modifier;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column camera_lamp_real.modifier
	 *
	 * @param modifier
	 *            the value for camera_lamp_real.modifier
	 *
	 * @mbg.generated Thu Jul 05 17:35:25 CST 2018
	 */
	public void setModifier(String modifier)
	{
		this.modifier = modifier == null ? null : modifier.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column camera_lamp_real.modify_time
	 *
	 * @return the value of camera_lamp_real.modify_time
	 *
	 * @mbg.generated Thu Jul 05 17:35:25 CST 2018
	 */
	public Date getModifyTime()
	{
		return modifyTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column camera_lamp_real.modify_time
	 *
	 * @param modifyTime
	 *            the value for camera_lamp_real.modify_time
	 *
	 * @mbg.generated Thu Jul 05 17:35:25 CST 2018
	 */
	public void setModifyTime(Date modifyTime)
	{
		this.modifyTime = modifyTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column camera_lamp_real.create_time
	 *
	 * @return the value of camera_lamp_real.create_time
	 *
	 * @mbg.generated Thu Jul 05 17:35:25 CST 2018
	 */
	public Date getCreateTime()
	{
		return createTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column camera_lamp_real.create_time
	 *
	 * @param createTime
	 *            the value for camera_lamp_real.create_time
	 *
	 * @mbg.generated Thu Jul 05 17:35:25 CST 2018
	 */
	public void setCreateTime(Date createTime)
	{
		this.createTime = createTime;
	}
}