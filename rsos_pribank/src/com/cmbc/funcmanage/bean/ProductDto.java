package com.cmbc.funcmanage.bean;

import java.io.Serializable;
import java.util.List;

import rsos.framework.utils.CalendarUtil;
import rsos.framework.utils.UUIDGenerator;

public class ProductDto implements Serializable {
	private static final long serialVersionUID = -7595327692707250590L;
	private List<ProductRedemptionInterval> insertedPRIlList;
	private List<ProductRedemptionInterval> updatePRIlList;
	private List<ProductRedemptionInterval> deletedPRIlList;
	
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
	
	
     public Product initProduct(){
    	 Product product = new Product();
    	 product.setProductId(UUIDGenerator.generateShortUuid());
    	 product.setProductCode(productCode);
    	 product.setProductName(productName);
    	 product.setBenefitDate(benefitDate);
    	 product.setDueDate(dueDate);
    	 product.setPlannedBenefit(plannedBenefit);
    	 product.setIsRoll(isRoll);
    	 product.setAddTime(CalendarUtil.getDateTimeStr());
    	 if(redemptionIntervalId!=null&&redemptionIntervalId.length()>0){
    		 product.setRedemptionIntervalId(redemptionIntervalId);
    		 product.setOpenDay(openDay);
    		 product.setRedeemBegin(redeemBegin);
    		 product.setRedeemEnd(redeemEnd);
    		 product.setRollBenefit(rollBenefit);
    	 }
		 return product;
     }
     
     public Product initProduct(String productId){
    	 Product product = new Product();
    	 product.setProductId(productId);
    	 product.setProductCode(productCode);
    	 product.setProductName(productName);
    	 product.setBenefitDate(benefitDate);
    	 product.setDueDate(dueDate);
    	 product.setPlannedBenefit(plannedBenefit);
    	 product.setIsRoll(isRoll);
    	 product.setAddTime(CalendarUtil.getDateTimeStr());
    	 if(redemptionIntervalId!=null&&redemptionIntervalId.length()>0){
    		 product.setRedemptionIntervalId(redemptionIntervalId);
    		 product.setOpenDay(openDay);
    		 product.setRedeemBegin(redeemBegin);
    		 product.setRedeemEnd(redeemEnd);
    		 product.setRollBenefit(rollBenefit);
    	 }
		 return product;
     }
     
     public Product updateProduct(Product product){
    	 product.setProductCode(productCode);
    	 product.setProductName(productName);
    	 product.setBenefitDate(benefitDate);
    	 product.setDueDate(dueDate);
    	 product.setPlannedBenefit(plannedBenefit);
    	 product.setIsRoll(isRoll);
    	 product.setEditTime(CalendarUtil.getDateTimeStr());
    	 if(redemptionIntervalId!=null&&redemptionIntervalId.length()>0){
    		 product.setRedemptionIntervalId(redemptionIntervalId);
    		 product.setOpenDay(openDay);
    		 product.setRedeemBegin(redeemBegin);
    		 product.setRedeemEnd(redeemEnd);
    		 product.setRollBenefit(rollBenefit);
    	 }else if(product.getRedemptionIntervalId()!=null&&product.getRedemptionIntervalId().length()>0){
    		 System.out.println("No Change the InRedemptionInterval!");
    	 }else{
    		 product.setRedemptionIntervalId("");
    		 product.setOpenDay("");
    		 product.setRedeemBegin("");
    		 product.setRedeemEnd("");
    		 product.setRollBenefit("");
    	 }
		return product;
    	 
     }
    
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
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
	public void setInsertedPRIlList(List<ProductRedemptionInterval> insertedPRIlList) {
		this.insertedPRIlList = insertedPRIlList;
	}
	public List<ProductRedemptionInterval> getInsertedPRIlList() {
		return insertedPRIlList;
	}
	public void setUpdatePRIlList(List<ProductRedemptionInterval> updatePRIlList) {
		this.updatePRIlList = updatePRIlList;
	}
	public List<ProductRedemptionInterval> getUpdatePRIlList() {
		return updatePRIlList;
	}
	public void setDeletedPRIlList(List<ProductRedemptionInterval> deletedPRIlList) {
		this.deletedPRIlList = deletedPRIlList;
	}
	public List<ProductRedemptionInterval> getDeletedPRIlList() {
		return deletedPRIlList;
	}
	public String getRedemptionIntervalId() {
		return redemptionIntervalId;
	}
	public void setRedemptionIntervalId(String redemptionIntervalId) {
		this.redemptionIntervalId = redemptionIntervalId;
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
	
	

}
