package com.cmbc.reportStatistic.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import rsos.framework.constant.Constants;
import rsos.framework.easyui.EasyGridList;
import rsos.framework.easyui.EasyResult;
import rsos.framework.exception.AppException;

import com.cmbc.funcmanage.action.FuncBaseAction;
import com.cmbc.funcmanage.bean.Product;
import com.cmbc.funcmanage.bean.RedempBook;
import com.cmbc.reportStatistic.bean.RedempIntervalReport;
import com.cmbc.reportStatistic.excelTool.ExcelUtils;
import com.cmbc.reportStatistic.excelTool.TableData;
import com.cmbc.reportStatistic.excelTool.TableDataRow;
import com.cmbc.reportStatistic.excelTool.TableHeaderMetaData;

public class RedempBookAction extends FuncBaseAction {
	private Logger log = Logger.getLogger(RedempBookAction.class);
	private static final long serialVersionUID = 8746045870683929606L;
	
	public void getRedempBookList(){
		try {
			printParameters();
			String pages = ServletActionContext.getRequest().getParameter("page");
			String rows = ServletActionContext.getRequest().getParameter("rows");
			int currentPage = Integer.parseInt(pages);
			int pageSize    = Integer.parseInt(rows);
			Map<String,Object> m = new HashMap<String,Object>();
			m = setConditionFour(m);
			
			List<RedempBook> redempBookList =  new ArrayList<RedempBook>();
			redempBookList = contractService.findRedempBooks("redempBookView",currentPage,pageSize,m);
			int total = contractService.getBooksTotal("redempBookView",m);
			EasyGridList<RedempBook> rlist = new EasyGridList<RedempBook>();
			rlist.setRows(redempBookList);
			rlist.setTotal(total);
			rlist.setRetCode(Constants.RETCODE_00000);
			rlist.setMessage(getText(Constants.RETCODE_00000));
			log.info(getText(Constants.RETCODE_00000)+"---"+rlist.toJson());
			writeJsonSuccess(rlist.toJson());			
		}catch(AppException ex){
			writeErrors(ex.getId());
			ex.printStackTrace();
		} catch (Exception e) {
			writeErrors(Constants.RETCODE_999999);
			e.printStackTrace();
		}
	}
	
	public void redempProductBookExcel(){
		try{
			printParameters();
			String productCode = ServletActionContext.getRequest().getParameter("productCode");
			List<Product> productList = new ArrayList<Product>();
			if(productCode!=null&&productCode.length()>0){
				Product product = productService.findProductByproductCode(productCode);
				if(product!=null&&product.getIsRoll().equals("是")){
					productList.add(product);
					EasyResult result = new EasyResult(Constants.RETCODE_00000, getText(Constants.RETCODE_00000));
					writeJsonSuccess(result.toJson());
				}else{
					EasyResult result = new EasyResult(Constants.RETCODE_000021, getText(Constants.RETCODE_000021));
					writeJsonSuccess(result.toJson());
				}
			}
		}catch (Exception e) {
			writeErrors(Constants.RETCODE_999999);
			e.printStackTrace();
		}
	}
	
	public void printRedempProductBookExcel(){
		try{
			printParameters();
			String productCode = ServletActionContext.getRequest().getParameter("productCode");
			List<Product> productList = new ArrayList<Product>();
			if(productCode!=null&&productCode.length()>0){
				Product product = productService.findProductByproductCode(productCode);
				productList.add(product);
				String title = "产品"+productCode+"赎回台账";
		        String[] hearders = new String[] {"产品编号", "产品名称","起息日","到息日","预期收益(%)","开放日","赎回起始日","赎回结束日","滚动期收益(%)","赎回期状态","赎回份数"};
		        List<String> unitHearders = new ArrayList<String>();
		        unitHearders = setUnitHearders(unitHearders);
		        TableData redempBookTable = createRedempBookTableData(productList, ExcelUtils.createTableHeader(hearders,unitHearders));
		        exportReport(title,redempBookTable);
			}
		}catch (Exception e) {
			writeErrors(Constants.RETCODE_999999);
			e.printStackTrace();
		}
	}
	
	public void printRedempAllProductBookExcel(){
		try {
			printParameters();
			List<Product> productList = new ArrayList<Product>();
			productList = productService.findAllProduct();
        	String title = "全产品赎回台账";
	        String[] hearders = new String[] {"产品编号", "产品名称","起息日","到息日","预期收益(%)","开放日","赎回起始日","赎回结束日","滚动期收益(%)","赎回期状态","赎回份数"};
	        List<String> unitHearders = new ArrayList<String>();
	        unitHearders = setUnitHearders(unitHearders);
	        TableData redempBookTable = createRedempBookTableData(productList, ExcelUtils.createTableHeader(hearders,unitHearders));
	        exportReport(title,redempBookTable);
		}catch (Exception e) {
			writeErrors(Constants.RETCODE_999999);
			e.printStackTrace();
		}
	}

	private List<String> setUnitHearders(List<String> unitHearders) {
		unitHearders.add("产品编号");
		unitHearders.add("产品名称");
		unitHearders.add("起息日");
		unitHearders.add("到息日");
		unitHearders.add("预期收益(%)");
		return unitHearders;
	}

	@SuppressWarnings("unchecked")
	private TableData createRedempBookTableData(List<Product> productList,TableHeaderMetaData headMeta) throws AppException {
		TableData redempBookTable= new TableData(headMeta);
		TableDataRow row = null;
		String[] productFields = new String[]{"productCode","productName","benefitDate","dueDate","plannedBenefit"};
		String[] redempIntervalFileds = new String[]{"openDay","redeemBegin","redeemEnd","rollBenefit","redemptionStatus","redempBackNum"};
		if(productList != null && productList.size()>0){
			for(Product product : productList){
				row = new TableDataRow(redempBookTable);
				Map<String, Object> map = (product instanceof Map)?(Map<String, Object>)product:ExcelUtils.beanToMap(product);
				for(String productFiled:productFields){
					row.addCell(map.get(productFiled));
				}
				List<RedempIntervalReport> redempIntervalReportList = productRedemptionIntervalService.findRedempIntervalReportsByProductId(product.getProductId());
				if(redempIntervalReportList!=null&&redempIntervalReportList.size()>0){
					RedempIntervalReport redempIntervalReport = redempIntervalReportList.get(0);
					Map<String, Object> mapNew = (redempIntervalReport instanceof Map)?(Map<String, Object>)redempIntervalReport:ExcelUtils.beanToMap(redempIntervalReport);
					for(String priFile:redempIntervalFileds){
						row.addCell(mapNew.get(priFile));
					}
					redempBookTable.addRow(row);
					redempBookTable = insertRedemptionIntervalRow(product,redempBookTable,redempIntervalReportList,productFields,redempIntervalFileds);
				}
				
			}
		}
		return redempBookTable;
	}

	@SuppressWarnings("unchecked")
	private TableData insertRedemptionIntervalRow(Product product,TableData redempBookTable,
			List<RedempIntervalReport> redempIntervalReportList,String[] productFields,
			String[] redempIntervalFileds) {
		TableDataRow row = null;
		int productFieldSize = productFields.length;
		int size = redempIntervalReportList.size();
		for(int i=1;i<size;i++){
			row = new TableDataRow(redempBookTable);
			Map<String, Object> map = (product instanceof Map)?(Map<String, Object>)product:ExcelUtils.beanToMap(product);
			for(int j=0;j<productFieldSize;j++){
				row.addCell(map.get(productFields[j]));
			}
			RedempIntervalReport redempIntervalReport = redempIntervalReportList.get(i);
			Map<String, Object> mapNew = (redempIntervalReport instanceof Map)?(Map<String, Object>)redempIntervalReport:ExcelUtils.beanToMap(redempIntervalReport);
			for(String priFile:redempIntervalFileds){
				row.addCell(mapNew.get(priFile));
			}
			redempBookTable.addRow(row);
		}
		return redempBookTable;
	}
	
	
}
