package com.cmbc.funcmanage.bean;

import java.io.Serializable;

public class RedempBook implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String contractId;
	private String productId;
	private String productCode;
	private String productName;
	private String benefitDate;
	private String dueDate;
	private String plannedBenefit;
	private String customeId;
	private String customeName;
	private String signAccount;
	private String money;
	private String belongDepartment;
	private String signDepartment;
	private String redempStartDate;
	private String redempConformDate;
	private String openDay;
	private String redeemBegin;
	private String redeemEnd;
	private String rollBenefit;
	private String redempStatus;
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
	public String getSignAccount() {
		return signAccount;
	}
	public void setSignAccount(String signAccount) {
		this.signAccount = signAccount;
	}
	public String getBelongDepartment() {
		return belongDepartment;
	}
	public void setBelongDepartment(String belongDepartment) {
		this.belongDepartment = belongDepartment;
	}
	public String getSignDepartment() {
		return signDepartment;
	}
	public void setSignDepartment(String signDepartment) {
		this.signDepartment = signDepartment;
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
	public String getRedempStatus() {
		return redempStatus;
	}
	public void setRedempStatus(String redempStatus) {
		this.redempStatus = redempStatus;
	}
	public void setMoney(String money) {
		this.money = money;
	}
	public String getMoney() {
		return money;
	}
	public void setContractId(String contractId) {
		this.contractId = contractId;
	}
	public String getContractId() {
		return contractId;
	}
}
