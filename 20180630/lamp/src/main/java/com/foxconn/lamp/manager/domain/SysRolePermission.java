package com.foxconn.lamp.manager.domain;



import java.io.Serializable;


public class SysRolePermission implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4419864886290187014L;
	private Integer id;
    /**
     * 角色ID
     */
	private String rid;
    /**
     * 权限ID
     */
	private String pid;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
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
}
