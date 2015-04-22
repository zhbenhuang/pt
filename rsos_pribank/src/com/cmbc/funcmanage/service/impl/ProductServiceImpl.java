package com.cmbc.funcmanage.service.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import rsos.framework.constant.GlobalConstants;
import rsos.framework.constant.StaticVariable;
import rsos.framework.exception.AppException;
import rsos.framework.utils.CalendarUtil;

import com.cmbc.funcmanage.bean.Product;
import com.cmbc.funcmanage.bean.ProductDto;
import com.cmbc.funcmanage.bean.ProductInfo;
import com.cmbc.funcmanage.bean.ProductRedemptionInterval;
import com.cmbc.funcmanage.dao.ProductDao;
import com.cmbc.funcmanage.dao.ProductRedemptionIntervalDao;
import com.cmbc.funcmanage.service.ProductService;

public class ProductServiceImpl implements ProductService{
	private Logger log = Logger.getLogger(GlobalConstants.USER_ACCESS_LOGGER);
	private ProductDao productDao;
	private ProductRedemptionIntervalDao productRedemptionIntervalDao;
	
	public void setProductDao(ProductDao productDao) {
		this.productDao = productDao;
	}
	
	public void setProductRedemptionIntervalDao(
			ProductRedemptionIntervalDao productRedemptionIntervalDao) {
		this.productRedemptionIntervalDao = productRedemptionIntervalDao;
	}

	@Override
	public List<Product> findByPagination(String table,int currentPage , int pageSize ,Map<String,Object> m)throws AppException{
		try{
			return productDao.findByPagination(table,currentPage,pageSize,m);
		}catch (Exception ex) {
			ex.printStackTrace();
			throw new AppException("E000011",new String[]{m.toString()});
		}
	}
	
	@Override
	public int getTotal(Map<String,Object> m) throws AppException{
		try{
			int count = productDao.getTotal(m);
			return count;
		}catch (Exception ex) {
			ex.printStackTrace();
			throw new AppException("E000011",new String[]{m.toString()});
		}
		
	}
	
	@Override
	public void updateProRedem(Product product) throws AppException {

		try{
			productDao.updateProRedem(product);
		}catch(Exception e){
			e.printStackTrace();
			throw new AppException("E000006");
		}
		
	}
	@Override
	public List<Product> findByPage(String table, int currentPage,
			int pageSize, Map<String, Object> m) throws AppException {
		try{
			return productDao.findByPage(table,currentPage,pageSize,m);
		}catch(Exception e){
			e.printStackTrace();
			throw new AppException("E000011",new String[]{m.toString()});
		}
		
	}
	@Override
	public int getTotal(String table, Map<String, Object> m) throws AppException {
		try{
			return productDao.getTotal(table,m);
		}catch(Exception e){
			e.printStackTrace();
			throw new AppException("E000011",new String[]{m.toString()});
		}
		
	}

	@Override
	public void deleteProduct(String[] productIds) throws AppException {
		try {
			for(String productId:productIds){
				productRedemptionIntervalDao.deleteProRedemps(productId);
				productDao.deleteProduct(productId);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new AppException("E000005");
		}
		
	}
	@Override
	public void modifyProduct(Product product) throws AppException {
		try {
			productDao.update(product);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new AppException("E000006");
		}
		
	}
	@Override
	public void saveProduct(Product product) throws AppException {
		try {
			productDao.insert(product);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new AppException("E000007");
		}
	}
	@Override
	public Product findById(String productId) throws AppException {
		try {
			return productDao.selectBy(productId);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new AppException("E000011",new String[]{productId});
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void saveProduct(ProductDto productDto) throws AppException {
		try{
			log.info("----saveProduct...");
			Product product = productDto.initProduct();
			List<ProductRedemptionInterval> insertPRIList = productDto.getInsertedPRIlList();
			if(insertPRIList!=null&&insertPRIList.size()>0){
				Iterator iterator = insertPRIList.iterator();
				while(iterator.hasNext()){
					ProductRedemptionInterval productRedemptionInterval = (ProductRedemptionInterval)iterator.next();
					productRedemptionInterval.setProductId(product.getProductId());
					productDao.updateContractRedemptionIntervalId(product.getProductId(),productRedemptionInterval);
					productRedemptionIntervalDao.insert(productRedemptionInterval);
				}
			}
			productDao.insert(product);
		}catch(Exception e){
			e.printStackTrace();
			throw new AppException("E000007");
		}
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public void updateProduct(ProductDto productDto,String productId) throws AppException {
		try{
			log.info("----updateProduct...");
			Product product = productDao.selectBy(productId);
			product = productDto.updateProduct(product);
			product.setEditTime(CalendarUtil.getDateTimeStr());
			
			List<ProductRedemptionInterval> insertPRIList = productDto.getInsertedPRIlList();
			if(insertPRIList!=null&&insertPRIList.size()>0){
				Iterator iterator = insertPRIList.iterator();
				while(iterator.hasNext()){
					ProductRedemptionInterval productRedemptionInterval = (ProductRedemptionInterval)iterator.next();
					productRedemptionInterval.setProductId(productId);
					productDao.updateContractRedemptionIntervalId(product.getProductId(),productRedemptionInterval);
					productRedemptionIntervalDao.insert(productRedemptionInterval);
					product = adjustRedemptionInterval(product,productRedemptionInterval);
				}
			}
			List<ProductRedemptionInterval> updatePRIList = productDto.getUpdatePRIlList();
			if(updatePRIList!=null&&updatePRIList.size()>0){
				Iterator iterator = updatePRIList.iterator();
				while(iterator.hasNext()){
					ProductRedemptionInterval productRedemptionInterval = (ProductRedemptionInterval)iterator.next();
					productRedemptionIntervalDao.update(productRedemptionInterval);
					product = adjustRedemptionInterval(product,productRedemptionInterval);
				}
			}
			List<ProductRedemptionInterval> deletePRIList = productDto.getDeletedPRIlList();
			if(deletePRIList!=null&&deletePRIList.size()>0){
				Iterator iterator = deletePRIList.iterator();
				while(iterator.hasNext()){
					ProductRedemptionInterval productRedemptionInterval = (ProductRedemptionInterval)iterator.next();
					if(productRedemptionInterval.getRedemptionIntervalId().equals(product.getRedemptionIntervalId())){
						product.setRedemptionIntervalId("");
			    		product.setOpenDay("");
			    		product.setRedeemBegin("");
			    		product.setRedeemEnd("");
			    		product.setRollBenefit("");
					}
					productRedemptionIntervalDao.deleteBy(productRedemptionInterval);
				}
			}
			productDao.update(product);
		}catch(Exception e){
			e.printStackTrace();
			throw new AppException("E000006");
		}
	}

	private Product adjustRedemptionInterval(Product product,
			ProductRedemptionInterval pri) {
		if(product.getRedemptionIntervalId()!=null&&product.getRedemptionIntervalId().equals(pri.getRedemptionIntervalId())){
			if(!pri.getRedemptionStatus().equals(StaticVariable.INREDEMPTION)){
				product.setRedemptionIntervalId("");
	    		product.setOpenDay("");
	    		product.setRedeemBegin("");
	    		product.setRedeemEnd("");
	    		product.setRollBenefit("");
			}
		}
		return product;
	}

	@Override
	public String findProductIdByCode(String productCode) throws AppException {
		try{
			return productDao.findProductIdByCode(productCode);
		}catch(Exception e){
			e.printStackTrace();
			throw new AppException("E000011",new String[]{productCode});
		}
	}

	@Override
	public Product findProductByproductCode(String productCode)
			throws AppException {
		String productId = productDao.findProductIdByCode(productCode);
		if(productId!=null&&productId.length()>0){
			return productDao.selectBy(productId);
		}else{
			return null;
		}
		
	}

	@Override
	public List<Product> findAllProduct() throws AppException {
		return productDao.findAllRollProduct();
	}

	
	
	@Override
	public List<ProductInfo> findAllProductInfo() throws AppException {
		return productDao.findAllProductInfo();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void insertProductDtos(List<ProductDto> productDtoList)
			throws AppException {
		try{
			Iterator iterator = productDtoList.iterator();
			while(iterator.hasNext()){
				ProductDto productDto = (ProductDto)iterator.next();
				String productCode = productDto.getProductCode();
				if(productDao.isNotCreate(productCode)){
					String productId = productDto.getProductId();
					Product product = productDto.initProduct(productId);
					productDao.insert(product);
					List<ProductRedemptionInterval> insertedPRIlList = productDto.getInsertedPRIlList();
					if(insertedPRIlList!=null){
						insertAllPRI(insertedPRIlList);
					}
					productDao.updateProductInfoCreated(productCode);
				}else{
					throw new AppException("E000025");
				}
			}
		}catch(AppException aE){
			throw aE;
		}catch(Exception e){
			throw new AppException("E000004");
		}
		
	}

	@SuppressWarnings("unchecked")
	private void insertAllPRI(List<ProductRedemptionInterval> insertedPRIlList) {
		Iterator iterator = insertedPRIlList.iterator();
		while(iterator.hasNext()){
			ProductRedemptionInterval pri = (ProductRedemptionInterval)iterator.next();
			productRedemptionIntervalDao.insert(pri);
		}
	}
	
}

