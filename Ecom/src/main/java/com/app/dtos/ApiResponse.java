package com.app.dtos;

import java.util.List;

public class ApiResponse<T> {
	
	private List<T> data;
	private PageInfo pageInfo;

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

	public PageInfo getPageInfo() {
		return pageInfo;
	}

	public void setPageInfo(PageInfo pageInfo) {
		this.pageInfo = pageInfo;
	}
}
