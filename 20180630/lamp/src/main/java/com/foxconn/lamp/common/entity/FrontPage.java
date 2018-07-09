package com.foxconn.lamp.common.entity;

import com.baomidou.mybatisplus.plugins.Page;

public class FrontPage<T>
{
	private Integer currentPage;
	private Integer pageSize;

	// 获取mybatisPlus封装的Page对象
	public Page<T> getPagePlus()
	{
		Page<T> pagePlus = new Page<T>();
		pagePlus.setCurrent(this.currentPage);
		pagePlus.setSize(this.pageSize);
		return pagePlus;
	}

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
