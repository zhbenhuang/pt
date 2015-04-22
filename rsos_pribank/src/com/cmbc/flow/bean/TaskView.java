package com.cmbc.flow.bean;

import java.io.Serializable;

public class TaskView implements Serializable {
	private static final long serialVersionUID = 1L;
	private String stepCode;
	private String taskName;
	private String assignUserId;
	private String assignUserName;
	private String assignDepartment;
	private String assignDepartmentId;	
	private String arriveTime;
	private String signTime;
	private String completeTime;
	private String taskStatus;
	private String actType;
	
	public TaskView() {
	}
	public TaskView(String stepCode, String taskName, String assignUserId,
			String assignUserName, String assignDepartment,
			String assignDepartmentId, String arriveTime, String signTime,
			String completeTime, String taskStatus,String actType) {
		this.stepCode = stepCode;
		this.taskName = taskName;
		this.assignUserId = assignUserId;
		this.assignUserName = assignUserName;
		this.assignDepartment = assignDepartment;
		this.assignDepartmentId = assignDepartmentId;
		this.arriveTime = arriveTime;
		this.signTime = signTime;
		this.completeTime = completeTime;
		this.taskStatus = taskStatus;
		this.actType = actType;
	}
	
	public String getStepCode() {
		return stepCode;
	}
	public void setStepCode(String stepCode) {
		this.stepCode = stepCode;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public String getAssignUserId() {
		return assignUserId;
	}
	public void setAssignUserId(String assignUserId) {
		this.assignUserId = assignUserId;
	}
	public String getAssignUserName() {
		return assignUserName;
	}
	public void setAssignUserName(String assignUserName) {
		this.assignUserName = assignUserName;
	}
	public String getAssignDepartment() {
		return assignDepartment;
	}
	public void setAssignDepartment(String assignDepartment) {
		this.assignDepartment = assignDepartment;
	}
	public String getAssignDepartmentId() {
		return assignDepartmentId;
	}
	public void setAssignDepartmentId(String assignDepartmentId) {
		this.assignDepartmentId = assignDepartmentId;
	}
	public String getArriveTime() {
		return arriveTime;
	}
	public void setArriveTime(String arriveTime) {
		this.arriveTime = arriveTime;
	}
	public String getSignTime() {
		return signTime;
	}
	public void setSignTime(String signTime) {
		this.signTime = signTime;
	}
	public String getCompleteTime() {
		return completeTime;
	}
	public void setCompleteTime(String completeTime) {
		this.completeTime = completeTime;
	}
	public String getTaskStatus() {
		return taskStatus;
	}
	public void setTaskStatus(String taskStatus) {
		this.taskStatus = taskStatus;
	}
	public void setActType(String actType) {
		this.actType = actType;
	}
	public String getActType() {
		return actType;
	}

}
