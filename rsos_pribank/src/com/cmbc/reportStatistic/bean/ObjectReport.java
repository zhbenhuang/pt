package com.cmbc.reportStatistic.bean;

import java.io.Serializable;

public class ObjectReport implements Serializable{
	private static final long serialVersionUID = -6581269446524784312L;
	private int serialNumber;
	private String businessId;
	private String clientId;
	private String clientName;
	private String objectApplyerName;
	private String applyDepartmentName;
	private String fdevApplyerName;
	private String fdevApplyDepartment;
	private String applyDate;
	private String endDate;
	private String newAgentName;
	private String newBranchBank;
	private String originalAgentName;
	private String originalBranchBank;
	private String result;
	
	public String getBusinessId() {
		return businessId;
	}
	public void setBusinessId(String businessId) {
		this.businessId = businessId;
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
	public String getObjectApplyerName() {
		return objectApplyerName;
	}
	public void setObjectApplyerName(String objectApplyerName) {
		this.objectApplyerName = objectApplyerName;
	}
	public String getApplyDepartmentName() {
		return applyDepartmentName;
	}
	public void setApplyDepartmentName(String applyDepartmentName) {
		this.applyDepartmentName = applyDepartmentName;
	}
	public String getApplyDate() {
		return applyDate;
	}
	public void setApplyDate(String applyDate) {
		this.applyDate = applyDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getNewAgentName() {
		return newAgentName;
	}
	public void setNewAgentName(String newAgentName) {
		this.newAgentName = newAgentName;
	}
	public String getNewBranchBank() {
		return newBranchBank;
	}
	public void setNewBranchBank(String newBranchBank) {
		this.newBranchBank = newBranchBank;
	}
	public String getOriginalAgentName() {
		return originalAgentName;
	}
	public void setOriginalAgentName(String originalAgentName) {
		this.originalAgentName = originalAgentName;
	}
	public String getOriginalBranchBank() {
		return originalBranchBank;
	}
	public void setOriginalBranchBank(String originalBranchBank) {
		this.originalBranchBank = originalBranchBank;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public void setSerialNumber(int serialNumber) {
		this.serialNumber = serialNumber;
	}
	public int getSerialNumber() {
		return serialNumber;
	}
	public void setFdevApplyDepartment(String fdevApplyDepartment) {
		this.fdevApplyDepartment = fdevApplyDepartment;
	}
	public String getFdevApplyDepartment() {
		return fdevApplyDepartment;
	}
	public void setFdevApplyerName(String fdevApplyerName) {
		this.fdevApplyerName = fdevApplyerName;
	}
	public String getFdevApplyerName() {
		return fdevApplyerName;
	}
	
}
