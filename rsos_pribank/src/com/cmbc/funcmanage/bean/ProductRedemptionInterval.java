package com.cmbc.funcmanage.bean;

import java.io.Serializable;

public class ProductRedemptionInterval implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String redemptionIntervalId;
	private String productId;
	private String openDay;
    private String redeemBegin;
    private String redeemEnd;
    private String rollBenefit;
    private String redemptionStatus;	//赎回去状态---已过赎回期、正在赎回期、即将开始赎回、已过开放日
    

	public void setOpenDay(String openDay) {
		this.openDay = openDay;
	}

	public String getOpenDay() {
		return openDay;
	}

	public void setRedeemBegin(String redeemBegin) {
		this.redeemBegin = redeemBegin;
	}

	public String getRedeemBegin() {
		return redeemBegin;
	}

	public void setRedeemEnd(String redeemEnd) {
		this.redeemEnd = redeemEnd;
	}

	public String getRedeemEnd() {
		return redeemEnd;
	}

	public void setRollBenefit(String rollBenefit) {
		this.rollBenefit = rollBenefit;
	}

	public String getRollBenefit() {
		return rollBenefit;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getProductId() {
		return productId;
	}

	public void setRedemptionIntervalId(String redemptionIntervalId) {
		this.redemptionIntervalId = redemptionIntervalId;
	}

	public String getRedemptionIntervalId() {
		return redemptionIntervalId;
	}

	public void setRedemptionStatus(String redemptionStatus) {
		this.redemptionStatus = redemptionStatus;
	}

	public String getRedemptionStatus() {
		return redemptionStatus;
	}

	
}
