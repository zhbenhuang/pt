package com.cmbc.funcmanage.bean;

import java.io.Serializable;

public class ProductInfo implements Serializable {

	private static final long serialVersionUID = 1L;
	private String productCode;
    private String productName;
    private String benefitDate;
    private String dueDate;
    private String plannedBenefit;
    private String isRoll;
    private String firstOpenDay;
    private String rollIntervalSpan;
    private String rollBenefit;
    private String created;
     
     
     
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
	public String getIsRoll() {
		return isRoll;
	}
	public void setIsRoll(String isRoll) {
		this.isRoll = isRoll;
	}
	public String getFirstOpenDay() {
		return firstOpenDay;
	}
	public void setFirstOpenDay(String firstOpenDay) {
		this.firstOpenDay = firstOpenDay;
	}
	public String getRollIntervalSpan() {
		return rollIntervalSpan;
	}
	public void setRollIntervalSpan(String rollIntervalSpan) {
		this.rollIntervalSpan = rollIntervalSpan;
	}
	public String getRollBenefit() {
		return rollBenefit;
	}
	public void setRollBenefit(String rollBenefit) {
		this.rollBenefit = rollBenefit;
	}
	public void setCreated(String created) {
		this.created = created;
	}
	public String getCreated() {
		return created;
	}
     
     
     
     
     

}
