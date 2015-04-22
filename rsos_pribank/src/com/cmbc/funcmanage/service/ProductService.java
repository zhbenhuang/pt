package com.cmbc.funcmanage.service;
import java.util.List;
import java.util.Map;

import rsos.framework.exception.AppException;

import com.cmbc.funcmanage.bean.Product;
import com.cmbc.funcmanage.bean.ProductDto;
import com.cmbc.funcmanage.bean.ProductInfo;


public interface ProductService{

	/**
	 * 分页查询,查询条件是产品名、产品编号、赎回区间、开放日
	 * @param table
	 * @param currentPage
	 * @param pageSize
	 * @param m
	 * @return
	 * @throws Exception
	 */
	public List<Product> findByPagination(String table, int currentPage , int pageSize ,Map<String,Object> m)throws AppException;
	
	/**
	 * 更改产品赎回区间的状态
	 * @param product
	 */
	public void updateProRedem(Product product)throws AppException;
	

	/**
	 * 分页查询产品信息，查询条件是产品名称、产品编码
	 * @param table
	 * @param currentPage
	 * @param pageSize
	 * @param m
	 * @return
	 */
	public List<Product> findByPage(String table, int currentPage,
			int pageSize, Map<String, Object> m)throws AppException;

	/**
	 * 查询结果总数
	 * @param table
	 * @param m
	 * @return
	 */
	public int getTotal(String table, Map<String, Object> m)throws AppException;

	/**
	 * 查询结果总数
	 * @param m
	 * @return
	 */
	public int getTotal(Map<String, Object> m)throws AppException;
	
	public void deleteProduct(String[] productIds) throws AppException;
	public void saveProduct(Product product) throws AppException;
	public void modifyProduct(Product product) throws AppException;

	public Product findById(String productId)throws AppException;

	public void saveProduct(ProductDto productDto)throws AppException;

	public void updateProduct(ProductDto productDto, String productId)throws AppException;

	public String findProductIdByCode(String productCode)throws AppException;

	public Product findProductByproductCode(String productCode)throws AppException;

	public List<Product> findAllProduct()throws AppException;

	public List<ProductInfo> findAllProductInfo()throws AppException;

	public void insertProductDtos(List<ProductDto> productDtoList)throws AppException;

	
	
}