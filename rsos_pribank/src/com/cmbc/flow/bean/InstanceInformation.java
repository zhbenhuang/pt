package com.cmbc.flow.bean;

import java.io.Serializable;

public class InstanceInformation implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String businessId;
	private String codeId;
	private String processTypeName;
	private String clientId;
	private String clientName;
	private String status;
	private String userId;
	private String userName;
	private String department;
	private String createTime;	//流程发起时间
	private String editTime;
	private String title;
	
	private String taskId;
	
	private String arriveTime;
	private String signTime;
	private String completeTime;
	private String finishedTime;
	
	private String preUserId;
	private String preUserName;
	private String preDepartment;
	
	private String nextUserId;
	private String nextUserName;
	private String nextDepartment;
	
	private String currentUserId;
	private String currentUserName;
	private String currentDepartment;
	
	public String getBusinessId() {
		return businessId;
	}
	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}
	public String getProcessTypeName() {
		return processTypeName;
	}
	public void setProcessTypeName(String processTypeName) {
		this.processTypeName = processTypeName;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getEditTime() {
		return editTime;
	}
	public void setEditTime(String editTime) {
		this.editTime = editTime;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public String getTaskId() {
		return taskId;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSignTime() {
		return signTime;
	}
	public void setSignTime(String signTime) {
		this.signTime = signTime;
	}
	public void setArriveTime(String arriveTime) {
		this.arriveTime = arriveTime;
	}
	public String getArriveTime() {
		return arriveTime;
	}
	public void setCompleteTime(String completeTime) {
		this.completeTime = completeTime;
	}
	public String getCompleteTime() {
		return completeTime;
	}
	public void setPreUserId(String preUserId) {
		this.preUserId = preUserId;
	}
	public String getPreUserId() {
		return preUserId;
	}
	public void setPreUserName(String preUserName) {
		this.preUserName = preUserName;
	}
	public String getPreUserName() {
		return preUserName;
	}
	public void setPreDepartment(String preDepartment) {
		this.preDepartment = preDepartment;
	}
	public String getPreDepartment() {
		return preDepartment;
	}
	public void setNextUserId(String nextUserId) {
		this.nextUserId = nextUserId;
	}
	public String getNextUserId() {
		return nextUserId;
	}
	public void setNextUserName(String nextUserName) {
		this.nextUserName = nextUserName;
	}
	public String getNextUserName() {
		return nextUserName;
	}
	public void setNextDepartment(String nextDepartment) {
		this.nextDepartment = nextDepartment;
	}
	public String getNextDepartment() {
		return nextDepartment;
	}
	public void setFinishedTime(String finishedTime) {
		this.finishedTime = finishedTime;
	}
	public String getFinishedTime() {
		return finishedTime;
	}
	public void setCodeId(String codeId) {
		this.codeId = codeId;
	}
	public String getCodeId() {
		return codeId;
	}
	public void setCurrentUserName(String currentUserName) {
		this.currentUserName = currentUserName;
	}
	public String getCurrentUserName() {
		return currentUserName;
	}
	public void setCurrentUserId(String currentUserId) {
		this.currentUserId = currentUserId;
	}
	public String getCurrentUserId() {
		return currentUserId;
	}
	public void setCurrentDepartment(String currentDepartment) {
		this.currentDepartment = currentDepartment;
	}
	public String getCurrentDepartment() {
		return currentDepartment;
	}
	
	
	
	
	
	

}
