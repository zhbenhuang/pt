package com.cmbc.reportStatistic.bean;

import java.io.Serializable;

public class RedempIntervalReport implements Serializable {

	private static final long serialVersionUID = -7070441960511157683L;
	private String openDay;
    private String redeemBegin;
    private String redeemEnd;
    private String rollBenefit;
    private String redemptionStatus;	//赎回去状态---已过赎回期、正在赎回期、即将开始赎回、已过开放日
    private String redempBackNum;
    
    
    
	public RedempIntervalReport() {
		super();
	}
	public RedempIntervalReport(String openDay, String redeemBegin,
			String redeemEnd, String rollBenefit, String redemptionStatus,
			String redempBackNum) {
		super();
		this.openDay = openDay;
		this.redeemBegin = redeemBegin;
		this.redeemEnd = redeemEnd;
		this.rollBenefit = rollBenefit;
		this.redemptionStatus = redemptionStatus;
		this.redempBackNum = redempBackNum;
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
	public String getRedemptionStatus() {
		return redemptionStatus;
	}
	public void setRedemptionStatus(String redemptionStatus) {
		this.redemptionStatus = redemptionStatus;
	}
	public String getRedempBackNum() {
		return redempBackNum;
	}
	public void setRedempBackNum(String redempBackNum) {
		this.redempBackNum = redempBackNum;
	}
    
    

}
