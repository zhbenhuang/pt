package com.cmbc.pbms.dto;

import java.io.Serializable;

import rsos.framework.db.PageDto;

public class QueryStruValueDto implements Serializable {
	private static final long serialVersionUID = 1L;
	private PageDto pageDto;
	private String struId;
	private String term;
	public PageDto getPageDto() {
		return pageDto;
	}
	public void setPageDto(PageDto pageDto) {
		this.pageDto = pageDto;
	}
	public String getStruId() {
		return struId;
	}
	public void setStruId(String struId) {
		this.struId = struId;
	}
	public String getTerm() {
		return term;
	}
	public void setTerm(String term) {
		this.term = term;
	}
		
}
