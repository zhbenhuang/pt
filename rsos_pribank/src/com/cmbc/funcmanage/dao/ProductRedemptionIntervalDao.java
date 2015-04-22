package com.cmbc.funcmanage.dao;

import java.util.List;

import rsos.framework.db.EasyBaseDao;

import com.cmbc.funcmanage.bean.ProductRedemptionInterval;
import com.cmbc.reportStatistic.bean.RedempIntervalReport;

public interface ProductRedemptionIntervalDao extends EasyBaseDao<ProductRedemptionInterval>{
	
	public int getTotal(String productCode);

	public List<ProductRedemptionInterval> findByPagenation(String productCode,
			int currentPage, int pageSize);

	public void deleteProRedemp(String productRedemptionId);

	public List<ProductRedemptionInterval> findRedempByProductId(
			String productId);

	public List<RedempIntervalReport> findRedempIntervalReportsByProductId(
			String productId);

	public void deleteProRedemps(String productId);
}
