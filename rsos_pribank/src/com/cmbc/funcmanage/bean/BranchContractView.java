package com.cmbc.funcmanage.bean;

import java.io.Serializable;

public class BranchContractView implements Serializable {

	private static final long serialVersionUID = -5899064004119849903L;
	
	private String contractId;
	private String customeId;
    private String customeName;
    private String productId;
    private String productCode;
    private String productName;
    private String signAccount;
    private String handStatus;	
    private String handDate;	
    private String getContractDate;
    private String redempStatus; 
    private String redempStartDate;
    private String redempConformDate;
    
    private String benefitDate;
    private String dueDate;
    private String plannedBenefit;
    private String isRoll;
    
    private String openDay;
    private String redeemBegin;
    private String redeemEnd;
    private String rollBenefit;
    
    private String productType;
    private String belongDepartment;
    private String signDepartment;
    
    
    
	public BranchContractView() {
	}
	public BranchContractView(String contractId, String customeId,
			String customeName, String productId, String productCode,
			String productName, String signAccount, String handStatus,
			String redempStatus, String benefitDate, String dueDate, 
			String plannedBenefit,String isRoll) {
		this.contractId = contractId;
		this.customeId = customeId;
		this.customeName = customeName;
		this.productId = productId;
		this.productCode = productCode;
		this.productName = productName;
		this.signAccount = signAccount;
		this.handStatus = handStatus;
		this.redempStatus = redempStatus;
		this.benefitDate = benefitDate;
		this.dueDate = dueDate;
		this.plannedBenefit = plannedBenefit;
		this.isRoll = isRoll;
	}
	
	
	public BranchContractView(String contractId, String customeId,
			String customeName, String productId, String productCode,
			String productName, String redempStatus, String dueDate,
			String isRoll, String productType) {
		super();
		this.contractId = contractId;
		this.customeId = customeId;
		this.customeName = customeName;
		this.productId = productId;
		this.productCode = productCode;
		this.productName = productName;
		this.redempStatus = redempStatus;
		this.dueDate = dueDate;
		this.isRoll = isRoll;
		this.productType = productType;
	}
	public String getContractId() {
		return contractId;
	}
	public void setContractId(String contractId) {
		this.contractId = contractId;
	}
	public String getCustomeId() {
		return customeId;
	}
	public void setCustomeId(String customeId) {
		this.customeId = customeId;
	}
	public String getCustomeName() {
		return customeName;
	}
	public void setCustomeName(String customeName) {
		this.customeName = customeName;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getSignAccount() {
		return signAccount;
	}
	public void setSignAccount(String signAccount) {
		this.signAccount = signAccount;
	}
	public String getHandStatus() {
		return handStatus;
	}
	public void setHandStatus(String handStatus) {
		this.handStatus = handStatus;
	}
	public String getHandDate() {
		return handDate;
	}
	public void setHandDate(String handDate) {
		this.handDate = handDate;
	}
	public String getGetContractDate() {
		return getContractDate;
	}
	public void setGetContractDate(String getContractDate) {
		this.getContractDate = getContractDate;
	}
	public String getRedempStatus() {
		return redempStatus;
	}
	public void setRedempStatus(String redempStatus) {
		this.redempStatus = redempStatus;
	}
	public String getRedempStartDate() {
		return redempStartDate;
	}
	public void setRedempStartDate(String redempStartDate) {
		this.redempStartDate = redempStartDate;
	}
	public String getRedempConformDate() {
		return redempConformDate;
	}
	public void setRedempConformDate(String redempConformDate) {
		this.redempConformDate = redempConformDate;
	}
	public String getBenefitDate() {
		return benefitDate;
	}
	public void setBenefitDate(String benefitDate) {
		this.benefitDate = benefitDate;
	}
	public String getDueDate() {
		return dueDate;
	}
	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}
	public String getPlannedBenefit() {
		return plannedBenefit;
	}
	public void setPlannedBenefit(String plannedBenefit) {
		this.plannedBenefit = plannedBenefit;
	}
	public String getIsRoll() {
		return isRoll;
	}
	public void setIsRoll(String isRoll) {
		this.isRoll = isRoll;
	}
	public String getOpenDay() {
		return openDay;
	}
	public void setOpenDay(String openDay) {
		this.openDay = openDay;
	}
	public String getRedeemBegin() {
		return redeemBegin;
	}
	public void setRedeemBegin(String redeemBegin) {
		this.redeemBegin = redeemBegin;
	}
	public String getRedeemEnd() {
		return redeemEnd;
	}
	public void setRedeemEnd(String redeemEnd) {
		this.redeemEnd = redeemEnd;
	}
	public String getRollBenefit() {
		return rollBenefit;
	}
	public void setRollBenefit(String rollBenefit) {
		this.rollBenefit = rollBenefit;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	public String getProductType() {
		return productType;
	}
	public void setBelongDepartment(String belongDepartment) {
		this.belongDepartment = belongDepartment;
	}
	public String getBelongDepartment() {
		return belongDepartment;
	}
	public void setSignDepartment(String signDepartment) {
		this.signDepartment = signDepartment;
	}
	public String getSignDepartment() {
		return signDepartment;
	}
    
    

}
