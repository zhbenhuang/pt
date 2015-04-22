package rsos.framework.struts2;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;


import rsos.framework.constant.StaticVariable;
import rsos.framework.utils.CalendarUtil;

import com.cmbc.funcmanage.bean.Contract;

public class ImportXSL {	
	public static List<Contract> importContract(InputStream fis){
		List<Contract> contractList = new ArrayList<Contract>();
		try{ 
			try{
				HSSFWorkbook wb = new HSSFWorkbook(fis);	//创建工作簿
				contractList = parseHSSF(wb);
			}catch(Exception e){
				XSSFWorkbook wb = new XSSFWorkbook(fis);
				contractList = parseXSSF(wb);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return contractList;
	}
	
	private static List<Contract> parseXSSF(XSSFWorkbook wb) {
		List<Contract> contractList = new ArrayList<Contract>();
		XSSFSheet sheet = wb.getSheetAt(0);		//得到第一个工作表
		XSSFRow row = null;
		int sheetsSize = wb.getNumberOfSheets();
		for(int i=0;i<sheetsSize;i++){
			sheet = wb.getSheetAt(i);
			int rows = sheet.getPhysicalNumberOfRows();
			for(int j=0;j<rows;j++){
				 row = sheet.getRow(j);
				 Contract contract = new Contract();
				 if(row.getCell(0)!= null){
					 if(ImportXSL.getXSSFCellValue(row.getCell(0)) != null && !"".equals(ImportXSL.getXSSFCellValue(row.getCell(0)))) {
						contract.setCustomeId(ImportXSL.getXSSFCellValue(row.getCell(0)).trim());
					 }
				 }else{
					 contract.setCustomeId("");
					 contract.setContractInfoStatus(StaticVariable.UNFULL);
				 }
				 
				 if(row.getCell(1)!= null){
					 if(ImportXSL.getXSSFCellValue(row.getCell(1)) != null && !"".equals(ImportXSL.getXSSFCellValue(row.getCell(1)))) {
						contract.setCustomeName(ImportXSL.getXSSFCellValue(row.getCell(1)).trim());
					 }
				 }else{
					 contract.setCustomeName("");
					 contract.setContractInfoStatus(StaticVariable.UNFULL);
				 }
				 
				 if(row.getCell(2)!= null){
					 if(ImportXSL.getXSSFCellValue(row.getCell(2)) != null && !"".equals(ImportXSL.getXSSFCellValue(row.getCell(2)))) {
						contract.setSaleDate(ImportXSL.getXSSFCellValue(row.getCell(2)).trim());
					 }
				 }else{
					 contract.setSaleDate("");
					 contract.setContractInfoStatus(StaticVariable.UNFULL);
				 }
				 
				 if(row.getCell(3)!= null){
					 if(ImportXSL.getXSSFCellValue(row.getCell(3)) != null && !"".equals(ImportXSL.getXSSFCellValue(row.getCell(3)))) {
						contract.setProductType(ImportXSL.getXSSFCellValue(row.getCell(3)).trim());
					 }
				 }else{
					 contract.setProductType("");
					 contract.setContractInfoStatus(StaticVariable.UNFULL);
				 }
				 
				 if(row.getCell(4)!= null){
					 if(ImportXSL.getXSSFCellValue(row.getCell(4)) != null && !"".equals(ImportXSL.getXSSFCellValue(row.getCell(4)))) {
						contract.setProductName(ImportXSL.getXSSFCellValue(row.getCell(4)).trim());
					 }
				 }else{
					 contract.setProductName("");
					 contract.setContractInfoStatus(StaticVariable.UNFULL);
				 }
				 
				 if(row.getCell(5)!= null){
					 if(ImportXSL.getXSSFCellValue(row.getCell(5)) != null && !"".equals(ImportXSL.getXSSFCellValue(row.getCell(5)))) {
						contract.setMoney(ImportXSL.getXSSFCellValue(row.getCell(5)).trim());
					 }
				 }else{
					 contract.setMoney("");
					 contract.setContractInfoStatus(StaticVariable.UNFULL);
				 }
				 
				 if(row.getCell(6)!= null){
					 if(ImportXSL.getXSSFCellValue(row.getCell(6)) != null && !"".equals(ImportXSL.getXSSFCellValue(row.getCell(6)))) {
						contract.setSignAccount(ImportXSL.getXSSFCellValue(row.getCell(6)).trim());
					 }
				 }else{
					 contract.setSignAccount("");
					 contract.setContractInfoStatus(StaticVariable.UNFULL);
				 }
				 
				 if(row.getCell(7)!= null){
					 if(ImportXSL.getXSSFCellValue(row.getCell(7)) != null && !"".equals(ImportXSL.getXSSFCellValue(row.getCell(7)))) {
						contract.setSaleManager(ImportXSL.getXSSFCellValue(row.getCell(7)).trim());
					 }
				 }else{
					 contract.setSaleManager("");
					 //contract.setContractInfoStatus(StaticVariable.UNFULL);
				 }
				 
				 if(row.getCell(8)!= null){
					 if(ImportXSL.getXSSFCellValue(row.getCell(8)) != null && !"".equals(ImportXSL.getXSSFCellValue(row.getCell(8)))) {
						contract.setBusinessManager(ImportXSL.getXSSFCellValue(row.getCell(8)).trim());
					 }
				 }else{
					 contract.setBusinessManager("");
					 //contract.setContractInfoStatus(StaticVariable.UNFULL);
				 }
				 
				 if(row.getCell(9)!= null){
					 if(ImportXSL.getXSSFCellValue(row.getCell(9)) != null && !"".equals(ImportXSL.getXSSFCellValue(row.getCell(9)))) {
						contract.setBelongDepartment(ImportXSL.getXSSFCellValue(row.getCell(9)).trim());
					 }
				 }else{
					 contract.setBelongDepartment("");
					 contract.setContractInfoStatus(StaticVariable.UNFULL);
				 }
				 
				 if(row.getCell(10)!= null){
					 if(ImportXSL.getXSSFCellValue(row.getCell(10)) != null && !"".equals(ImportXSL.getXSSFCellValue(row.getCell(10)))) {
						contract.setSignDepartment(ImportXSL.getXSSFCellValue(row.getCell(10)).trim());
					 }
				 }else{
					 contract.setSignDepartment("");
					 contract.setContractInfoStatus(StaticVariable.UNFULL);
				 }				 
				 contractList.add(contract);
			}
		}
		return contractList;
	}

	private static String getXSSFCellValue(XSSFCell cell) {
		String value = null;  
        //简单的查检列类型  
        switch(cell.getCellType())  
        {  
            case XSSFCell.CELL_TYPE_STRING://字符串  
                value = cell.getRichStringCellValue().getString();  
                break;  
            case XSSFCell.CELL_TYPE_NUMERIC://数字  
            	if(HSSFDateUtil.isCellDateFormatted(cell)){//进一步判断,如果是时间格式
            		value = CalendarUtil.formatDatetime(cell.getDateCellValue(), CalendarUtil.DATEFORMAT_YYYYMMDD);
            		break;
            	}else{
            		long dd = (long)cell.getNumericCellValue();  
                    value = dd+"";  
                    break;  
            	}
            case XSSFCell.CELL_TYPE_BLANK:  
                value = "";  
                break;     
            case XSSFCell.CELL_TYPE_FORMULA:  
                value = String.valueOf(cell.getCellFormula());  
                break;  
            case XSSFCell.CELL_TYPE_BOOLEAN://boolean型值  
                value = String.valueOf(cell.getBooleanCellValue());  
                break;  
            case XSSFCell.CELL_TYPE_ERROR:  
                value = String.valueOf(cell.getErrorCellValue());  
                break;  
            default:  
                break;  
        }  
        return value;
	}

	private static List<Contract> parseHSSF(HSSFWorkbook wb) {
		List<Contract> contractList = new ArrayList<Contract>();
		HSSFSheet sheet = (HSSFSheet)wb.getSheetAt(0);		//得到第一个工作表
		HSSFRow row = null;
		int sheetsSize = wb.getNumberOfSheets();
		for(int i=0;i<sheetsSize;i++){
			sheet = (HSSFSheet)wb.getSheetAt(i);
			int rows = sheet.getPhysicalNumberOfRows();
			for(int j=0;j<rows;j++){
				 int index = 0;
				 row = sheet.getRow(j);
				 Contract contract = new Contract();
				 if(row.getCell(0)!= null){
					 if(ImportXSL.getHSSFCellValue(row.getCell(0)) != null && !"".equals(ImportXSL.getHSSFCellValue(row.getCell(0)))) {
						contract.setCustomeId(ImportXSL.getHSSFCellValue(row.getCell(0)).trim());
					 }
				 }else{
					 index++;
					 contract.setCustomeId("");
					 contract.setContractInfoStatus(StaticVariable.UNFULL);
				 }
				 
				 if(row.getCell(1)!= null){
					 if(ImportXSL.getHSSFCellValue(row.getCell(1)) != null && !"".equals(ImportXSL.getHSSFCellValue(row.getCell(1)))) {
						contract.setCustomeName(ImportXSL.getHSSFCellValue(row.getCell(1)).trim());
					 }
				 }else{
					 contract.setCustomeName("");
					 contract.setContractInfoStatus(StaticVariable.UNFULL);
				 }
				 
				 if(row.getCell(2)!= null){
					 if(ImportXSL.getHSSFCellValue(row.getCell(2)) != null && !"".equals(ImportXSL.getHSSFCellValue(row.getCell(2)))) {
						contract.setSaleDate(ImportXSL.getHSSFCellValue(row.getCell(2)).trim());
					 }
				 }else{
					 contract.setSaleDate("");
					 contract.setContractInfoStatus(StaticVariable.UNFULL);
				 }
				 
				 if(row.getCell(3)!= null){
					 if(ImportXSL.getHSSFCellValue(row.getCell(3)) != null && !"".equals(ImportXSL.getHSSFCellValue(row.getCell(3)))) {
						contract.setProductType(ImportXSL.getHSSFCellValue(row.getCell(3)).trim());
					 }
				 }else{
					 contract.setProductType("");
					 contract.setContractInfoStatus(StaticVariable.UNFULL);
				 }
				 
				 if(row.getCell(4)!= null){
					 if(ImportXSL.getHSSFCellValue(row.getCell(4)) != null && !"".equals(ImportXSL.getHSSFCellValue(row.getCell(4)))) {
						contract.setProductName(ImportXSL.getHSSFCellValue(row.getCell(4)).trim());
					 }
				 }else{
					 index++;
					 contract.setProductName("");
					 contract.setContractInfoStatus(StaticVariable.UNFULL);
				 }
				 
				 if(row.getCell(5)!= null){
					 if(ImportXSL.getHSSFCellValue(row.getCell(5)) != null && !"".equals(ImportXSL.getHSSFCellValue(row.getCell(5)))) {
						contract.setMoney(ImportXSL.getHSSFCellValue(row.getCell(5)).trim());
					 }
				 }else{
					 contract.setMoney("");
					 contract.setContractInfoStatus(StaticVariable.UNFULL);
				 }
				 
				 if(row.getCell(6)!= null){
					 if(ImportXSL.getHSSFCellValue(row.getCell(6)) != null && !"".equals(ImportXSL.getHSSFCellValue(row.getCell(6)))) {
						contract.setSignAccount(ImportXSL.getHSSFCellValue(row.getCell(6)).trim());
					 }
				 }else{
					 index++;
					 contract.setSignAccount("");
					 contract.setContractInfoStatus(StaticVariable.UNFULL);
				 }
				 
				 if(row.getCell(7)!= null){
					 if(ImportXSL.getHSSFCellValue(row.getCell(7)) != null && !"".equals(ImportXSL.getHSSFCellValue(row.getCell(7)))) {
						contract.setSaleManager(ImportXSL.getHSSFCellValue(row.getCell(7)).trim());
					 }
				 }else{
					 contract.setSaleManager("");
					 //contract.setContractInfoStatus(StaticVariable.UNFULL);
				 }
				 
				 if(row.getCell(8)!= null){
					 if(ImportXSL.getHSSFCellValue(row.getCell(8)) != null && !"".equals(ImportXSL.getHSSFCellValue(row.getCell(8)))) {
						contract.setBusinessManager(ImportXSL.getHSSFCellValue(row.getCell(8)).trim());
					 }
				 }else{
					 contract.setBusinessManager("");
					 //contract.setContractInfoStatus(StaticVariable.UNFULL);
				 }
				 
				 if(row.getCell(9)!= null){
					 if(ImportXSL.getHSSFCellValue(row.getCell(9)) != null && !"".equals(ImportXSL.getHSSFCellValue(row.getCell(9)))) {
						contract.setBelongDepartment(ImportXSL.getHSSFCellValue(row.getCell(9)).trim());
					 }
				 }else{
					 contract.setBelongDepartment("");
					 contract.setContractInfoStatus(StaticVariable.UNFULL);
				 }
				 
				 if(row.getCell(10)!= null){
					 if(ImportXSL.getHSSFCellValue(row.getCell(10)) != null && !"".equals(ImportXSL.getHSSFCellValue(row.getCell(10)))) {
						contract.setSignDepartment(ImportXSL.getHSSFCellValue(row.getCell(10)).trim());
					 }
				 }else{
					 contract.setSignDepartment("");
					 contract.setContractInfoStatus(StaticVariable.UNFULL);
				 }
				 if(index>0){
					 break;
				 }else{
					 contractList.add(contract);
				 }
			}
		}
		return contractList;
	}

	
	//判断从Excel文件中解析出来数据的格式  
    private static String getHSSFCellValue(HSSFCell cell){  
        String value = null;  
        //简单的查检列类型  
        switch(cell.getCellType())  
        {  
            case HSSFCell.CELL_TYPE_STRING://字符串  
                value = cell.getRichStringCellValue().getString();  
                break;  
            case HSSFCell.CELL_TYPE_NUMERIC://数字  
            	if(HSSFDateUtil.isCellDateFormatted(cell)){//进一步判断,如果是时间格式
            		value = CalendarUtil.formatDatetime(cell.getDateCellValue(), CalendarUtil.DATEFORMAT_YYYYMMDD);
            		break;
            	}else{
            		long dd = (long)cell.getNumericCellValue();  
                    value = dd+"";  
                    break;  
            	}
            case HSSFCell.CELL_TYPE_BLANK:  
                value = "";  
                break;     
            case HSSFCell.CELL_TYPE_FORMULA:  
                value = String.valueOf(cell.getCellFormula());  
                break;  
            case HSSFCell.CELL_TYPE_BOOLEAN://boolean型值  
                value = String.valueOf(cell.getBooleanCellValue());  
                break;  
            case HSSFCell.CELL_TYPE_ERROR:  
                value = String.valueOf(cell.getErrorCellValue());  
                break;  
            default:  
                break;  
        }  
        return value;  
    }
    
	//判断从Excel文件中解析出来数据的格式（通用03，07+版本）  
    private static String getCellValueWb(Cell cell){  
        String value = null;  
        //简单的查检列类型  
        switch(cell.getCellType())  
        {  
            case Cell.CELL_TYPE_STRING://字符串  
                value = cell.getRichStringCellValue().getString();  
                break;  
            case Cell.CELL_TYPE_NUMERIC://数字  
            	if(DateUtil.isCellDateFormatted(cell)){//进一步判断,如果是时间格式
            		value = CalendarUtil.formatDatetime(cell.getDateCellValue(), CalendarUtil.DATEFORMAT_YYYYMMDD);
            		break;
            	}else{
            		long dd = (long)cell.getNumericCellValue();  
                    value = dd+"";  
                    break;  
            	}
            case Cell.CELL_TYPE_BLANK:  
                value = "";  
                break;     
            case Cell.CELL_TYPE_FORMULA:  
                value = String.valueOf(cell.getCellFormula());  
                break;  
            case Cell.CELL_TYPE_BOOLEAN://boolean型值  
                value = String.valueOf(cell.getBooleanCellValue());  
                break;  
            case Cell.CELL_TYPE_ERROR:  
                value = String.valueOf(cell.getErrorCellValue());  
                break;  
            default:  
                break;  
        }  
        return value;  
    }
}
