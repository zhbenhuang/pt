package com.cmbc.pbms.dto;

import java.io.Serializable;

import rsos.framework.db.PageDto;

public class QueryApproveInfoDto implements Serializable {
	private static final long serialVersionUID = 1L;
	private PageDto pageDto;
	private String apprId;
	private String apprType;
	private String apprStatus;
	private String applyUserId;
	
	public PageDto getPageDto() {
		return pageDto;
	}
	public void setPageDto(PageDto pageDto) {
		this.pageDto = pageDto;
	}
	public String getApprType() {
		return apprType;
	}
	public void setApprType(String apprType) {
		this.apprType = apprType;
	}
	public String getApprId() {
		return apprId;
	}
	public void setApprId(String apprId) {
		this.apprId = apprId;
	}
	public String getApprStatus() {
		return apprStatus;
	}
	public void setApprStatus(String apprStatus) {
		this.apprStatus = apprStatus;
	}
	public String getApplyUserId() {
		return applyUserId;
	}
	public void setApplyUserId(String applyUserId) {
		this.applyUserId = applyUserId;
	}
		
}
