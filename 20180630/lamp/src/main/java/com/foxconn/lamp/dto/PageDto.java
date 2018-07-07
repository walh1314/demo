package com.foxconn.lamp.dto;

import java.io.Serializable;

public class PageDto implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3615989775038414339L;
	private Integer currentPage;
	private Integer pageSize;
	public Integer getCurrentPage()
	{
		return currentPage;
	}
	public void setCurrentPage(Integer currentPage)
	{
		this.currentPage = currentPage;
	}
	public Integer getPageSize()
	{
		return pageSize;
	}
	public void setPageSize(Integer pageSize)
	{
		this.pageSize = pageSize;
	}

	
}
