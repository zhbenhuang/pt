package com.cmbc.sa.dto;

import java.io.Serializable;

import rsos.framework.db.PageDto;

public class QueryRoleDto implements Serializable {
	private static final long serialVersionUID = 1L;
	private PageDto pageDto;
	private String roleId;
	private String roleName;
	private Integer business;
	private String userId;
	private String userName;
	private String departmentId;
		
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
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
	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}
	public String getDepartmentId() {
		return departmentId;
	}
	
}
