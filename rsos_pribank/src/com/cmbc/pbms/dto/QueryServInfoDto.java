package com.cmbc.pbms.dto;

import java.io.Serializable;

import rsos.framework.db.PageDto;

public class QueryServInfoDto implements Serializable {
	private static final long serialVersionUID = 1L;
	private PageDto pageDto;
	private String serId;
	private String serName;
	public PageDto getPageDto() {
		return pageDto;
	}
	public void setPageDto(PageDto pageDto) {
		this.pageDto = pageDto;
	}
	public String getSerId() {
		return serId;
	}
	public void setSerId(String serId) {
		this.serId = serId;
	}
	public String getSerName() {
		return serName;
	}
	public void setSerName(String serName) {
		this.serName = serName;
	}
		
}
