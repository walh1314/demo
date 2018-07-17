package com.foxconn.lamp.camera.domain;

import java.io.Serializable;
import java.util.Date;

public class CameraInfo implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 8233720988827130954L;

	/**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column camera_info.id
     *
     * @mbg.generated Thu Jul 05 17:22:55 CST 2018
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column camera_info.name
     *
     * @mbg.generated Thu Jul 05 17:22:55 CST 2018
     */
    private String name;
    
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column camera_info.serial
     *
     * @mbg.generated Thu Jul 05 17:22:55 CST 2018
     */
    private String serial;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column camera_info.device_id
     *
     * @mbg.generated Thu Jul 05 17:22:55 CST 2018
     */
    private String deviceId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column camera_info.mac_addr
     *
     * @mbg.generated Thu Jul 05 17:22:55 CST 2018
     */
    private String macAddr;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column camera_info.pub_net_addr
     *
     * @mbg.generated Thu Jul 05 17:22:55 CST 2018
     */
    private String pubNetAddr;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column camera_info.lan_net_addr
     *
     * @mbg.generated Thu Jul 05 17:22:55 CST 2018
     */
    private String lanNetAddr;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column camera_info.type
     *
     * @mbg.generated Thu Jul 05 17:22:55 CST 2018
     */
    private String type;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column camera_info.location_addr
     *
     * @mbg.generated Thu Jul 05 17:22:55 CST 2018
     */
    private String locationAddr;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column camera_info.status
     *
     * @mbg.generated Thu Jul 05 17:22:55 CST 2018
     */
    private Long status;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column camera_info.desc
     *
     * @mbg.generated Thu Jul 05 17:22:55 CST 2018
     */
    private String desc;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column camera_info.creater
     *
     * @mbg.generated Thu Jul 05 17:22:55 CST 2018
     */
    private String creater;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column camera_info.modifier
     *
     * @mbg.generated Thu Jul 05 17:22:55 CST 2018
     */
    private String modifier;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column camera_info.modify_time
     *
     * @mbg.generated Thu Jul 05 17:22:55 CST 2018
     */
    private Date modifyTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column camera_info.create_time
     *
     * @mbg.generated Thu Jul 05 17:22:55 CST 2018
     */
    private Date createTime;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column camera_info.id
     *
     * @return the value of camera_info.id
     *
     * @mbg.generated Thu Jul 05 17:22:55 CST 2018
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column camera_info.id
     *
     * @param id the value for camera_info.id
     *
     * @mbg.generated Thu Jul 05 17:22:55 CST 2018
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column camera_info.name
     *
     * @return the value of camera_info.name
     *
     * @mbg.generated Thu Jul 05 17:22:55 CST 2018
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column camera_info.name
     *
     * @param name the value for camera_info.name
     *
     * @mbg.generated Thu Jul 05 17:22:55 CST 2018
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column camera_info.serial
     *
     * @return the value of camera_info.serial
     *
     * @mbg.generated Thu Jul 05 17:22:55 CST 2018
     */
    public String getSerial() {
        return serial;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column camera_info.serial
     *
     * @param serial the value for camera_info.serial
     *
     * @mbg.generated Thu Jul 05 17:22:55 CST 2018
     */
    public void setSerial(String serial) {
        this.serial = serial == null ? null : serial.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column camera_info.device_id
     *
     * @return the value of camera_info.device_id
     *
     * @mbg.generated Thu Jul 05 17:22:55 CST 2018
     */
    public String getDeviceId() {
        return deviceId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column camera_info.device_id
     *
     * @param deviceId the value for camera_info.device_id
     *
     * @mbg.generated Thu Jul 05 17:22:55 CST 2018
     */
    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId == null ? null : deviceId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column camera_info.mac_addr
     *
     * @return the value of camera_info.mac_addr
     *
     * @mbg.generated Thu Jul 05 17:22:55 CST 2018
     */
    public String getMacAddr() {
        return macAddr;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column camera_info.mac_addr
     *
     * @param macAddr the value for camera_info.mac_addr
     *
     * @mbg.generated Thu Jul 05 17:22:55 CST 2018
     */
    public void setMacAddr(String macAddr) {
        this.macAddr = macAddr == null ? null : macAddr.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column camera_info.pub_net_addr
     *
     * @return the value of camera_info.pub_net_addr
     *
     * @mbg.generated Thu Jul 05 17:22:55 CST 2018
     */
    public String getPubNetAddr() {
        return pubNetAddr;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column camera_info.pub_net_addr
     *
     * @param pubNetAddr the value for camera_info.pub_net_addr
     *
     * @mbg.generated Thu Jul 05 17:22:55 CST 2018
     */
    public void setPubNetAddr(String pubNetAddr) {
        this.pubNetAddr = pubNetAddr == null ? null : pubNetAddr.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column camera_info.lan_net_addr
     *
     * @return the value of camera_info.lan_net_addr
     *
     * @mbg.generated Thu Jul 05 17:22:55 CST 2018
     */
    public String getLanNetAddr() {
        return lanNetAddr;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column camera_info.lan_net_addr
     *
     * @param lanNetAddr the value for camera_info.lan_net_addr
     *
     * @mbg.generated Thu Jul 05 17:22:55 CST 2018
     */
    public void setLanNetAddr(String lanNetAddr) {
        this.lanNetAddr = lanNetAddr == null ? null : lanNetAddr.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column camera_info.type
     *
     * @return the value of camera_info.type
     *
     * @mbg.generated Thu Jul 05 17:22:55 CST 2018
     */
    public String getType() {
        return type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column camera_info.type
     *
     * @param type the value for camera_info.type
     *
     * @mbg.generated Thu Jul 05 17:22:55 CST 2018
     */
    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column camera_info.location_addr
     *
     * @return the value of camera_info.location_addr
     *
     * @mbg.generated Thu Jul 05 17:22:55 CST 2018
     */
    public String getLocationAddr() {
        return locationAddr;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column camera_info.location_addr
     *
     * @param locationAddr the value for camera_info.location_addr
     *
     * @mbg.generated Thu Jul 05 17:22:55 CST 2018
     */
    public void setLocationAddr(String locationAddr) {
        this.locationAddr = locationAddr == null ? null : locationAddr.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column camera_info.status
     *
     * @return the value of camera_info.status
     *
     * @mbg.generated Thu Jul 05 17:22:55 CST 2018
     */
    public Long getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column camera_info.status
     *
     * @param status the value for camera_info.status
     *
     * @mbg.generated Thu Jul 05 17:22:55 CST 2018
     */
    public void setStatus(Long status) {
        this.status = status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column camera_info.desc
     *
     * @return the value of camera_info.desc
     *
     * @mbg.generated Thu Jul 05 17:22:55 CST 2018
     */
    public String getDesc() {
        return desc;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column camera_info.desc
     *
     * @param desc the value for camera_info.desc
     *
     * @mbg.generated Thu Jul 05 17:22:55 CST 2018
     */
    public void setDesc(String desc) {
        this.desc = desc == null ? null : desc.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column camera_info.creater
     *
     * @return the value of camera_info.creater
     *
     * @mbg.generated Thu Jul 05 17:22:55 CST 2018
     */
    public String getCreater() {
        return creater;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column camera_info.creater
     *
     * @param creater the value for camera_info.creater
     *
     * @mbg.generated Thu Jul 05 17:22:55 CST 2018
     */
    public void setCreater(String creater) {
        this.creater = creater == null ? null : creater.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column camera_info.modifier
     *
     * @return the value of camera_info.modifier
     *
     * @mbg.generated Thu Jul 05 17:22:55 CST 2018
     */
    public String getModifier() {
        return modifier;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column camera_info.modifier
     *
     * @param modifier the value for camera_info.modifier
     *
     * @mbg.generated Thu Jul 05 17:22:55 CST 2018
     */
    public void setModifier(String modifier) {
        this.modifier = modifier == null ? null : modifier.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column camera_info.modify_time
     *
     * @return the value of camera_info.modify_time
     *
     * @mbg.generated Thu Jul 05 17:22:55 CST 2018
     */
    public Date getModifyTime() {
        return modifyTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column camera_info.modify_time
     *
     * @param modifyTime the value for camera_info.modify_time
     *
     * @mbg.generated Thu Jul 05 17:22:55 CST 2018
     */
    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column camera_info.create_time
     *
     * @return the value of camera_info.create_time
     *
     * @mbg.generated Thu Jul 05 17:22:55 CST 2018
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column camera_info.create_time
     *
     * @param createTime the value for camera_info.create_time
     *
     * @mbg.generated Thu Jul 05 17:22:55 CST 2018
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}