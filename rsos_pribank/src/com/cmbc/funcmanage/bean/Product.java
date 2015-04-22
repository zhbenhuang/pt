package com.cmbc.funcmanage.bean;

import java.io.Serializable;
public class Product implements Serializable
{
	 private static final long serialVersionUID = 1L;
     private String productId;
	 private String productCode;
     private String productName;
     private String benefitDate;
     private String dueDate;
     private String plannedBenefit;
     private String isRoll;
     private String redemptionIntervalId;
     private String openDay;
     private String redeemBegin;
     private String redeemEnd;
     private String rollBenefit;
     private String addTime;
     private String editTime;
     
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
		public void setRedeemEnd(String redeemEnd) 
		{		
			this.redeemEnd = redeemEnd;
		}
	public String getRollBenefit() {
		return rollBenefit;
	}
	public void setRollBenefit(String rollBenefit) {
		this.rollBenefit = rollBenefit;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getProductId() {
		return productId;
	}
	public void setAddTime(String addTime) {
		this.addTime = addTime;
	}
	public String getAddTime() {
		return addTime;
	}
	public void setEditTime(String editTime) {
		this.editTime = editTime;
	}
	public String getEditTime() {
		return editTime;
	}
	public void setRedemptionIntervalId(String redemptionIntervalId) {
		this.redemptionIntervalId = redemptionIntervalId;
	}
	public String getRedemptionIntervalId() {
		return redemptionIntervalId;
	}
}

