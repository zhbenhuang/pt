package com.cmbc.reportStatistic.bean;

public class FdevReport {
	private int serialNumber;
	private String applyDate;
	private String applyerName;
	private String applyDepartmentName;
	private String clientId;
	private String clientName;
	private String agentName;
	private String branchBank;
	private String originalBranchBank;
	private String originalAgentName;
	private String result;
	public int getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(int serialNumber) {
		this.serialNumber = serialNumber;
	}
	public String getApplyDate() {
		return applyDate;
	}
	public void setApplyDate(String applyDate) {
		this.applyDate = applyDate;
	}
	public String getBranchBank() {
		return branchBank;
	}
	public void setBranchBank(String branchBank) {
		this.branchBank = branchBank;
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
	public String getOriginalBranchBank() {
		return originalBranchBank;
	}
	public void setOriginalBranchBank(String originalBranchBank) {
		this.originalBranchBank = originalBranchBank;
	}
	public String getOriginalAgentName() {
		return originalAgentName;
	}
	public void setOriginalAgentName(String originalAgentName) {
		this.originalAgentName = originalAgentName;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}
	public String getAgentName() {
		return agentName;
	}
	public void setApplyDepartmentName(String applyDepartmentName) {
		this.applyDepartmentName = applyDepartmentName;
	}
	public String getApplyDepartmentName() {
		return applyDepartmentName;
	}
	public void setApplyerName(String applyerName) {
		this.applyerName = applyerName;
	}
	public String getApplyerName() {
		return applyerName;
	}
	
}
