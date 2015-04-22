package com.cmbc.funcmanage.bean;

import java.io.Serializable;
public class Contract implements Serializable
{
	 private static final long serialVersionUID = 1L;
	 private String contractId;
	 
	 private String customeId;
     private String customeName;
     private String saleDate;
     private String productType;
     private String productCode;
     private String productName;
     private String money;
     private String signAccount;
     private String saleManager;
     private String businessManager;
     private String belongDepartment;
     private String belongDepartmentId;
     private String signDepartment;
     private String signDepartmentId;
     
     private String addDate;	//添加时间
     private String modifyDate; //修改时间
     private String operatorName;//操作人
     private String contractInfoStatus;		//合同信息是否完整
     
     private String handStatus;	//合同移交状态,标记合同是否提交给分行
     private String handDate;	//合同移交到分行的时间
     private String handXinTuoDate;	//分行移交至信托的时间
     private String handBackDate; //合同移交返回时间
     private String getContractDate; //合同领取时间
     
     private String redempStatus; //合同赎回状态
     private String redempStartDate;//合同赎回开始时间
     private String redempConformDate;//分行确认赎回时间
     private String redempDate;   //合同赎回的时间
     private String redemptionIntervalId; //如果客户对应的合同已赎回,对应有一个赎回区间
     private String noticeStatus; //是否生成通知
     
     private String codeId;
     private String businessId;	//用于流程的businessId
     private String remark;  //合同流程备注
     
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getCustomeId() {
		return customeId;
	}
	public void setCustomeId(String customeId) {
		this.customeId = customeId;
	}
	public String getCustomeName() {
		return customeName;
	}
	public void setCustomeName(String customeName) {
		this.customeName = customeName;
	}
	public String getSaleDate() {
		return saleDate;
	}
	public void setSaleDate(String saleDate) {
		this.saleDate = saleDate;
	}
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getMoney() {
		return money;
	}
	public void setMoney(String money) {
		this.money = money;
	}
	public String getSignAccount() {
		return signAccount;
	}
	public void setSignAccount(String signAccount) {
		this.signAccount = signAccount;
	}
	public String getSaleManager() {
		return saleManager;
	}
	public void setSaleManager(String saleManager) {
		this.saleManager = saleManager;
	}
	public String getBusinessManager() {
		return businessManager;
	}
	public void setBusinessManager(String businessManager) {
		this.businessManager = businessManager;
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
	public void setCodeId(String codeId) {
		this.codeId = codeId;
	}
	public String getCodeId() {
		return codeId;
	}
	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}
	public String getBusinessId() {
		return businessId;
	}
	public void setBelongDepartmentId(String belongDepartmentId) {
		this.belongDepartmentId = belongDepartmentId;
	}
	public String getBelongDepartmentId() {
		return belongDepartmentId;
	}
	public void setSignDepartmentId(String signDepartmentId) {
		this.signDepartmentId = signDepartmentId;
	}
	public String getSignDepartmentId() {
		return signDepartmentId;
	}
	
	public void setHandStatus(String handStatus) {
		this.handStatus = handStatus;
	}
	public String getHandStatus() {
		return handStatus;
	}
	public void setRedempStatus(String redempStatus) {
		this.redempStatus = redempStatus;
	}
	public String getRedempStatus() {
		return redempStatus;
	}
	public void setNoticeStatus(String noticeStatus) {
		this.noticeStatus = noticeStatus;
	}
	public String getNoticeStatus() {
		return noticeStatus;
	}
	public void setHandDate(String handDate) {
		this.handDate = handDate;
	}
	public String getHandDate() {
		return handDate;
	}
	public void setRedempDate(String redempDate) {
		this.redempDate = redempDate;
	}
	public String getRedempDate() {
		return redempDate;
	}
	public void setRedemptionIntervalId(String redemptionIntervalId) {
		this.redemptionIntervalId = redemptionIntervalId;
	}
	public String getRedemptionIntervalId() {
		return redemptionIntervalId;
	}
	public void setHandBackDate(String handBackDate) {
		this.handBackDate = handBackDate;
	}
	public String getHandBackDate() {
		return handBackDate;
	}
	public void setRedempStartDate(String redempStartDate) {
		this.redempStartDate = redempStartDate;
	}
	public String getRedempStartDate() {
		return redempStartDate;
	}
	public void setRedempConformDate(String redempConformDate) {
		this.redempConformDate = redempConformDate;
	}
	public String getRedempConformDate() {
		return redempConformDate;
	}
	public void setHandXinTuoDate(String handXinTuoDate) {
		this.handXinTuoDate = handXinTuoDate;
	}
	public String getHandXinTuoDate() {
		return handXinTuoDate;
	}
	public void setContractId(String contractId) {
		this.contractId = contractId;
	}
	public String getContractId() {
		return contractId;
	}
	public void setGetContractDate(String getContractDate) {
		this.getContractDate = getContractDate;
	}
	public String getGetContractDate() {
		return getContractDate;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getRemark() {
		return remark;
	}
	public void setAddDate(String addDate) {
		this.addDate = addDate;
	}
	public String getAddDate() {
		return addDate;
	}
	public void setModifyDate(String modifyDate) {
		this.modifyDate = modifyDate;
	}
	public String getModifyDate() {
		return modifyDate;
	}
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}
	public String getOperatorName() {
		return operatorName;
	}
	public void setContractInfoStatus(String contractInfoStatus) {
		this.contractInfoStatus = contractInfoStatus;
	}
	public String getContractInfoStatus() {
		return contractInfoStatus;
	}
}