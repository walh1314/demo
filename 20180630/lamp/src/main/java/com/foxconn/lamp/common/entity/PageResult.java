package com.foxconn.lamp.common.entity;

import com.github.pagehelper.PageInfo;

public class PageResult<T>
{
	private T rows;
	private Integer currentPage;
	private Integer pageSize;

	// 总条数
	private Long totalNum;
	// 是否有下一页
	private Integer isMore;
	// 总页数
	private Integer totalPage;

	@SuppressWarnings("unchecked")
	public PageResult(PageInfo<? extends Object> pageInfo)
	{
		this.rows = (T) pageInfo.getList();
		this.totalNum = pageInfo.getTotal();
		this.pageSize = pageInfo.getPageSize();
		this.totalPage = pageInfo.getPages();
	}
	
	public PageResult(T t)
	{
		this.rows = t;
	}

	public PageResult()
	{

	}

	public Long getTotalNum()
	{
		return totalNum;
	}

	public void setTotalNum(Long totalNum)
	{
		this.totalNum = totalNum;
	}

	public Integer getIsMore()
	{
		return isMore;
	}

	public void setIsMore(Integer isMore)
	{
		this.isMore = isMore;
	}

	public Integer getTotalPage()
	{
		return totalPage;
	}

	public void setTotalPage(Integer totalPage)
	{
		this.totalPage = totalPage;
	}

	public T getRows()
	{
		return rows;
	}

	public void setRows(T rows)
	{
		this.rows = rows;
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
