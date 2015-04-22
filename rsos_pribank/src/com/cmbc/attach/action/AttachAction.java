package com.cmbc.attach.action;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import rsos.framework.struts2.BaseAction;

import com.cmbc.attach.bean.AttachInfo;
import com.cmbc.attach.service.AttachService;

/**
 * @author zhbenhuang
 * @version 2015年4月21日 上午11:14:16
 */
public class AttachAction extends BaseAction {
	private Logger log = Logger.getLogger(AttachAction.class);
	private static final long serialVersionUID = 1L;	
	
    private String attachId;    	
    private String attachName;
    private List<AttachInfo> QueryNoticeAttachResultList = new ArrayList<AttachInfo>();
	protected AttachService attachService;
	private String businessId;
    public void setAttachService(AttachService attachService) {
		this.attachService = attachService;
	}
    
    public String addNoticeAttach(){    	    	
    	return "addNoticeAttach";
    }
    
	public String downloadNoticeAttach() {		
		try {
			attachName = attachService.findAttachNameByAttachId(attachId);			
		} catch (Exception ex) {
			String message = "{'message':'附件下载失败!'}";
			JSONObject jsonObject = (JSONObject) JSONSerializer.toJSON(message);
			this.writeJsonSuccess(jsonObject.toString());
			ex.printStackTrace();
		}
		return "downloadNoticeAttach";		
	}
	
	public String delAttach() {
		try {				
				AttachInfo attachInfo = new AttachInfo();
				attachInfo = attachService.getAttachInfoByAttachId(attachId);
				attachService.deleteObject(attachInfo);		
		} catch (Exception ex) {
			String message = "{'message':'附件删除失败!'}";
			JSONObject jsonObject = (JSONObject) JSONSerializer.toJSON(message);
			this.writeJsonSuccess(jsonObject.toString());
			ex.printStackTrace();
		}
		return "deleteAttachSuccess";
	}
	
	public String listCheckNoticeAttach() {
    	try {
    		businessId = ServletActionContext.getRequest().getParameter("businessId");
    		QueryNoticeAttachResultList = attachService.getAttachInfoByBusinessId(businessId);
    	} catch (Exception ex) {
			String message = "{'message':'附件显示失败!'}";
			JSONObject jsonObject = (JSONObject) JSONSerializer.toJSON(message);
			this.writeJsonSuccess(jsonObject.toString());
			ex.printStackTrace();
		}
    	return "listCheckNoticeAttach";
		
	}
	
	public String listNoticeAttach() {
    	try {
    		businessId = ServletActionContext.getRequest().getParameter("businessId");
    		QueryNoticeAttachResultList = attachService.getAttachInfoByBusinessId(businessId);
    	} catch (Exception ex) {
			String message = "{'message':'附件显示失败!'}";
			JSONObject jsonObject = (JSONObject) JSONSerializer.toJSON(message);
			this.writeJsonSuccess(jsonObject.toString());
			ex.printStackTrace();
		}
    	return "listNoticeAttach";		
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

	public List<AttachInfo> getQueryNoticeAttachResultList() {
		return QueryNoticeAttachResultList;
	}

	public void setQueryNoticeAttachResultList(
			List<AttachInfo> queryNoticeAttachResultList) {
		QueryNoticeAttachResultList = queryNoticeAttachResultList;
	}

	public String getBusinessId() {
		return businessId;
	}

	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}    	

}

