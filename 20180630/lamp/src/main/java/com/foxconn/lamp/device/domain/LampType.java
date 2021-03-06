package com.foxconn.lamp.device.domain;

import java.io.Serializable;
import java.util.Date;

public class LampType implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7147927347266223072L;

	/**
	 *
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column lamp_type.id
	 *
	 * @mbg.generated Thu Jul 05 17:26:55 CST 2018
	 */
	private Integer id;

	/**
	 *
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column lamp_type.code
	 *
	 * @mbg.generated Thu Jul 05 17:26:55 CST 2018
	 */
	private String code;

	/**
	 *
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column lamp_type.name
	 *
	 * @mbg.generated Thu Jul 05 17:26:55 CST 2018
	 */
	private String name;

	/**
	 *
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column lamp_type.order
	 *
	 * @mbg.generated Thu Jul 05 17:26:55 CST 2018
	 */
	private Integer order;

	/**
	 *
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column lamp_type.desc
	 *
	 * @mbg.generated Thu Jul 05 17:26:55 CST 2018
	 */
	private String desc;

	/**
	 *
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column lamp_type.status
	 *
	 * @mbg.generated Thu Jul 05 17:26:55 CST 2018
	 */
	private Long status;

	/**
	 *
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column lamp_type.creater
	 *
	 * @mbg.generated Thu Jul 05 17:26:55 CST 2018
	 */
	private String creater;

	/**
	 *
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column lamp_type.modifier
	 *
	 * @mbg.generated Thu Jul 05 17:26:55 CST 2018
	 */
	private String modifier;

	/**
	 *
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column lamp_type.modify_time
	 *
	 * @mbg.generated Thu Jul 05 17:26:55 CST 2018
	 */
	private Date modifyTime;

	/**
	 *
	 * This field was generated by MyBatis Generator. This field corresponds to
	 * the database column lamp_type.create_time
	 *
	 * @mbg.generated Thu Jul 05 17:26:55 CST 2018
	 */
	private Date createTime;

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column lamp_type.id
	 *
	 * @return the value of lamp_type.id
	 *
	 * @mbg.generated Thu Jul 05 17:26:55 CST 2018
	 */
	public Integer getId()
	{
		return id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column lamp_type.id
	 *
	 * @param id
	 *            the value for lamp_type.id
	 *
	 * @mbg.generated Thu Jul 05 17:26:55 CST 2018
	 */
	public void setId(Integer id)
	{
		this.id = id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column lamp_type.code
	 *
	 * @return the value of lamp_type.code
	 *
	 * @mbg.generated Thu Jul 05 17:26:55 CST 2018
	 */
	public String getCode()
	{
		return code;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column lamp_type.code
	 *
	 * @param code
	 *            the value for lamp_type.code
	 *
	 * @mbg.generated Thu Jul 05 17:26:55 CST 2018
	 */
	public void setCode(String code)
	{
		this.code = code == null ? null : code.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column lamp_type.name
	 *
	 * @return the value of lamp_type.name
	 *
	 * @mbg.generated Thu Jul 05 17:26:55 CST 2018
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column lamp_type.name
	 *
	 * @param name
	 *            the value for lamp_type.name
	 *
	 * @mbg.generated Thu Jul 05 17:26:55 CST 2018
	 */
	public void setName(String name)
	{
		this.name = name == null ? null : name.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column lamp_type.order
	 *
	 * @return the value of lamp_type.order
	 *
	 * @mbg.generated Thu Jul 05 17:26:55 CST 2018
	 */
	public Integer getOrder()
	{
		return order;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column lamp_type.order
	 *
	 * @param order
	 *            the value for lamp_type.order
	 *
	 * @mbg.generated Thu Jul 05 17:26:55 CST 2018
	 */
	public void setOrder(Integer order)
	{
		this.order = order;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column lamp_type.desc
	 *
	 * @return the value of lamp_type.desc
	 *
	 * @mbg.generated Thu Jul 05 17:26:55 CST 2018
	 */
	public String getDesc()
	{
		return desc;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column lamp_type.desc
	 *
	 * @param desc
	 *            the value for lamp_type.desc
	 *
	 * @mbg.generated Thu Jul 05 17:26:55 CST 2018
	 */
	public void setDesc(String desc)
	{
		this.desc = desc == null ? null : desc.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column lamp_type.status
	 *
	 * @return the value of lamp_type.status
	 *
	 * @mbg.generated Thu Jul 05 17:26:55 CST 2018
	 */
	public Long getStatus()
	{
		return status;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column lamp_type.status
	 *
	 * @param status
	 *            the value for lamp_type.status
	 *
	 * @mbg.generated Thu Jul 05 17:26:55 CST 2018
	 */
	public void setStatus(Long status)
	{
		this.status = status;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column lamp_type.creater
	 *
	 * @return the value of lamp_type.creater
	 *
	 * @mbg.generated Thu Jul 05 17:26:55 CST 2018
	 */
	public String getCreater()
	{
		return creater;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column lamp_type.creater
	 *
	 * @param creater
	 *            the value for lamp_type.creater
	 *
	 * @mbg.generated Thu Jul 05 17:26:55 CST 2018
	 */
	public void setCreater(String creater)
	{
		this.creater = creater == null ? null : creater.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column lamp_type.modifier
	 *
	 * @return the value of lamp_type.modifier
	 *
	 * @mbg.generated Thu Jul 05 17:26:55 CST 2018
	 */
	public String getModifier()
	{
		return modifier;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column lamp_type.modifier
	 *
	 * @param modifier
	 *            the value for lamp_type.modifier
	 *
	 * @mbg.generated Thu Jul 05 17:26:55 CST 2018
	 */
	public void setModifier(String modifier)
	{
		this.modifier = modifier == null ? null : modifier.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column lamp_type.modify_time
	 *
	 * @return the value of lamp_type.modify_time
	 *
	 * @mbg.generated Thu Jul 05 17:26:55 CST 2018
	 */
	public Date getModifyTime()
	{
		return modifyTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column lamp_type.modify_time
	 *
	 * @param modifyTime
	 *            the value for lamp_type.modify_time
	 *
	 * @mbg.generated Thu Jul 05 17:26:55 CST 2018
	 */
	public void setModifyTime(Date modifyTime)
	{
		this.modifyTime = modifyTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column lamp_type.create_time
	 *
	 * @return the value of lamp_type.create_time
	 *
	 * @mbg.generated Thu Jul 05 17:26:55 CST 2018
	 */
	public Date getCreateTime()
	{
		return createTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column lamp_type.create_time
	 *
	 * @param createTime
	 *            the value for lamp_type.create_time
	 *
	 * @mbg.generated Thu Jul 05 17:26:55 CST 2018
	 */
	public void setCreateTime(Date createTime)
	{
		this.createTime = createTime;
	}
}