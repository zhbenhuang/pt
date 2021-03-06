package com.cmbc.pbms.bean;

// Generated 2015-5-4 16:45:24 by Hibernate Tools 3.4.0.CR1

/**
 * PbmsApproveParmt generated by hbm2java
 */
public class PbmsApproveParmt implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5292591827469761142L;
	private int apprType;
	private String apprName;
	private Integer apprSwitch;
	private Integer stepNum;
	private String stepRole1;
	private String stepRole2;
	private String stepRole3;
	private String stepRole4;
	private String stepRole5;
	private String userId;
	private String alterTime;

	public PbmsApproveParmt() {
	}

	public PbmsApproveParmt(int apprType) {
		this.apprType = apprType;
	}

	public PbmsApproveParmt(int apprType, String apprName, Integer apprSwitch,
			Integer stepNum, String stepRole1, String stepRole2,
			String stepRole3, String stepRole4, String stepRole5,
			String userId, String alterTime) {
		this.apprType = apprType;
		this.apprName = apprName;
		this.apprSwitch = apprSwitch;
		this.stepNum = stepNum;
		this.stepRole1 = stepRole1;
		this.stepRole2 = stepRole2;
		this.stepRole3 = stepRole3;
		this.stepRole4 = stepRole4;
		this.stepRole5 = stepRole5;
		this.userId = userId;
		this.alterTime = alterTime;
	}

	public int getApprType() {
		return this.apprType;
	}

	public void setApprType(int apprType) {
		this.apprType = apprType;
	}

	public String getApprName() {
		return this.apprName;
	}

	public void setApprName(String apprName) {
		this.apprName = apprName;
	}

	public Integer getApprSwitch() {
		return this.apprSwitch;
	}

	public void setApprSwitch(Integer apprSwitch) {
		this.apprSwitch = apprSwitch;
	}

	public Integer getStepNum() {
		return this.stepNum;
	}

	public void setStepNum(Integer stepNum) {
		this.stepNum = stepNum;
	}

	public String getStepRole1() {
		return this.stepRole1;
	}

	public void setStepRole1(String stepRole1) {
		this.stepRole1 = stepRole1;
	}

	public String getStepRole2() {
		return this.stepRole2;
	}

	public void setStepRole2(String stepRole2) {
		this.stepRole2 = stepRole2;
	}

	public String getStepRole3() {
		return this.stepRole3;
	}

	public void setStepRole3(String stepRole3) {
		this.stepRole3 = stepRole3;
	}

	public String getStepRole4() {
		return this.stepRole4;
	}

	public void setStepRole4(String stepRole4) {
		this.stepRole4 = stepRole4;
	}

	public String getStepRole5() {
		return this.stepRole5;
	}

	public void setStepRole5(String stepRole5) {
		this.stepRole5 = stepRole5;
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

}
