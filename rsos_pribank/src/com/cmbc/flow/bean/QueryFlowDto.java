package com.cmbc.flow.bean;

import java.io.Serializable;

import rsos.framework.db.PageDto;

public class QueryFlowDto implements Serializable {
	private static final long serialVersionUID = 1L;
	private PageDto pageDto;
	private String flowName;
	private String startUser;
	private String preUser;
	private String topic;
	private String flowType;
	private String beginStartDate;
	private String endStartDate;
	private String assignUserId;
	private String processStatus;
	private String isFlowActivity;
	private String taskStatus;
	private String isTaskActivity;
	private String taskId;
	private String processInsId;
	
	public PageDto getPageDto() {
		return pageDto;
	}
	public void setPageDto(PageDto pageDto) {
		this.pageDto = pageDto;
	}
	public String getFlowName() {
		return flowName;
	}
	public void setFlowName(String flowName) {
		this.flowName = flowName;
	}
	public String getStartUser() {
		return startUser;
	}
	public void setStartUser(String startUser) {
		this.startUser = startUser;
	}
	public String getPreUser() {
		return preUser;
	}
	public void setPreUser(String preUser) {
		this.preUser = preUser;
	}
	public String getTopic() {
		return topic;
	}
	public void setTopic(String topic) {
		this.topic = topic;
	}
	public String getFlowType() {
		return flowType;
	}
	public void setFlowType(String flowType) {
		this.flowType = flowType;
	}
	public String getBeginStartDate() {
		return beginStartDate;
	}
	public void setBeginStartDate(String beginStartDate) {
		this.beginStartDate = beginStartDate;
	}
	public String getEndStartDate() {
		return endStartDate;
	}
	public void setEndStartDate(String endStartDate) {
		this.endStartDate = endStartDate;
	}
	public String getAssignUserId() {
		return assignUserId;
	}
	public void setAssignUserId(String assignUserId) {
		this.assignUserId = assignUserId;
	}
	public String getProcessStatus() {
		return processStatus;
	}
	public void setProcessStatus(String processStatus) {
		this.processStatus = processStatus;
	}
	public String getIsFlowActivity() {
		return isFlowActivity;
	}
	public void setIsFlowActivity(String isFlowActivity) {
		this.isFlowActivity = isFlowActivity;
	}
	public String getTaskStatus() {
		return taskStatus;
	}
	public void setTaskStatus(String taskStatus) {
		this.taskStatus = taskStatus;
	}
	public String getIsTaskActivity() {
		return isTaskActivity;
	}
	public void setIsTaskActivity(String isTaskActivity) {
		this.isTaskActivity = isTaskActivity;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public void setProcessInsId(String processInsId) {
		this.processInsId = processInsId;
	}
	public String getProcessInsId() {
		return processInsId;
	}
	
	
}
