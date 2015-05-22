package com.cmbc.pbms.dto;

import java.io.Serializable;

import rsos.framework.db.PageDto;

public class QueryServApplyDto implements Serializable {
	private static final long serialVersionUID = 1L;
	private PageDto pageDto;
	private String seqNo;
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
	public String getSeqNo() {
		return seqNo;
	}
	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
	}
	public String getApprId() {
		return apprId;
	}
	public void setApprId(String apprId) {
		this.apprId = apprId;
	}
		
}
