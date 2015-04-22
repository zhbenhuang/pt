package com.cmbc.funcmanage.action;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import rsos.framework.constant.Constants;
import rsos.framework.constant.StaticVariable;
import rsos.framework.easyui.EasyGridList;
import rsos.framework.exception.AppException;
import rsos.framework.utils.CalendarUtil;
import rsos.framework.utils.UUIDGenerator;

import com.cmbc.funcmanage.bean.Product;
import com.cmbc.funcmanage.bean.ProductDto;
import com.cmbc.funcmanage.bean.ProductInfo;
import com.cmbc.funcmanage.bean.ProductRedemptionInterval;
import com.opensymphony.xwork2.ActionContext;


import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class ProductAction extends FuncBaseAction {
	private Logger log = Logger.getLogger(ProductAction.class);
	private static final long serialVersionUID = 1L;
	private int total;
	
	public void saveProduct() {
		try {
			printParameters();
			String productObject = ServletActionContext.getRequest().getParameter("productObject");
			JSONObject productJson = StringToJson(productObject).get(0);
			String productCode = productJson.getString("productCode");
			Product product = productService.findProductByproductCode(productCode);
			if(product!=null){
				String message = "{'message':'系统中已存在相同编号的产品,不可重复添加!','retCode':'E999999'}";
				JSONObject jsonObject = (JSONObject) JSONSerializer.toJSON(message);
				this.writeJsonSuccess(jsonObject.toString());
			}else{
				ProductDto productDto = new ProductDto();
				productDto = this.generatorProductDto(productDto);
				productService.saveProduct(productDto);
				String message = "{'message':'产品添加成功!','retCode':'A000000'}";
				JSONObject jsonObject = (JSONObject) JSONSerializer.toJSON(message);
				this.writeJsonSuccess(jsonObject.toString());
			}
		}catch(AppException ex){
			String str = "{'message':'产品添加失败,请重新登录后重试!','retCode':'E999999'}";
			JSONObject jsonObject = (JSONObject) JSONSerializer.toJSON(str);
			this.writeJsonSuccess(jsonObject.toString());
			ex.printStackTrace();
		}catch (Exception e) {
			String message = "{'message':'系统故障,请联系管理员!','retCode':'E999999'}";
			JSONObject jsonObject = (JSONObject) JSONSerializer.toJSON(message);
			this.writeJsonSuccess(jsonObject.toString());
			e.printStackTrace();
		}
	}
	/**
	 * 批量删除产品信息及其关联的赎回区间
	 */
	@SuppressWarnings("unchecked")
	public void deleteProduct(){	
		try{
			Map maps = (Map)ActionContext.getContext().getParameters();
			String[] productIds = (String[]) maps.get("productIds");
			productService.deleteProduct(productIds);
			String str = "{'message':'删除成功!'}";
			JSONObject jsonObject = (JSONObject) JSONSerializer.toJSON(str);
			this.writeJsonSuccess(jsonObject.toString());
		}catch(AppException ex){
			String str = "{'message':'删除失败,请重新登录后重试!'}";
			JSONObject jsonObject = (JSONObject) JSONSerializer.toJSON(str);
			this.writeJsonSuccess(jsonObject.toString());
			ex.printStackTrace();
		}catch(Exception e){
			String str = "{'message':'系统错误导致删除失败,联系管理员!'}";
			JSONObject jsonObject = (JSONObject) JSONSerializer.toJSON(str);
			this.writeJsonSuccess(jsonObject.toString());
			e.printStackTrace();
		}
	}
	
	public void updateProduct() {
		try {
			String productObject = ServletActionContext.getRequest().getParameter("productObject");
			JSONObject productJson = StringToJson(productObject).get(0);
			String productId = productJson.getString("productId");
			
			printParameters();
			ProductDto productDto = new ProductDto();
			productDto = this.generatorProductDto(productDto);
			productService.updateProduct(productDto,productId);
			String str = "{'message':'产品信息更新成功!','retCode':'A000000'}";
			JSONObject jsonObject = (JSONObject) JSONSerializer.toJSON(str);
			this.writeJsonSuccess(jsonObject.toString());
		}catch(AppException ex){
			String str = "{'message':'产品信息更新失败,请重新登录后重试!','retCode':'E999999'}";
			JSONObject jsonObject = (JSONObject) JSONSerializer.toJSON(str);
			this.writeJsonSuccess(jsonObject.toString());
		}catch(Exception e){
			String str = "{'message':'系统错误导致删除失败,联系管理员!','retCode':'E999999'}";
			JSONObject jsonObject = (JSONObject) JSONSerializer.toJSON(str);
			this.writeJsonSuccess(jsonObject.toString());
			e.printStackTrace();
		}
	 }
	
	@SuppressWarnings("unchecked")
	public ProductDto generatorProductDto(ProductDto productDto) throws AppException{
		String productObject = ServletActionContext.getRequest().getParameter("productObject");
		JSONObject productJson = StringToJson(productObject).get(0);
		String productCode = productJson.getString("productCode");
		String productName = productJson.getString("productName");
		String benefitDate = productJson.getString("benefitDate");
		String dueDate = productJson.getString("dueDate");
		String plannedBenefit = productJson.getString("plannedBenefit");
		String isRoll = productJson.getString("isRoll");
		productDto.setProductCode(productCode);
		productDto.setProductName(productName);
		productDto.setBenefitDate(benefitDate);
		productDto.setDueDate(dueDate);
		productDto.setPlannedBenefit(plannedBenefit);
		productDto.setIsRoll(isRoll);
		try{
			String inserted = ServletActionContext.getRequest().getParameter("inserted");
			List<ProductRedemptionInterval> insertedPRIlList = new ArrayList<ProductRedemptionInterval>();
			if(!(inserted.equals("{}")||inserted.equals("[]"))){
				List<JSONObject> insertedJsonList = StringToJson(inserted);
				Iterator iterator = insertedJsonList.iterator();
				while(iterator.hasNext()){
					JSONObject insertedJson = (JSONObject)iterator.next();
					String openDay = insertedJson.getString("openDay");
					String redeemBegin = insertedJson.getString("redeemBegin");
					String redeemEnd = insertedJson.getString("redeemEnd");
					String rollBenefit = insertedJson.getString("rollBenefit");
					if(openDay!=null&&openDay.length()>0&&redeemBegin!=null&&redeemBegin.length()>0&&redeemEnd!=null&&redeemEnd.length()>0&&rollBenefit!=null&&rollBenefit.length()>0){
						ProductRedemptionInterval productRedemptionInterval = new ProductRedemptionInterval();
						productRedemptionInterval.setRedemptionIntervalId(UUIDGenerator.generateShortUuid());
						productRedemptionInterval.setRollBenefit(insertedJson.getString("rollBenefit"));
						productRedemptionInterval.setOpenDay(openDay.replaceAll("-", ""));
						productRedemptionInterval.setRedeemBegin(redeemBegin.replaceAll("-", ""));
						productRedemptionInterval.setRedeemEnd(redeemEnd.replaceAll("-", ""));
						String redemptionStatus = redemptionStatus(productRedemptionInterval);
						productRedemptionInterval.setRedemptionStatus(redemptionStatus);
						if(redemptionStatus.equals(StaticVariable.INREDEMPTION)){
							productDto.setRedemptionIntervalId(productRedemptionInterval.getRedemptionIntervalId());
							productDto.setRedeemBegin(productRedemptionInterval.getRedeemBegin());
							productDto.setRedeemEnd(productRedemptionInterval.getRedeemEnd());
							productDto.setRollBenefit(productRedemptionInterval.getRollBenefit());
							productDto.setOpenDay(productRedemptionInterval.getOpenDay());
						}
						insertedPRIlList.add(productRedemptionInterval);
					}else {
						continue;
					}
				}
				productDto.setInsertedPRIlList(insertedPRIlList);
			}
			String updated = ServletActionContext.getRequest().getParameter("updated");
			List<ProductRedemptionInterval> updatePRIlList = new ArrayList<ProductRedemptionInterval>();
			if(!(updated.equals("{}")||updated.equals("[]"))){
				List<JSONObject> updatedJsonList = StringToJson(updated);
				Iterator iterator = updatedJsonList.iterator();
				while(iterator.hasNext()){
					JSONObject updatedJson = (JSONObject)iterator.next();
					String openDay = updatedJson.getString("openDay");
					String redeemBegin = updatedJson.getString("redeemBegin");
					String redeemEnd = updatedJson.getString("redeemEnd");
					String rollBenefit = updatedJson.getString("rollBenefit");
					if(openDay!=null&&openDay.length()>0&&redeemBegin!=null&&redeemBegin.length()>0&&redeemEnd!=null&&redeemEnd.length()>0&&rollBenefit!=null&&rollBenefit.length()>0){
						ProductRedemptionInterval productRedemptionInterval = productRedemptionIntervalService.findById(updatedJson.getString("redemptionIntervalId"));
						productRedemptionInterval.setOpenDay(openDay.replaceAll("-", ""));
						productRedemptionInterval.setRedeemBegin(redeemBegin.replaceAll("-", ""));
						productRedemptionInterval.setRedeemEnd(redeemEnd.replaceAll("-", ""));
						productRedemptionInterval.setRollBenefit(rollBenefit);
						String redemptionStatus = redemptionStatus(productRedemptionInterval);
						productRedemptionInterval.setRedemptionStatus(redemptionStatus);
						if(redemptionStatus.equals(StaticVariable.INREDEMPTION)){
							productDto.setRedemptionIntervalId(productRedemptionInterval.getRedemptionIntervalId());
							productDto.setRedeemBegin(productRedemptionInterval.getRedeemBegin());
							productDto.setRedeemEnd(productRedemptionInterval.getRedeemEnd());
							productDto.setRollBenefit(productRedemptionInterval.getRollBenefit());
							productDto.setOpenDay(productRedemptionInterval.getOpenDay());
						}
						updatePRIlList.add(productRedemptionInterval);
					}
				}
				productDto.setUpdatePRIlList(updatePRIlList);
			}
			String deleted = ServletActionContext.getRequest().getParameter("deleted");
			List<ProductRedemptionInterval> deletedPRIlList = new ArrayList<ProductRedemptionInterval>();
			if(!(deleted.equals("{}")||deleted.equals("[]"))){
				List<JSONObject> deletedJsonList = StringToJson(deleted);
				int NUM = deletedJsonList.size();
				for(int i=0;i<NUM;i++){
					String deleteStr = deletedJsonList.get(i).getString("redemptionIntervalId");
					ProductRedemptionInterval productRedemptionInterval = productRedemptionIntervalService.findById(deleteStr);
					deletedPRIlList.add(productRedemptionInterval);
				}
				productDto.setDeletedPRIlList(deletedPRIlList);
			}
		}catch(AppException ex){
			throw ex;
		}catch(Exception e){
			e.printStackTrace();
			throw new AppException("E000011");
		}
		return productDto;
		
	}
	/*
	 * 系统自动判断赎回区间的状态
	 */
	private String redemptionStatus(ProductRedemptionInterval pri) {
		int openDay = Integer.parseInt(pri.getOpenDay());
		int redeemBegin = Integer.parseInt(pri.getRedeemBegin());
		int redeemEnd = Integer.parseInt(pri.getRedeemEnd());
		int currentDate = Integer.parseInt(CalendarUtil.formatDatetime(new Date(),CalendarUtil.DATEFORMAT_YYYYMMDD));
		if(wrongRedemption(pri)){
			return StaticVariable.WRONGREDEMP;
		}else if(openDay<currentDate){
			return StaticVariable.PASTOPENDAY;
		}else if(currentDate<=openDay&&currentDate>redeemEnd){
			return StaticVariable.PASTREDEMPTION;
		}else if(currentDate<=redeemEnd&&currentDate>=redeemBegin){
			return StaticVariable.INREDEMPTION;
		}else{
			return StaticVariable.STEPINGREDEMPTION;
		}
	}
	private boolean wrongRedemption(ProductRedemptionInterval pri) {
		int openDay = Integer.parseInt(pri.getOpenDay());
		int redeemBegin = Integer.parseInt(pri.getRedeemBegin());
		int redeemEnd = Integer.parseInt(pri.getRedeemEnd());
		if(redeemBegin>=redeemEnd){
			return true;
		}else if(openDay<=redeemEnd){
			return true;
		}else{
			return false;
		}
	}
	
	public void importProductAction(){
		try {
			printParameters();
			String productObject = ServletActionContext.getRequest().getParameter("productObject");
			JSONObject productJson = StringToJson(productObject).get(0);
			String benefitDate = productJson.getString("benefitDate");
			String dueDate = productJson.getString("dueDate");
			Date BeginDate = CalendarUtil.transformStringToDate(benefitDate, CalendarUtil.UP_ITEM_DATEFORMAT_DATE_1);
			Date endDate = CalendarUtil.transformStringToDate(dueDate, CalendarUtil.UP_ITEM_DATEFORMAT_DATE_1);
			if(BeginDate.after(endDate)||endDate.equals(BeginDate)){
				String message = "{'message':'起息日必须晚于到息日!','retCode':'E999999'}";
				JSONObject jsonObject = (JSONObject) JSONSerializer.toJSON(message);
				this.writeJsonSuccess(jsonObject.toString());
			}else{
				String productCode = productJson.getString("productCode");
				Product product = productService.findProductByproductCode(productCode);
				if(product!=null){
					String message = "{'message':'系统中已存在相同编号的产品,不可重复添加!','retCode':'E999999'}";
					JSONObject jsonObject = (JSONObject) JSONSerializer.toJSON(message);
					this.writeJsonSuccess(jsonObject.toString());
				}else{
					List<ProductDto> productDtoList = new ArrayList<ProductDto>();
					ProductInfo productInfo = new ProductInfo();
					productInfo = generatorProductInfo(productJson);
					ProductDto productDto = generatorProductDto(productInfo);
					productDtoList.add(productDto);
					productService.insertProductDtos(productDtoList);
					String message = "{'message':'产品添加成功!','retCode':'A000000'}";
					JSONObject jsonObject = (JSONObject) JSONSerializer.toJSON(message);
					this.writeJsonSuccess(jsonObject.toString());
				}
			}
		}catch(AppException ex){
			String str = "{'message':'产品添加失败,请重新登录后重试!','retCode':'E999999'}";
			JSONObject jsonObject = (JSONObject) JSONSerializer.toJSON(str);
			this.writeJsonSuccess(jsonObject.toString());
			ex.printStackTrace();
		}catch (Exception e) {
			String message = "{'message':'系统故障,请联系管理员!','retCode':'E999999'}";
			JSONObject jsonObject = (JSONObject) JSONSerializer.toJSON(message);
			this.writeJsonSuccess(jsonObject.toString());
			e.printStackTrace();
		}
	}
	
	private ProductInfo generatorProductInfo(JSONObject productJson) {
		ProductInfo productInfo = new ProductInfo();
		String isRoll = productJson.getString("isRoll");
		productInfo.setProductCode(productJson.getString("productCode"));
		productInfo.setProductName(productJson.getString("productName"));
		productInfo.setBenefitDate(productJson.getString("benefitDate"));
		productInfo.setDueDate(productJson.getString("dueDate"));
		productInfo.setPlannedBenefit(productJson.getString("plannedBenefit"));
		productInfo.setIsRoll(isRoll);
		if(isRoll.equals("是")){
			productInfo.setFirstOpenDay(productJson.getString("firstOpenDay"));
			productInfo.setRollIntervalSpan(productJson.getString("rollIntervalSpan"));
			productInfo.setRollBenefit(productJson.getString("rollBenefit"));
		}
		return productInfo;
	}
	@SuppressWarnings("unchecked")
	public void importProductsAction(){
		try{
			List<ProductInfo> productInfoList = new ArrayList<ProductInfo>();
			productInfoList = productService.findAllProductInfo();
			List<ProductDto> productDtoList = new ArrayList<ProductDto>();
			Iterator iterator = productInfoList.iterator();
			while(iterator.hasNext()){
				ProductInfo productInfo = (ProductInfo)iterator.next();
				ProductDto productDto = generatorProductDto(productInfo);
				productDtoList.add(productDto);
			}
			productService.insertProductDtos(productDtoList);
			String str = "{'message':'产品信息初始化成功!','retCode':'A000000'}";
			JSONObject jsonObject = (JSONObject) JSONSerializer.toJSON(str);
			this.writeJsonSuccess(jsonObject.toString());
		}catch(AppException ex){
			writeErrors(ex.getId());
			ex.printStackTrace();
		} catch (Exception e) {
			writeErrors(Constants.RETCODE_999999);
			e.printStackTrace();
		}
	}
	
	private ProductDto generatorProductDto(ProductInfo productInfo) throws Exception {
		try {
			ProductDto productDto = new ProductDto();
			productDto = generatorProductDto(productInfo,productDto);
			String isRoll = productInfo.getIsRoll();
			if(isRoll.equals("否")){
				return productDto;
			}else{
				List<ProductRedemptionInterval> insertedPRIlList = new ArrayList<ProductRedemptionInterval>();
				String dueDate = productInfo.getDueDate();
				Date endDate = CalendarUtil.transformStringToDate(dueDate, CalendarUtil.UP_ITEM_DATEFORMAT_DATE_1);
				Date limitDate = CalendarUtil.transformStringToDate(Constants.LIMITDATE, CalendarUtil.UP_ITEM_DATEFORMAT_DATE_1);
				if(endDate.after(limitDate)){
					endDate = limitDate;
				}
				
				String firstOpenDay = productInfo.getFirstOpenDay();
				Date openDay = CalendarUtil.transformStringToDate(firstOpenDay, CalendarUtil.UP_ITEM_DATEFORMAT_DATE_1);
				
				String rollBenefit = productInfo.getRollBenefit();
				int rollIntervalSpan = Integer.parseInt(productInfo.getRollIntervalSpan());
				
				while(endDate.after(openDay)||endDate.equals(openDay)){
					ProductRedemptionInterval pri = generatorPri(openDay,rollBenefit,productDto.getProductId());
					if(pri.getRedemptionStatus().equals(StaticVariable.INREDEMPTION)){
						productDto.setRedemptionIntervalId(pri.getRedemptionIntervalId());
						productDto.setOpenDay(pri.getOpenDay());
						productDto.setRedeemBegin(pri.getRedeemBegin());
						productDto.setRedeemEnd(pri.getRedeemEnd());
						productDto.setRollBenefit(pri.getRollBenefit());
					}
					insertedPRIlList.add(pri);
					openDay = CalendarUtil.add(openDay, CalendarUtil.MONTH, rollIntervalSpan);
				}
				productDto.setInsertedPRIlList(insertedPRIlList);
				return productDto;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	private ProductDto generatorProductDto(ProductInfo productInfo,ProductDto productDto) throws ParseException {
		try{
			productDto.setProductId(UUIDGenerator.generateShortUuid());
			productDto.setProductCode(productInfo.getProductCode());
			productDto.setProductName(productInfo.getProductName());
			String benefitDate = productInfo.getBenefitDate();
			benefitDate = CalendarUtil.transformStringToFormattedString(benefitDate, CalendarUtil.UP_ITEM_DATEFORMAT_DATE_1, CalendarUtil.DATEFORMAT_YYYYMMDD);
			productDto.setBenefitDate(benefitDate);
			String dueDate = productInfo.getDueDate();
			dueDate = CalendarUtil.transformStringToFormattedString(dueDate, CalendarUtil.UP_ITEM_DATEFORMAT_DATE_1, CalendarUtil.DATEFORMAT_YYYYMMDD);
			productDto.setDueDate(dueDate);
			productDto.setPlannedBenefit(productInfo.getPlannedBenefit());
			productDto.setIsRoll(productInfo.getIsRoll());
			return productDto;
		} catch (ParseException e) {
			throw e;
		}
	}
	
	private ProductRedemptionInterval generatorPri(Date openDay,String rollBenefit,String productId) throws Exception {
		try{
			Date redeemBeginDate = CalendarUtil.add(openDay, CalendarUtil.MONTH, -2);
			Date redeemEndDate = CalendarUtil.add(openDay, CalendarUtil.MONTH, -1);
			ProductRedemptionInterval pri = new ProductRedemptionInterval();
			pri.setRedemptionIntervalId(UUIDGenerator.generateShortUuid());
			pri.setProductId(productId);
			pri.setRollBenefit(rollBenefit);
			pri.setOpenDay(CalendarUtil.formatDatetime(openDay, CalendarUtil.DATEFORMAT_YYYYMMDD));
			pri.setRedeemBegin(CalendarUtil.formatDatetime(redeemBeginDate, CalendarUtil.DATEFORMAT_YYYYMMDD));
			pri.setRedeemEnd(CalendarUtil.formatDatetime(redeemEndDate, CalendarUtil.DATEFORMAT_YYYYMMDD));
			String redemptionStatus = redemptionStatus(pri);
			pri.setRedemptionStatus(redemptionStatus);
			return pri;
		}catch(Exception e){
			throw e;
		}
	}
	/**
	 * 获取产品列表
	 */
	public void getlist(){
		try{
			String pages = ServletActionContext.getRequest().getParameter("page");
			String rows = ServletActionContext.getRequest().getParameter("rows");
			String openDayBegin = ServletActionContext.getRequest().getParameter("openDayBegin");
			String openDayEnd = ServletActionContext.getRequest().getParameter("openDayEnd");
			String redeemBegin = ServletActionContext.getRequest().getParameter("redeemBegin");
			String redeemEnd = ServletActionContext.getRequest().getParameter("redeemEnd");
			
			int currentPage = Integer.parseInt(pages);
			int pageSize    = Integer.parseInt(rows);
			
			Map<String,Object> m = new HashMap<String,Object>();
			m = this.m1();
			List<Product> productlist = new ArrayList<Product>();
			if((openDayBegin!=null&&openDayBegin.length()>0)||(openDayEnd!=null&&openDayEnd.length()>0)
			   ||(redeemBegin!=null&&redeemBegin.length()>0)||(redeemEnd!=null&&redeemEnd.length()>0)){
				productlist = this.productService.findByPagination("productList_View",currentPage , pageSize , m);
				total = this.productService.getTotal("productList_View",m);
			}else{
				productlist = this.productService.findByPage("financialProduct",currentPage , pageSize , m);
				total = this.productService.getTotal(m);
			}
			
			EasyGridList<Product> plist = new EasyGridList<Product>();
			plist.setRows(productlist);
			plist.setTotal(total);
			plist.setRetCode(Constants.RETCODE_00000);
			plist.setMessage(getText(Constants.RETCODE_00000));
			log.info(getText(Constants.RETCODE_00000)+"---"+plist.toJson());
			writeJsonSuccess(plist.toJson());			
		}catch(AppException ex){
			writeErrors(ex.getId());
			ex.printStackTrace();
		} catch (Exception e) {
			writeErrors(Constants.RETCODE_999999);
			e.printStackTrace();
		}
	}
	/**
	 * 获取产品列表
	 */
	public void getProductList(){
		try{
			String pages = ServletActionContext.getRequest().getParameter("page");
			String rows = ServletActionContext.getRequest().getParameter("rows");
			int currentPage = Integer.parseInt(pages);
			int pageSize    = Integer.parseInt(rows);
			Map<String,Object> m = new HashMap<String,Object>();
			m = this.m1();
			List<Product> productlist = this.productService.findByPage("financialProduct",currentPage , pageSize , m);
			total = this.productService.getTotal(m);
			
			EasyGridList<Product> plist = new EasyGridList<Product>();
			plist.setRows(productlist);
			plist.setTotal(total);
			plist.setRetCode(Constants.RETCODE_00000);
			plist.setMessage(getText(Constants.RETCODE_00000));
			log.info(getText(Constants.RETCODE_00000)+"---"+plist.toJson());
			writeJsonSuccess(plist.toJson());	
		}catch(AppException ex){
			writeErrors(ex.getId());
			ex.printStackTrace();
		} catch (Exception e) {
			writeErrors(Constants.RETCODE_999999);
			e.printStackTrace();
		}
	}
	
	public void getProductByProductId(){
		try{
			printParameters();
			String productId = getHttpRequest().getParameter("productId");
			Product product = new Product();
			product = this.productService.findById(productId);
			JSONObject jsonObject = (JSONObject) JSONSerializer.toJSON(product);
			this.writeJsonSuccess(jsonObject.toString());
		}catch(AppException ex){
			writeErrors(ex.getId());
			ex.printStackTrace();
		}catch (Exception e) {
			writeErrors(Constants.RETCODE_999999);
			e.printStackTrace();
		}
	}
	
	/**
	 * 查询某个产品所有的赎回区间
	 * productId为参数
	 */
	public void getRedemptionList(){
		try{
			List<ProductRedemptionInterval> prilist = new ArrayList<ProductRedemptionInterval>();
			total = 0;
			String pages = ServletActionContext.getRequest().getParameter("page");
			String rows = ServletActionContext.getRequest().getParameter("rows");
			String productId = ServletActionContext.getRequest().getParameter("productId");
			int currentPage = Integer.parseInt(pages);
			int pageSize    = Integer.parseInt(rows);
			
			if(productId!=null){
				prilist = productRedemptionIntervalService.findByPagenation(productId,currentPage,pageSize);
				total = productRedemptionIntervalService.getTotal(productId);
			}
			
			EasyGridList<ProductRedemptionInterval> plist = new EasyGridList<ProductRedemptionInterval>();
			plist.setRows(prilist);
			plist.setTotal(total);
			plist.setRetCode(Constants.RETCODE_00000);
			plist.setMessage(getText(Constants.RETCODE_00000));
			log.info(getText(Constants.RETCODE_00000)+"---"+plist.toJson());
			writeJsonSuccess(plist.toJson());	
		}catch(AppException ex){
			writeErrors(ex.getId());
			ex.printStackTrace();
		} catch (Exception e) {
			writeErrors(Constants.RETCODE_999999);
			e.printStackTrace();
		}
	}
	
	public void getRedemptionByProductId(){
		try{
			List<ProductRedemptionInterval> prilist = new ArrayList<ProductRedemptionInterval>();
			String productId = ServletActionContext.getRequest().getParameter("productId");
			if(productId!=null){
				prilist = productRedemptionIntervalService.findRedempByProductId(productId);
			}
			EasyGridList<ProductRedemptionInterval> plist = new EasyGridList<ProductRedemptionInterval>();
			plist.setRows(prilist);
			plist.setRetCode(Constants.RETCODE_00000);
			plist.setMessage(getText(Constants.RETCODE_00000));
			log.info(getText(Constants.RETCODE_00000)+"---"+plist.toJson());
			writeJsonSuccess(plist.toJson());	
		}catch(AppException ex){
			writeErrors(ex.getId());
			ex.printStackTrace();
		} catch (Exception e) {
			writeErrors(Constants.RETCODE_999999);
			e.printStackTrace();
		}
	}
	
	public void getRedemptions(){
		try{
			List<ProductRedemptionInterval> prilist = new ArrayList<ProductRedemptionInterval>();
			total = 0;
			String pages = ServletActionContext.getRequest().getParameter("page");
			String rows = ServletActionContext.getRequest().getParameter("rows");
			String productCode = ServletActionContext.getRequest().getParameter("productCode");
			int currentPage = Integer.parseInt(pages);
			int pageSize    = Integer.parseInt(rows);
			if(productCode!=null){
				String productId = productService.findProductIdByCode(productCode);
				if(productId!=null){
					prilist = productRedemptionIntervalService.findByPagenation(productId,currentPage,pageSize);
					total = productRedemptionIntervalService.getTotal(productId);
				}
			}
			EasyGridList<ProductRedemptionInterval> plist = new EasyGridList<ProductRedemptionInterval>();
			plist.setRows(prilist);
			plist.setTotal(total);
			plist.setRetCode(Constants.RETCODE_00000);
			plist.setMessage(getText(Constants.RETCODE_00000));
			log.info(getText(Constants.RETCODE_00000)+"---"+plist.toJson());
			writeJsonSuccess(plist.toJson());	
		}catch(AppException ex){
			writeErrors(ex.getId());
			ex.printStackTrace();
		} catch (Exception e) {
			writeErrors(Constants.RETCODE_999999);
			e.printStackTrace();
		}
	}
	
	/**
	 * 将查询条件放入Map容器中
	 * @return
	 */
	public Map<String, Object> m1() {
		String productCode = ServletActionContext.getRequest().getParameter("productCode");
		String productName = ServletActionContext.getRequest().getParameter("productName");
		String openDayBegin = ServletActionContext.getRequest().getParameter("openDayBegin");
		String openDayEnd = ServletActionContext.getRequest().getParameter("openDayEnd");
		String redeemBegin = ServletActionContext.getRequest().getParameter("redeemBegin");
		String redeemEnd = ServletActionContext.getRequest().getParameter("redeemEnd");
		Map<String,Object> m = new HashMap<String,Object>();
		
		if(productCode!=null&&productCode.length()>0){
			m.put("productCode", productCode);
		}
		if(productName!=null&&productName.length()>0){
			m.put("productName",productName);
		}
		if(openDayBegin!=null&&openDayBegin.length()>0){
			m.put("openDayBegin",openDayBegin);
		}
		if(openDayEnd!=null&&openDayEnd.length()>0){
			m.put("openDayEnd",openDayEnd);
		}
		if(redeemBegin!=null&&redeemBegin.length()>0){
			m.put("redeemBegin",redeemBegin);
		}
		if(redeemEnd!=null&&redeemEnd.length()>0){
			m.put("redeemEnd",redeemEnd);
		}
		return m;
	}
	
	 /**
 	 * "(\\{[^{}]*\\})+"  //正则表达式，抽取出{}中的数据
 	 * [{"openDay":"2014-04-29","redeemBegin":"2014-04-29","redeemEnd":"2014-04-28","rollBenefit":"0.3"},
 	 * {"openDay":"2014-04-30","redeemBegin":"2014-04-29","redeemEnd":"2014-04-27","rollBenefit":"0.6"}]
 	 * 抽取出：{"openDay":"2014-04-29","redeemBegin":"2014-04-29","redeemEnd":"2014-04-28","rollBenefit":"0.3"}和
 	 * {"openDay":"2014-04-30","redeemBegin":"2014-04-29","redeemEnd":"2014-04-27","rollBenefit":"0.6"}
 	 * 然后转换为json类型的数据
 	 * 
 	 */
 	public List<JSONObject> StringToJson(String str){
 		String regEx = "(\\{[^{}]*\\})+";		
 		Pattern p=Pattern.compile(regEx);
 		Matcher m=p.matcher(str);
 		List<JSONObject> JsonObjectList = new ArrayList<JSONObject>();
 		while(m.find()){
 			JSONObject jsonObject = (JSONObject) JSONSerializer.toJSON(m.group());
 			JsonObjectList.add(jsonObject);
 		}
 		return JsonObjectList;
 	}
	
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
}