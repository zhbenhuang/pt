package com.cmbc.attach.service;

import java.util.List;

import rsos.framework.exception.AppException;

import com.cmbc.attach.bean.AttachInfo;

public interface AttachService {

	public String findAttachNameByAttachId(String attachId) throws AppException;
	public AttachInfo getAttachInfoByAttachId(String attachId) throws AppException;
	public List<AttachInfo> getAttachInfoByBusinessId(String businessId) throws AppException;
	public void deleteObject(Object object) throws AppException;
	public boolean save(Object object) throws AppException;
	
}

