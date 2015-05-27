package com.cmbc.pbms.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import rsos.framework.constant.Constants;
import rsos.framework.exception.AppException;
import rsos.framework.struts2.BaseAction;
import rsos.framework.utils.UploadConfigurationRead;

import com.cmbc.pbms.bean.PbmsServInfo;
import com.cmbc.pbms.service.ServInfoService;
import com.opensymphony.xwork2.Action;
@Scope("prototype")
@Controller("downloadServInfoFileAction")
public class DownloadServInfoFileAction extends BaseAction
		 {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7448748577778248376L;
	
	private ServInfoService servInfoService;
	public void setServInfoService(ServInfoService servInfoService) {
		this.servInfoService = servInfoService;
	}
	
	public void getFile(){		
		try{
			printParameters();
			String serId = getHttpRequest().getParameter("serId");
			String fileType = getHttpRequest().getParameter("fileType");
			String fileNameStr = getHttpRequest().getParameter("fileName");
			String fileName = fileNameStr;
			if(fileNameStr != null){
				fileName = new String(fileName.getBytes("iso-8859-1"),"utf-8");
			}
			String tmpPath = UploadServInfoFileAction.getTmpPath(fileType);
			
			String fullPath= "";
			if(serId == null || serId.length()<=0){
				fullPath=getSavePath()+"/new/"+tmpPath;
				if(fileName == null) fileName = getFistChildFileName(fullPath);
				if(fileName == null) return ;
			}else{
				PbmsServInfo servInfo = servInfoService.findServInfo(serId);
				if(servInfo == null) return ;
				fullPath=getSavePath()+"/"+servInfo.getSerId()+"/"+tmpPath;
				fileName= getFileName(fileType, servInfo);
			}
			File subDir = new File(fullPath);
			if(!subDir.exists()){
				return ;
			}
			
			String fullPathName = fullPath+"/"+fileName;
			if(!(new File(fullPathName)).exists()){
				return ;
			}
			InputStream is=new FileInputStream(fullPathName);
			int len=0;
			byte []buffers=new byte[1024];
			getHttpResponse().reset();
			getHttpResponse().setContentType("application/x-msdownload");
			getHttpResponse().addHeader("Content-Disposition", "attachment;filename=\""+ fileName+"\"");  
			
			//把文件内容通过输出流打印到页面上供下载
			while((len=is.read(buffers))!=-1){
				OutputStream os=getHttpResponse().getOutputStream();
				os.write(buffers, 0, len);
			}
			
			is.close();	
		} catch(AppException e){			
			e.printStackTrace();
			addErrors(e);
		} catch (Exception e) {			
			e.printStackTrace();
			addErrors(Constants.RETCODE_999999);
		}
		
	}
	
	public static String getFistChildFileName(String fullPath) {
		if(fullPath == null){
			return null;
		}
		File file = new File(fullPath);
		if(!file.exists()){
			return null;
		}
		File[] subFiles = file.listFiles();
		if(subFiles == null || subFiles.length <=0) {
			return null;
		}
		return subFiles[0].getName();
	}
	public static String getFileName(String fileType, PbmsServInfo servInfo) {
		if(fileType == null || servInfo == null){
			return null;
		}
		if(fileType.equalsIgnoreCase("0")){
			return servInfo.getSerPic();
		}else if(fileType.equalsIgnoreCase("1")){
			return servInfo.getFileUrl1();
		}else if(fileType.equalsIgnoreCase("2")){
			return servInfo.getFileUrl2();
		}else{
			return  "unknow";
		}
	}
	
	private String getSavePath() {
		return UploadConfigurationRead.getInstance().getConfigItem("uploadServInfoPath").trim();
	}
	@Override
	public String execute() throws Exception {
		try{
			return Action.SUCCESS;
		}catch (Exception e){
			return Action.ERROR;
		}
	}
	
	
}

