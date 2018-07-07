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
@TableName("sys_role_permission")
public class SysRolePermission extends Model<SysRolePermission> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4419864886290187014L;
	private String id;
    /**
     * 角色ID
     */
	private String rid;
    /**
     * 权限ID
     */
	private String pid;


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRid() {
		return rid;
	}

	public void setRid(String rid) {
		this.rid = rid;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
