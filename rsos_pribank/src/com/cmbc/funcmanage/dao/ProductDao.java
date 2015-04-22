package com.cmbc.funcmanage.dao;
import java.util.List;
import java.util.Map;

import rsos.framework.db.EasyBaseDao;

import com.cmbc.funcmanage.bean.Product;
import com.cmbc.funcmanage.bean.ProductDto;
import com.cmbc.funcmanage.bean.ProductInfo;
import com.cmbc.funcmanage.bean.ProductRedemptionInterval;

public interface ProductDao extends EasyBaseDao<Product>{
	
	public List<Product> findByPagination(String table, int currentPage, int pageSize, Map<String, Object> m);
	
	/**
	 * 通过产品ID(productId)查找关联的赎回区间ID(productRedemptionId)
	 * @param productId
	 * @return
	 */
	public List findRedemptionIds(String productId);
	
	public void updateProRedem(Product product);

	public List<Product> findByPage(String table, int currentPage,
			int pageSize, Map<String, Object> m);

	public int getTotal(String table, Map<String, Object> m);

	public int getTotal(Map<String, Object> m);

	public void deleteProduct(String productId);

	public String findProductIdByName(String productName);

	public String findProductIdByCode(String productCode);

	public List<Product> findAllRollProduct();

	public String findProductCodeByName(String productName);

	public void updateContractRedemptionIntervalId(String productId,
			ProductRedemptionInterval productRedemptionInterval);

	public List<ProductInfo> findAllProductInfo();

	public boolean isNotCreate(String productCode);

	public void updateProductInfoCreated(String productCode);

}