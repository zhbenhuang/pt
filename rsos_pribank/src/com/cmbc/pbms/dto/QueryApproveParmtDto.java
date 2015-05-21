package com.cmbc.pbms.dto;

import java.io.Serializable;

import rsos.framework.db.PageDto;

public class QueryApproveParmtDto implements Serializable {
	private static final long serialVersionUID = 1L;
	private PageDto pageDto;
	private String apprType;
	private String apprName;
	public PageDto getPageDto() {
		return pageDto;
	}
	public void setPageDto(PageDto pageDto) {
		this.pageDto = pageDto;
	}
	public String getApprName() {
		return apprName;
	}
	public void setApprName(String apprName) {
		this.apprName = apprName;
	}
	public String getApprType() {
		return apprType;
	}
	public void setApprType(String apprType) {
		this.apprType = apprType;
	}
		
}
