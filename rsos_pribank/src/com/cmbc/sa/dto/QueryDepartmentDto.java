package com.cmbc.sa.dto;

import java.io.Serializable;

import rsos.framework.db.PageDto;


public class QueryDepartmentDto  implements Serializable {
	private static final long serialVersionUID = 1L;
	private PageDto pageDto;
	private String departmentId;
    private String departmentName;
	private String pDepartmentId = null;
    private String pDepartmentName = null;
    private String departmentList;
    private String type;
	
	public QueryDepartmentDto() {
	}

	public PageDto getPageDto() {
		return pageDto;
	}

	public void setPageDto(PageDto pageDto) {
		this.pageDto = pageDto;
	}

	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getpDepartmentId() {
		return pDepartmentId;
	}

	public void setpDepartmentId(String pDepartmentId) {
		this.pDepartmentId = pDepartmentId;
	}

	public String getpDepartmentName() {
		return pDepartmentName;
	}

	public void setpDepartmentName(String pDepartmentName) {
		this.pDepartmentName = pDepartmentName;
	}

	public String getDepartmentList() {
		return departmentList;
	}

	public void setDepartmentList(String departmentList) {
		this.departmentList = departmentList;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}
	
}
