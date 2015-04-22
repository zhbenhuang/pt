package com.cmbc.attach.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.cmbc.attach.service.AttachService;
import com.opensymphony.xwork2.ActionSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;

import rsos.framework.utils.UploadConfigurationRead;

public class DownloadAttachAction extends ActionSupport {

	private static final long serialVersionUID = -5063897330345131296L;
	protected static final Log logger = LogFactory.getLog(DownloadAttachAction.class.getName());

	private String targetDirectory;
	private String fileName;
	private int tag;
	private String fileNewName;
	private String tmpName = this.getRequest().getParameter("tmpName"); //将js的值传进来
	
	private String attachId = this.getRequest().getParameter("attachId");;
	private String attachName;

    private AttachService attachService;
	
	public HttpServletRequest getRequest() {
		return ServletActionContext.getRequest();
	}

	public String getFileName() throws UnsupportedEncodingException {		
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public String getFileNewName() throws UnsupportedEncodingException {		
		return fileNewName;
	}

	public void setFileNewName(String fileNewName) {
		this.fileNewName = fileNewName;
	}
	
	public String getTmpName() throws UnsupportedEncodingException{
		return tmpName;
	}

	public void setTmpName(String tmpName) {
		try {
			tmpName = new String(tmpName.getBytes("ISO-8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public int getTag() {
		return tag;
	}

	public void setTag(int tag) {
		this.tag = tag;
	}
	
	public String getTargetDirectory() {
		return targetDirectory;
	}

	public void setTargetDirectory(String targetDirectory) {
		this.targetDirectory = targetDirectory;
	}

	public InputStream getDownloadFile() throws Exception {			
			logger.debug("method--------downloadNoticeAttach: attachId= " + attachId);
			attachName = attachService.findAttachNameByAttachId(attachId);			
			fileName = attachName;
			fileNewName = URLEncoder.encode(fileName, "utf-8");
			fileNewName = fileNewName.substring(fileNewName.indexOf('_') + 1);
			targetDirectory = ServletActionContext.getServletContext().getRealPath("/"
						+ UploadConfigurationRead.getInstance()
								.getConfigItem("uploadAttachPath").trim());
			File file = new File(targetDirectory + "/" + fileName);
			InputStream inputStream = new FileInputStream(file);
			return inputStream;
	}

	public String getAttachId() {
		return attachId;
	}

	public void setAttachId(String attachId) {
		this.attachId = attachId;
	}

	public String getAttachName() {
		return attachName;
	}

	public void setAttachName(String attachName) {
		this.attachName = attachName;
	}

	public void setAttachService(AttachService attachService) {
		this.attachService = attachService;
	}

	@Override
	public String execute() throws Exception {
		return SUCCESS;
	}	
	
}


