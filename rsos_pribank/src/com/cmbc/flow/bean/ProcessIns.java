package com.cmbc.flow.bean;

/**
 * ProcessIns entity. @author MyEclipse Persistence Tools
 */

public class ProcessIns implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	// Fields

	private String businessId;
	private String createUserId;
	private String createUserName;
	private String processInsId;
	private String processTypeName;
	private String status;
	private String signTime;
	private String createTime;
	private String topic;
	private String codeId;
	private String department;
	private String departmentId;
	private String startTime;
	private String completeTime;
	private String currentTaskId;
	private String currentUserId;
	private String currentUserName;
	private String currentDepartmentId;
	private String currentDepartmentName;
	private String isActivity;

	// Constructors

	/** default constructor */
	public ProcessIns() {
	}

	/** minimal constructor */
	public ProcessIns(String businessId) {
		this.businessId = businessId;
	}

	/** full constructor */
	public ProcessIns(String businessId, String createUserId,
			String createUserName, String processInsId, String processTypeName,
			String status, String signTime, String createTime, String topic,
			String codeId, String department, String departmentId,
			String startTime, String completeTime, String currentTaskId,
			String isActivity, String url) {
		this.businessId = businessId;
		this.createUserId = createUserId;
		this.createUserName = createUserName;
		this.processInsId = processInsId;
		this.processTypeName = processTypeName;
		this.status = status;
		this.signTime = signTime;
		this.createTime = createTime;
		this.topic = topic;
		this.codeId = codeId;
		this.department = department;
		this.departmentId = departmentId;
		this.startTime = startTime;
		this.completeTime = completeTime;
		this.currentTaskId = currentTaskId;
		this.isActivity = isActivity;
		//this.url = url;
	}

	public String getBusinessId() {
		return this.businessId;
	}

	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}

	public String getCreateUserId() {
		return this.createUserId;
	}

	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}

	public String getCreateUserName() {
		return this.createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	public String getProcessInsId() {
		return this.processInsId;
	}

	public void setProcessInsId(String processInsId) {
		this.processInsId = processInsId;
	}

	public String getProcessTypeName() {
		return this.processTypeName;
	}

	public void setProcessTypeName(String processTypeName) {
		this.processTypeName = processTypeName;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSignTime() {
		return this.signTime;
	}

	public void setSignTime(String signTime) {
		this.signTime = signTime;
	}

	public String getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getTopic() {
		return this.topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public String getCodeId() {
		return this.codeId;
	}

	public void setCodeId(String codeId) {
		this.codeId = codeId;
	}

	public String getDepartment() {
		return this.department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getDepartmentId() {
		return this.departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	public String getCurrentUserName() {
		return currentUserName;
	}

	public void setCurrentUserName(String currentUserName) {
		this.currentUserName = currentUserName;
	}

	public String getCurrentDepartmentId() {
		return currentDepartmentId;
	}

	public void setCurrentDepartmentId(String currentDepartmentId) {
		this.currentDepartmentId = currentDepartmentId;
	}

	public String getCurrentDepartmentName() {
		return currentDepartmentName;
	}

	public void setCurrentDepartmentName(String currentDepartmentName) {
		this.currentDepartmentName = currentDepartmentName;
	}

	public String getStartTime() {
		return this.startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getCompleteTime() {
		return this.completeTime;
	}

	public void setCompleteTime(String completeTime) {
		this.completeTime = completeTime;
	}

	public String getCurrentTaskId() {
		return this.currentTaskId;
	}

	public void setCurrentTaskId(String currentTaskId) {
		this.currentTaskId = currentTaskId;
	}

	public String getIsActivity() {
		return this.isActivity;
	}

	public void setIsActivity(String isActivity) {
		this.isActivity = isActivity;
	}

	public void setCurrentUserId(String currentUserId) {
		this.currentUserId = currentUserId;
	}

	public String getCurrentUserId() {
		return currentUserId;
	}

	/*public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}*/

}