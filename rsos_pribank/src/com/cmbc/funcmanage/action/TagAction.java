package com.cmbc.funcmanage.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import rsos.framework.constant.Constants;
import rsos.framework.constant.GlobalConstants;
import rsos.framework.easyui.EasyGridList;
import rsos.framework.easyui.EasyResult;
import rsos.framework.exception.AppException;
import rsos.framework.utils.CalendarUtil;
import rsos.framework.utils.UUIDGenerator;
import rsos.framework.utils.UploadConfigurationRead;

import com.cmbc.funcmanage.bean.Tag;
import com.cmbc.sa.bean.Users;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;

public class TagAction extends FuncBaseAction {

	private static final long serialVersionUID = 1L;
	protected static final Log logger = LogFactory.getLog(TagAction.class);
	private String result;
	private Tag tagInfo;
	private String prefix;
	private String message;
	private EasyResult ret;	
	private String fileName;
	private String fileNewName;
	private String path;

	public void getlist(){
		try{
			String pages = ServletActionContext.getRequest().getParameter("page");
			String rows = ServletActionContext.getRequest().getParameter("rows");
			int currentPage = Integer.parseInt(pages);
			int pageSize    = Integer.parseInt(rows);
			
			String printDate = ServletActionContext.getRequest().getParameter("printDate");
			String printer = ServletActionContext.getRequest().getParameter("printer");

			Map<String,Object> m = new HashMap<String,Object>();
			if(printDate!=null&&printDate.length()>0){
				m.put("printDate", printDate);
			}
			if(printer!=null&&printer.length()>0){
				m.put("printer", printer);
			}

			List<Tag> tagList = tagService.findByPagination(currentPage, pageSize,m);
			int total = tagService.getTotal(m);
			
			EasyGridList<Tag> tList = new EasyGridList<Tag>();
			tList.setRows(tagList);
			tList.setTotal(total);
			tList.setRetCode(Constants.RETCODE_00000);
			tList.setMessage(getText(Constants.RETCODE_00000));
			logger.info(getText(Constants.RETCODE_00000)+"---"+tList.toJson());
			writeJsonSuccess(tList.toJson());			
		}catch(AppException ex){
			writeErrors(ex.getId());
			ex.printStackTrace();
		} catch (Exception e) {
			writeErrors(Constants.RETCODE_999999);
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	public void save(){
		try{
			List<String> list = new ArrayList<String>();
			Map<String,Object> map = new HashMap<String,Object>();
			String time = CalendarUtil.formatDatetime(new Date(),CalendarUtil.DATEFORMAT_YYYYMMDD);
			String time2 = CalendarUtil.getDateTimeStr();
			String prefix = ServletActionContext.getRequest().getParameter("prefix");
			String startCode = ServletActionContext.getRequest().getParameter("startCode");
			String endCode = ServletActionContext.getRequest().getParameter("endCode");
			String path = "C:\\barcodeImg\\" + prefix+startCode + "\\";
			Users user  = (Users) ActionContext.getContext().getSession().get(GlobalConstants.USER_INFORMATION_KEY);
			//实现打印条形码
			int start = Integer.valueOf(startCode).intValue();
			int end = Integer.parseInt(endCode);
			String code = "";
			for (int i = start; i <= end; i++) {
				DecimalFormat df1 = new DecimalFormat("000000");
				df1.format(i);
				code = prefix + String.valueOf(df1.format(i));
				list.add(code);
			}
			// 存入数据库
			Tag tag = new Tag();
			tag.setTagId(UUIDGenerator.generateShortUuid());
			tag.setPrefix(prefix);
			tag.setStartCode(startCode);
			tag.setEndCode(endCode);
			tag.setPath(path);
			tag.setPrinter(user.getUsername()); // 获取当前登录用户
			tag.setPrintDate(time);
			tag.setDate(time2);
			tagService.saveTag(tag,list);
			String message = "合同标签信息保存成功!";
			map.put("message", message);			
			JSONObject jsonObject = (JSONObject) JSONSerializer.toJSON(map);
			this.writeJsonSuccess(jsonObject.toString());
		} catch(AppException ex) {			
			String message = "{'message':'合同标签信息保存失败!'}";
			JSONObject jsonObject = (JSONObject) JSONSerializer.toJSON(message);
			this.writeJsonSuccess(jsonObject.toString());
			ex.printStackTrace();
		}catch (Exception e) {
			String message = "{'message':'系统故障,请联系管理员!'}";
			JSONObject jsonObject = (JSONObject) JSONSerializer.toJSON(message);
			this.writeJsonSuccess(jsonObject.toString());
			e.printStackTrace();
		}
	}

	public String showTaskByPrefix() {
		try{
			String prefix = ServletActionContext.getRequest().getParameter("prefix");
			String startCode = null;
			// 获取数据库中最近时间的终止编码
			boolean flag = tagService.isExistence(prefix);
			if (flag) {
				String maxEndCode = tagService.findMaxEndCode(prefix);
				// 对终止编码加一
				int maxEndCodeInt = Integer.valueOf(maxEndCode).intValue();
				int tmp = maxEndCodeInt + 1;
				DecimalFormat df = new DecimalFormat("000000");
				startCode = String.valueOf(df.format(tmp));
			} else {
				startCode = "000001";
			}
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("startCode", startCode);
			JSONObject json = JSONObject.fromObject(map);
			result = json.toString();
		}catch(AppException e){
			setRet(returnErrorMessage(e));
			e.printStackTrace();
		} catch (Exception e) {
			setRet(returnErrorMessage(Constants.RETCODE_999999));
			e.printStackTrace();
		}
		return Action.SUCCESS;
	}
	
	/*下载打印条码*/
	public InputStream getDownloadFile() throws Exception {	
		String path = ServletActionContext.getRequest().getParameter("path");
		fileName = "print.pdf";		
		fileNewName = URLEncoder.encode(fileName,"utf-8");
		File file = new File(path + fileName);
		InputStream inputStream = new FileInputStream(file);
		return inputStream;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	public Tag getTagInfo() {
		return tagInfo;
	}

	public void setTagInfo(Tag tagInfo) {
		this.tagInfo = tagInfo;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public void setRet(EasyResult ret) {
		this.ret = ret;
	}

	public EasyResult getRet() {
		return ret;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileNewName() {
		return fileNewName;
	}

	public void setFileNewName(String fileNewName) {
		this.fileNewName = fileNewName;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

}
