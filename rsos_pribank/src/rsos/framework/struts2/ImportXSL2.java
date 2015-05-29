package rsos.framework.struts2;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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


import rsos.framework.utils.CalendarUtil;

public class ImportXSL2 {	
	public static List<Map<String, String>> importRows(InputStream fis){
		List<Map<String, String>> mapList = new ArrayList<Map<String, String>>();
		try{ 
			try{
				HSSFWorkbook wb = new HSSFWorkbook(fis);	//创建工作簿
				mapList = parseHSSF(wb);
			}catch(Exception e){
				XSSFWorkbook wb = new XSSFWorkbook(fis);
				mapList = parseXSSF(wb);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return mapList;
	}
	
	private static List<Map<String, String>> parseXSSF(XSSFWorkbook wb) {
		List<Map<String, String>> mapList = new ArrayList<Map<String, String>>();
		XSSFSheet sheet = wb.getSheetAt(0);		//得到第一个工作表
		XSSFRow row = null;
		int sheetsSize = wb.getNumberOfSheets();
		for(int i=0;i<sheetsSize;i++){
			sheet = wb.getSheetAt(i);
			int rows = sheet.getPhysicalNumberOfRows();
			for(int j=0;j<rows;j++){
				 row = sheet.getRow(j);
				 Map<String, String> map = new HashMap<String, String>();
				 if(row.getCell(0)!= null){
					 if(ImportXSL2.getXSSFCellValue(row.getCell(0)) != null && !"".equals(ImportXSL2.getXSSFCellValue(row.getCell(0)))) {
						map.put("struId", ImportXSL2.getXSSFCellValue(row.getCell(0)).trim());
					 }
				 }else{
					 map.put("struId", "");
				 }
				 
				 if(row.getCell(1)!= null){
					 if(ImportXSL2.getXSSFCellValue(row.getCell(1)) != null && !"".equals(ImportXSL2.getXSSFCellValue(row.getCell(1)))) {
						map.put("struName", ImportXSL2.getXSSFCellValue(row.getCell(1)).trim());
					 }
				 }else{
					 map.put("struName", "");
				 }
				 
				 if(row.getCell(2)!= null){
					 if(ImportXSL2.getXSSFCellValue(row.getCell(2)) != null && !"".equals(ImportXSL2.getXSSFCellValue(row.getCell(2)))) {
						map.put("begValue", ImportXSL2.getXSSFCellValue(row.getCell(2)).trim());
					 }
				 }else{
					 map.put("begValue", "0");
				 }
				 mapList.add(map);
			}
		}
		return mapList;
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

	private static List<Map<String, String>> parseHSSF(HSSFWorkbook wb) {
		List<Map<String, String>> mapList = new ArrayList<Map<String, String>>();
		HSSFSheet sheet = (HSSFSheet)wb.getSheetAt(0);		//得到第一个工作表
		HSSFRow row = null;
		int sheetsSize = wb.getNumberOfSheets();
		for(int i=0;i<sheetsSize;i++){
			sheet = (HSSFSheet)wb.getSheetAt(i);
			int rows = sheet.getPhysicalNumberOfRows();
			for(int j=0;j<rows;j++){
				 int index = 0;
				 row = sheet.getRow(j);
				 Map<String, String> map = new HashMap<String, String>();
				 if(row.getCell(0)!= null){
					 if(ImportXSL2.getHSSFCellValue(row.getCell(0)) != null && !"".equals(ImportXSL2.getHSSFCellValue(row.getCell(0)))) {
						map.put("struId", ImportXSL2.getHSSFCellValue(row.getCell(0)).trim());
					 }
				 }else{
					 index++;
					 map.put("struId", "");
				 }
				 
				 if(row.getCell(1)!= null){
					 if(ImportXSL2.getHSSFCellValue(row.getCell(1)) != null && !"".equals(ImportXSL2.getHSSFCellValue(row.getCell(1)))) {
						map.put("struName",ImportXSL2.getHSSFCellValue(row.getCell(1)).trim());
					 }
				 }else{
					 map.put("struName","");
				 }
				 
				 
				 if(row.getCell(2)!= null){
					 if(ImportXSL2.getHSSFCellValue(row.getCell(2)) != null && !"".equals(ImportXSL2.getHSSFCellValue(row.getCell(2)))) {
						map.put("begValue", ImportXSL2.getHSSFCellValue(row.getCell(2)).trim());
					 }
				 }else{
					 map.put("begValue", "0");
				 }
				 
				 if(index>0){
					 break;
				 }else{
					 mapList.add(map);
				 }
			}
		}
		return mapList;
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
