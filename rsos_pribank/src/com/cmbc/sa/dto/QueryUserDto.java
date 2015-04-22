package com.cmbc.sa.dto;

import java.io.Serializable;

import rsos.framework.db.PageDto;

public class QueryUserDto implements Serializable {
	private static final long serialVersionUID = 1L;
	private PageDto pageDto;
	private String userId;
	private String userName;
	private String departmentId;
	private String departmentName;
	private Integer business;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Integer getBusiness() {
		return business;
	}
	public void setBusiness(Integer business) {
		this.business = business;
	}
	public PageDto getPageDto() {
		return pageDto;
	}
	public void setPageDto(PageDto pageDto) {
		this.pageDto = pageDto;
	}
	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}
	public String getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	public String getDepartmentName() {
		return departmentName;
	}
	
}
