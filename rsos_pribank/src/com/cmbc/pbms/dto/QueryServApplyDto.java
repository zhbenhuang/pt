package com.cmbc.pbms.dto;

import java.io.Serializable;

import rsos.framework.db.PageDto;

public class QueryServApplyDto implements Serializable {
	private static final long serialVersionUID = 1L;
	private PageDto pageDto;
	private String serNo;
	private String serId;
	private String apprId;
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
	public String getSerNo() {
		return serNo;
	}
	public void setSerNo(String serNo) {
		this.serNo = serNo;
	}
	public String getApprId() {
		return apprId;
	}
	public void setApprId(String apprId) {
		this.apprId = apprId;
	}
		
}
