package com.cmbc.attach.bean;

import java.io.Serializable;
public class AttachInfo implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String attachId;
	private String businessId;
	private String attachName;
	private String attachNewName;    
    
	public String getAttachId() {
		return attachId;
	}
	public void setAttachId(String attachId) {
		this.attachId = attachId;
	}
	public String getBusinessId() {
		return businessId;
	}
	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}
	public String getAttachName() {
		return attachName;
	}
	public void setAttachName(String attachName) {
		this.attachName = attachName;
	}
	public String getAttachNewName() {
		return attachName.substring(attachName.indexOf('_') + 1);
	}
	public void setAttachNewName(String attachNewName) {
		this.attachNewName = attachNewName;
	}
    
}

