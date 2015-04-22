package com.cmbc.funcmanage.bean;

import java.io.Serializable;

public class RedemptionInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String businessId;
	private String contractId;
	private String codeId;
	private String remark;
	
	
	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}
	public String getBusinessId() {
		return businessId;
	}
	public void setContractId(String contractId) {
		this.contractId = contractId;
	}
	public String getContractId() {
		return contractId;
	}
	public void setCodeId(String codeId) {
		this.codeId = codeId;
	}
	public String getCodeId() {
		return codeId;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getRemark() {
		return remark;
	}
	

}
