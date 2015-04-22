package com.cmbc.funcmanage.bean;

import java.io.Serializable;

public class Notice implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String noticeId;							//notice主键
	private String noticeType;							//通知类型,针对不同的业务
	private String noticeTitle;							//通知的标题
	private String noticeArriveTime;                    //通知产生时间
	private String noticeViewTime;						//通知查阅时间
	private String noticeViewStatus;					//通知查阅状态
	private String noticeDealTime;						//通知处理时间
	private String noticeDealStatus;					//通知处理状态
	private String assBusinessId;						//关联业务Id
	
	private String departmentId;						//查看和处理通知的机构
	private String noticeFlag;							//根据查阅角色的不同区分通知
	
	
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
	public String getNoticeId() {
		return noticeId;
	}
	public void setAssBusinessId(String assBusinessId) {
		this.assBusinessId = assBusinessId;
	}
	public String getAssBusinessId() {
		return assBusinessId;
	}
	public void setNoticeFlag(String noticeFlag) {
		this.noticeFlag = noticeFlag;
	}
	public String getNoticeFlag() {
		return noticeFlag;
	}
	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}
	public String getDepartmentId() {
		return departmentId;
	}
	public void setNoticeDealStatus(String noticeDealStatus) {
		this.noticeDealStatus = noticeDealStatus;
	}
	public String getNoticeDealStatus() {
		return noticeDealStatus;
	}
	public void setNoticeViewStatus(String noticeViewStatus) {
		this.noticeViewStatus = noticeViewStatus;
	}
	public String getNoticeViewStatus() {
		return noticeViewStatus;
	}
	public void setNoticeViewTime(String noticeViewTime) {
		this.noticeViewTime = noticeViewTime;
	}
	public String getNoticeViewTime() {
		return noticeViewTime;
	}
	

}
