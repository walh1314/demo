package com.foxconn.lamp.manager.domain;

import java.io.Serializable;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;


/**
 * <p>
 * 
 * </p>
 *
 * @author z77z
 * @since 2017-02-13
 */
@TableName("sys_user_role")
public class SysUserRole  extends Model<SysUserRole> {

	private static final long serialVersionUID = 2296810675938787305L;
	
	private String id;
    /**
     * 用户ID
     */
	private String uid;
    /**
     * 角色ID
     */
	private String rid;


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getRid() {
		return rid;
	}

	public void setRid(String rid) {
		this.rid = rid;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
