package com.cmbc.flow.bean;

/**
 * TaskIns entity. @author MyEclipse Persistence Tools
 */

public class TaskIns implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	// Fields

	private String taskId;
	private String processInsId;
	private String taskName;
	private String assignUserId;
	private String assignUserName;
	private String assignDepartment;
	private String assignDepartmentId;
	private String preUserId;
	private String preUserName;
	private String preDepartment;
	private String preDepartmentId;
	private String nextUserId;
	private String nextUserName;
	private String nextDepartment;
	private String nextDepartmentId;
	private String arriveTime;
	private String signTime;
	private String completeTime;
	private String status;
	private String isActivity;
	//private String url;

	// Constructors

	/** default constructor */
	public TaskIns() {
	}

	/** minimal constructor */
	public TaskIns(String taskId, String processInsId) {
		this.taskId = taskId;
		this.processInsId = processInsId;
	}

	public TaskIns(String taskId, String processInsId, String taskName,
			String assignUserId, String assignUserName,
			String assignDepartment, String assignDepartmentId,
			String preUserId, String preUserName, String preDepartment,
			String preDepartmentId, String nextUserId, String nextUserName,
			String nextDepartment, String nextDepartmentId, String arriveTime,
			String signTime, String completeTime, String status,
			String isActivity, String url) {
		this.taskId = taskId;
		this.processInsId = processInsId;
		this.taskName = taskName;
		this.assignUserId = assignUserId;
		this.assignUserName = assignUserName;
		this.assignDepartment = assignDepartment;
		this.assignDepartmentId = assignDepartmentId;
		this.preUserId = preUserId;
		this.preUserName = preUserName;
		this.preDepartment = preDepartment;
		this.preDepartmentId = preDepartmentId;
		this.nextUserId = nextUserId;
		this.nextUserName = nextUserName;
		this.nextDepartment = nextDepartment;
		this.nextDepartmentId = nextDepartmentId;
		this.arriveTime = arriveTime;
		this.signTime = signTime;
		this.completeTime = completeTime;
		this.status = status;
		this.isActivity = isActivity;
		//this.url = url;
	}

	// Property accessors

	public String getTaskId() {
		return this.taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getProcessInsId() {
		return this.processInsId;
	}

	public void setProcessInsId(String processInsId) {
		this.processInsId = processInsId;
	}

	public String getTaskName() {
		return this.taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getAssignUserId() {
		return this.assignUserId;
	}

	public void setAssignUserId(String assignUserId) {
		this.assignUserId = assignUserId;
	}

	public String getAssignDepartment() {
		return this.assignDepartment;
	}

	public void setAssignDepartment(String assignDepartment) {
		this.assignDepartment = assignDepartment;
	}

	public String getAssignDepartmentId() {
		return this.assignDepartmentId;
	}

	public void setAssignDepartmentId(String assignDepartmentId) {
		this.assignDepartmentId = assignDepartmentId;
	}

	public String getPreUserId() {
		return this.preUserId;
	}

	public void setPreUserId(String preUserId) {
		this.preUserId = preUserId;
	}

	public String getPreDepartment() {
		return this.preDepartment;
	}

	public void setPreDepartment(String preDepartment) {
		this.preDepartment = preDepartment;
	}

	public String getPreDepartmentId() {
		return this.preDepartmentId;
	}

	public void setPreDepartmentId(String preDepartmentId) {
		this.preDepartmentId = preDepartmentId;
	}

	public String getNextUserId() {
		return this.nextUserId;
	}

	public void setNextUserId(String nextUserId) {
		this.nextUserId = nextUserId;
	}

	public String getNextDepartment() {
		return this.nextDepartment;
	}

	public void setNextDepartment(String nextDepartment) {
		this.nextDepartment = nextDepartment;
	}

	public String getNextDepartmentId() {
		return this.nextDepartmentId;
	}

	public void setNextDepartmentId(String nextDepartmentId) {
		this.nextDepartmentId = nextDepartmentId;
	}

	public String getArriveTime() {
		return this.arriveTime;
	}

	public void setArriveTime(String arriveTime) {
		this.arriveTime = arriveTime;
	}

	public String getSignTime() {
		return this.signTime;
	}

	public void setSignTime(String signTime) {
		this.signTime = signTime;
	}

	public String getCompleteTime() {
		return this.completeTime;
	}

	public void setCompleteTime(String completeTime) {
		this.completeTime = completeTime;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getIsActivity() {
		return this.isActivity;
	}

	public void setIsActivity(String isActivity) {
		this.isActivity = isActivity;
	}

	/*public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}*/

	public String getAssignUserName() {
		return assignUserName;
	}

	public void setAssignUserName(String assignUserName) {
		this.assignUserName = assignUserName;
	}

	public String getPreUserName() {
		return preUserName;
	}

	public void setPreUserName(String preUserName) {
		this.preUserName = preUserName;
	}

	public String getNextUserName() {
		return nextUserName;
	}

	public void setNextUserName(String nextUserName) {
		this.nextUserName = nextUserName;
	}

}