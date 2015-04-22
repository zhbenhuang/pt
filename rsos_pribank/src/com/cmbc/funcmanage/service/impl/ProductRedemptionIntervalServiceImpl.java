package com.cmbc.funcmanage.service.impl;

import java.util.List;


import rsos.framework.exception.AppException;

import com.cmbc.funcmanage.bean.ProductRedemptionInterval;
import com.cmbc.funcmanage.dao.ProductRedemptionIntervalDao;
import com.cmbc.funcmanage.service.ProductRedemptionIntervalService;
import com.cmbc.reportStatistic.bean.RedempIntervalReport;


public class ProductRedemptionIntervalServiceImpl implements ProductRedemptionIntervalService {
	private ProductRedemptionIntervalDao productRedemptionIntervalDao;
	
	public void setProductRedemptionIntervalDao(
			ProductRedemptionIntervalDao productRedemptionIntervalDao) {
		this.productRedemptionIntervalDao = productRedemptionIntervalDao;
	}
	
	@Override
	public List<ProductRedemptionInterval> findByPagenation(String productId, int currentPage,
			int pageSize) throws AppException {
		try{
			return productRedemptionIntervalDao.findByPagenation(productId,currentPage,pageSize);
		}catch (Exception ex) {
			ex.printStackTrace();
			throw new AppException("E000011",new String[]{productId});
		}
		
	}
	@Override
	public int getTotal(String productId) throws AppException {
		try{
			return productRedemptionIntervalDao.getTotal(productId);
		}catch (Exception ex) {
			ex.printStackTrace();
			throw new AppException("E000011",new String[]{productId});
		}
		
	}
	@Override
	public void saveProRedemp(
			ProductRedemptionInterval productRedemptionInterval) throws AppException {
		try {
			productRedemptionIntervalDao.insert(productRedemptionInterval);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new AppException("E000007");
		}
	}
	@Override
	public void deleteProRedemp(String productRedemptionId) throws AppException {
		try {
			productRedemptionIntervalDao.deleteProRedemp(productRedemptionId);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new AppException("E000005");
		}
		
	}
	@Override
	public void modifyProRedemp(
			ProductRedemptionInterval productRedemptionInterval)
			throws AppException {
		try {
			productRedemptionIntervalDao.update(productRedemptionInterval);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new AppException("E000006");
		}
		
	}
	@Override
	public ProductRedemptionInterval findById(String redemptionIntervalId)
			throws AppException {
		try {
			return productRedemptionIntervalDao.selectBy(redemptionIntervalId);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new AppException("E000011",new String[]{redemptionIntervalId});
		}
	}

	@Override
	public List<ProductRedemptionInterval> findRedempByProductId(
			String productId) throws AppException {
		try{
			return productRedemptionIntervalDao.findRedempByProductId(productId);
		}catch (Exception ex) {
			ex.printStackTrace();
			throw new AppException("E000011",new String[]{productId});
		}
	}

	@Override
	public List<RedempIntervalReport> findRedempIntervalReportsByProductId(
			String productId) throws AppException {
		try{
			return productRedemptionIntervalDao.findRedempIntervalReportsByProductId(productId);
		}catch (Exception ex) {
			ex.printStackTrace();
			throw new AppException("E000011",new String[]{productId});
		}
	}

}
