package com.cmbc.pbms.bean;

// Generated 2015-5-4 16:45:24 by Hibernate Tools 3.4.0.CR1


/**
 * PbmsServInfo generated by hbm2java
 */
public class PbmsServInfo implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 555820566217419132L;
	private int serId;
	private String serName;
	private String serDes;
	private Integer bigType;
	private Integer smlType;
	private Integer serValue;
	private Integer serAmount;
	private String begTime;
	private String endTime;
	private String serPic;
	private String fileUrl1;
	private String fileUrl2;
	private String userId;
	private String alterTime;
	private String opt = "";//

	public PbmsServInfo() {
	}

	public PbmsServInfo(int serId) {
		this.serId = serId;
	}

	public PbmsServInfo(int serId, String serName, String serDes,
			Integer bigType, Integer smlType, Integer serValue,
			Integer serAmount, String begTime, String endTime, String serPic,
			String fileUrl1, String fileUrl2, String userId, String alterTime) {
		this.serId = serId;
		this.serName = serName;
		this.serDes = serDes;
		this.bigType = bigType;
		this.smlType = smlType;
		this.serValue = serValue;
		this.serAmount = serAmount;
		this.begTime = begTime;
		this.endTime = endTime;
		this.serPic = serPic;
		this.fileUrl1 = fileUrl1;
		this.fileUrl2 = fileUrl2;
		this.userId = userId;
		this.alterTime = alterTime;
	}

	public int getSerId() {
		return this.serId;
	}

	public void setSerId(int serId) {
		this.serId = serId;
	}

	public String getSerName() {
		return this.serName;
	}

	public void setSerName(String serName) {
		this.serName = serName;
	}

	public String getSerDes() {
		return this.serDes;
	}

	public void setSerDes(String serDes) {
		this.serDes = serDes;
	}

	public Integer getBigType() {
		return this.bigType;
	}

	public void setBigType(Integer bigType) {
		this.bigType = bigType;
	}

	public Integer getSmlType() {
		return this.smlType;
	}

	public void setSmlType(Integer smlType) {
		this.smlType = smlType;
	}

	public Integer getSerValue() {
		return this.serValue;
	}

	public void setSerValue(Integer serValue) {
		this.serValue = serValue;
	}

	public Integer getSerAmount() {
		return this.serAmount;
	}

	public void setSerAmount(Integer serAmount) {
		this.serAmount = serAmount;
	}

	public String getBegTime() {
		return this.begTime;
	}

	public void setBegTime(String begTime) {
		this.begTime = begTime;
	}

	public String getEndTime() {
		return this.endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getSerPic() {
		return this.serPic;
	}

	public void setSerPic(String serPic) {
		this.serPic = serPic;
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

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getAlterTime() {
		return this.alterTime;
	}

	public void setAlterTime(String alterTime) {
		this.alterTime = alterTime;
	}

	public String getOpt() {
		return opt;
	}

	public void setOpt(String opt) {
		this.opt = opt;
	}

}
