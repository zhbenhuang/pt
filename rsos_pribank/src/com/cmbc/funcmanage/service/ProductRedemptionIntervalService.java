package com.cmbc.funcmanage.service;

import java.util.List;

import rsos.framework.exception.AppException;

import com.cmbc.funcmanage.bean.ProductRedemptionInterval;
import com.cmbc.reportStatistic.bean.RedempIntervalReport;

public interface ProductRedemptionIntervalService{
	
	public int getTotal(String productCode) throws AppException;

	public List<ProductRedemptionInterval> findByPagenation(String productCode, int currentPage,
			int pageSize) throws AppException;

	public void saveProRedemp(ProductRedemptionInterval productRedemptionInterval) throws AppException;

	public void deleteProRedemp(String productRedemptionId)throws AppException;

	public void modifyProRedemp(
			ProductRedemptionInterval productRedemptionInterval)throws AppException;

	public ProductRedemptionInterval findById(String redemptionIntervalId)throws AppException;

	public List<ProductRedemptionInterval> findRedempByProductId(String productId)throws AppException;

	public List<RedempIntervalReport> findRedempIntervalReportsByProductId(
			String productId)throws AppException;
}
