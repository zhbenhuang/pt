package com.cmbc.funcmanage.bean;

import java.io.Serializable;

public class NoticeView implements Serializable {
	private static final long serialVersionUID = 7019120361993571024L;

	private String noticeId;							//notice主键
	private String noticeType;							//通知类型,针对不同的业务
	private String noticeTitle;							//通知的标题
	private String noticeArriveTime;                    //通知产生时间
	private String noticeDealTime;						//通知查阅时间
	private String noticeDealStatus;					//通知处理状态
	private String noticeViewTime;						//通知查阅时间
	private String noticeViewStatus;					//通知查阅状态
	
	private String belongDepartment;					//归属机构
	private String signDepartment;						//签约机构
	public String getNoticeId() {
		return noticeId;
	}
	public void setNoticeId(String noticeId) {
		this.noticeId = noticeId;
	}
	public String getNoticeType() {
		return noticeType;
	}
	public void setNoticeType(String noticeType) {
		this.noticeType = noticeType;
	}
	public String getNoticeTitle() {
		return noticeTitle;
	}
	public void setNoticeTitle(String noticeTitle) {
		this.noticeTitle = noticeTitle;
	}
	public String getNoticeArriveTime() {
		return noticeArriveTime;
	}
	public void setNoticeArriveTime(String noticeArriveTime) {
		this.noticeArriveTime = noticeArriveTime;
	}
	public String getNoticeDealTime() {
		return noticeDealTime;
	}
	public void setNoticeDealTime(String noticeDealTime) {
		this.noticeDealTime = noticeDealTime;
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
	public void setNoticeDealStatus(String noticeDealStatus) {
		this.noticeDealStatus = noticeDealStatus;
	}
	public String getNoticeDealStatus() {
		return noticeDealStatus;
	}
	public void setNoticeViewTime(String noticeViewTime) {
		this.noticeViewTime = noticeViewTime;
	}
	public String getNoticeViewTime() {
		return noticeViewTime;
	}
	public void setNoticeViewStatus(String noticeViewStatus) {
		this.noticeViewStatus = noticeViewStatus;
	}
	public String getNoticeViewStatus() {
		return noticeViewStatus;
	}
	
	
}
