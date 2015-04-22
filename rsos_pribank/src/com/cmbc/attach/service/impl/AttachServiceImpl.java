package com.cmbc.attach.service.impl;

import java.util.List;

import rsos.framework.exception.AppException;
import rsos.framework.utils.GenericsUtils;

import com.cmbc.attach.dao.AttachInfoDao;
import com.cmbc.attach.bean.AttachInfo;
import com.cmbc.attach.service.AttachService;

public class AttachServiceImpl implements AttachService {

	private AttachInfoDao attachInfoDao;
	public void setAttachInfoDao(AttachInfoDao attachInfoDao) {
		this.attachInfoDao = attachInfoDao;
	}
	protected Class clazz;
	
	public Class getParamType(){
		System.out.println(this.getClass().getGenericSuperclass());
		Class clazz = GenericsUtils.getSuperClassGenricType(AttachService.class, 0);
		return clazz;
	}
	@Override
	public String findAttachNameByAttachId(String attachId) throws AppException{		
		try {
			return attachInfoDao.findAttachNameByBusinessId(attachId);
		} catch(Exception e){
			e.printStackTrace();
			throw new AppException("E000011",new String[]{attachId});
		}		
	}
	@Override
	public AttachInfo getAttachInfoByAttachId(String attachId) throws AppException{		
		try {
			AttachInfo list = (AttachInfo)attachInfoDao.getAttachInfoByAttachId(attachId).get(0);
			return list;
//			return (AttachInfo) attachInfoDao.getAttachInfoByAttachId(attachId);
		}catch(Exception e){
			e.printStackTrace();
			throw new AppException("E000011",new String[]{attachId});
		}		
	}
	@Override
	public List<AttachInfo> getAttachInfoByBusinessId(String businessId) throws AppException{		
		try {
			return attachInfoDao.getAllAttach(businessId);
		}catch(Exception e){
			e.printStackTrace();
			throw new AppException("E000011",new String[]{businessId});
		}
	}
	@Override
	public void deleteObject(Object object) throws AppException{		
		try {
			attachInfoDao.deleteObject(object);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new AppException("E000005");
		}
	}
	@Override
	public boolean save(Object object) throws AppException{
		try {
			return attachInfoDao.save(object);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new AppException("E000007");
		}
	}

	
}

