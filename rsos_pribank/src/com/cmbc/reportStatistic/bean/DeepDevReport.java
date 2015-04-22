package com.cmbc.reportStatistic.bean;

import java.io.Serializable;

public class DeepDevReport implements Serializable{

	private static final long serialVersionUID = 4640058017732843500L;
	private int serialNumber;
	private String businessId;
	private String clientId;
	private String clientName;
	private String originalAgent;
	private String originalBranchBank;
	private String newAgent;
	private String newBranchBank;
	//private String agentName;
	private String applyerName;
	private String applyDepartmentName;
	private String applyDate;
	private String endDate;
	private String result;
	public DeepDevReport(String clientId,String clientName,String originalAgent,
			String originalBranchBank,String newAgent,String newBranchBank,String agentName,String applyDepartmentName,
			String applyDate,String endDate,String result,String applyerName){
		this.clientId = clientId;
		this.clientName = clientName;
		this.originalAgent = originalAgent;
		this.originalBranchBank = originalBranchBank;
		this.newAgent = newAgent;
		this.newBranchBank = newBranchBank;
		//this.agentName = agentName;
		this.applyDepartmentName=applyDepartmentName;
		this.applyDate = applyDate;
		this.endDate = endDate;
		this.result = result;
		this.applyerName = applyerName;
	}
	public DeepDevReport(){
		
	}
	
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
	public String getOriginalAgent() {
		return originalAgent;
	}
	public void setOriginalAgent(String originalAgent) {
		this.originalAgent = originalAgent;
	}
	public String getNewAgent() {
		return newAgent;
	}
	public void setNewAgent(String newAgent) {
		this.newAgent = newAgent;
	}
	public String getOriginalBranchBank() {
		return originalBranchBank;
	}
	public void setOriginalBranchBank(String originalBranchBank) {
		this.originalBranchBank = originalBranchBank;
	}
	
	public String getNewBranchBank() {
		return newBranchBank;
	}
	public void setNewBranchBank(String newBranchBank) {
		this.newBranchBank = newBranchBank;
	}
//	public String getAgentName() {
//		return agentName;
//	}
//	public void setAgentName(String agentName) {
//		this.agentName = agentName;
//	}
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
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public void setApplyDepartmentName(String applyDepartmentName) {
		this.applyDepartmentName = applyDepartmentName;
	}
	public String getApplyDepartmentName() {
		return applyDepartmentName;
	}
	public void setSerialNumber(int serialNumber) {
		this.serialNumber = serialNumber;
	}
	public int getSerialNumber() {
		return serialNumber;
	}
	public void setApplyerName(String applyerName) {
		this.applyerName = applyerName;
	}
	public String getApplyerName() {
		return applyerName;
	}
	
}
