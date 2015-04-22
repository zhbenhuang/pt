package com.cmbc.attach.dao;

import java.util.List;

import rsos.framework.db.EasyBaseDao;

import com.cmbc.attach.bean.AttachInfo;

public interface AttachInfoDao extends EasyBaseDao<AttachInfo>{
	
	public List<AttachInfo> getAllAttach(String businessId);
	public String findAttachNameByBusinessId(String attachId);
	public List getAttachInfoByAttachId(String attachId);
	public void deleteObject(Object object);
	public boolean save(Object object);

}

