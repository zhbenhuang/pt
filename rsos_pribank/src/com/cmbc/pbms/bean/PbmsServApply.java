package com.cmbc.pbms.bean;

// Generated 2015-5-4 16:45:24 by Hibernate Tools 3.4.0.CR1

/**
 * PbmsServApply generated by hbm2java
 */
public class PbmsServApply implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3351464955026263413L;
	private int seqNo;
	private Integer serId;
	private Integer apprId;
	private String struId;
	private Integer userId;
	private Integer clientId;
	private String pbclientName;
	private Integer mobilePhone;
	private String applyTime;
	private Integer applyQuatt;
	private String fileUrl1;
	private String fileUrl2;
	private String remark;
	private String applyStatus;

	public PbmsServApply() {
	}

	public PbmsServApply(int seqNo) {
		this.seqNo = seqNo;
	}

	public PbmsServApply(int seqNo, Integer serId, Integer apprId,
			String struId, Integer userId, Integer clientId,
			String pbclientName, Integer mobilePhone, String applyTime,
			Integer applyQuatt, String fileUrl1, String fileUrl2,
			String remark, String applyStatus) {
		this.seqNo = seqNo;
		this.serId = serId;
		this.apprId = apprId;
		this.struId = struId;
		this.userId = userId;
		this.clientId = clientId;
		this.pbclientName = pbclientName;
		this.mobilePhone = mobilePhone;
		this.applyTime = applyTime;
		this.applyQuatt = applyQuatt;
		this.fileUrl1 = fileUrl1;
		this.fileUrl2 = fileUrl2;
		this.remark = remark;
		this.applyStatus = applyStatus;
	}

	public int getSeqNo() {
		return this.seqNo;
	}

	public void setSeqNo(int seqNo) {
		this.seqNo = seqNo;
	}

	public Integer getSerId() {
		return this.serId;
	}

	public void setSerId(Integer serId) {
		this.serId = serId;
	}

	public Integer getApprId() {
		return this.apprId;
	}

	public void setApprId(Integer apprId) {
		this.apprId = apprId;
	}

	public String getStruId() {
		return this.struId;
	}

	public void setStruId(String struId) {
		this.struId = struId;
	}

	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getClientId() {
		return this.clientId;
	}

	public void setClientId(Integer clientId) {
		this.clientId = clientId;
	}

	public String getPbclientName() {
		return this.pbclientName;
	}

	public void setPbclientName(String pbclientName) {
		this.pbclientName = pbclientName;
	}

	public Integer getMobilePhone() {
		return this.mobilePhone;
	}

	public void setMobilePhone(Integer mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getApplyTime() {
		return this.applyTime;
	}

	public void setApplyTime(String applyTime) {
		this.applyTime = applyTime;
	}

	public Integer getApplyQuatt() {
		return this.applyQuatt;
	}

	public void setApplyQuatt(Integer applyQuatt) {
		this.applyQuatt = applyQuatt;
	}

	public String getFileUrl1() {
		return this.fileUrl1;
	}

	public void setFileUrl1(String fileUrl1) {
		this.fileUrl1 = fileUrl1;
	}

	public String getFileUrl2() {
		return this.fileUrl2;
	}

	public void setFileUrl2(String fileUrl2) {
		this.fileUrl2 = fileUrl2;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getApplyStatus() {
		return this.applyStatus;
	}

	public void setApplyStatus(String applyStatus) {
		this.applyStatus = applyStatus;
	}

}
