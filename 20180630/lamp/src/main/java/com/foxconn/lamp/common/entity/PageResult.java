package com.foxconn.lamp.common.entity;

import java.util.List;

import com.baomidou.mybatisplus.plugins.Page;
import com.github.pagehelper.PageInfo;

public class PageResult<T>
{
	private List<T> rows;
	private Integer currentPage;
	private Integer pageSize;

	// 总条数
	private Long totalNum;
	// 是否有下一页
	private Integer isMore;
	// 总页数
	private Integer totalPage;

	public PageResult(PageInfo<T> pageInfo)
	{
		this.rows = pageInfo.getList();
		this.totalNum = pageInfo.getTotal();
		this.pageSize = pageInfo.getPageSize();
		this.totalPage = pageInfo.getPages();
	}

	public PageResult(Page<T> page)
	{
		this.currentPage = page.getCurrent();
		this.pageSize = page.getSize();
		this.totalNum = Long.valueOf(page.getTotal());
		this.rows = page.getRecords();
		this.totalPage = page.getPages();
	}

	public PageResult(List<T> t)
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

	public List<T> getRows()
	{
		return rows;
	}

	public void setRows(List<T> rows)
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
