package com.cmbc.pbms.service.impl;

import org.apache.log4j.Logger;

import rsos.framework.constant.GlobalConstants;
import rsos.framework.easyui.EasyGridList;
import rsos.framework.exception.AppException;

import com.cmbc.pbms.bean.PbmsApproveParmt;
import com.cmbc.pbms.dao.ApproveParmtDao;
import com.cmbc.pbms.dto.QueryApproveParmtDto;
import com.cmbc.pbms.service.ApproveParmtService;

public class ApproveParmtServiceImpl implements ApproveParmtService {
	private Logger log = Logger.getLogger(GlobalConstants.USER_ACCESS_LOGGER);
	private ApproveParmtDao approveParmtDao;
	
	public void setApproveParmtDao(ApproveParmtDao approveParmtDao) {
		this.approveParmtDao = approveParmtDao;
	}
	
	public EasyGridList<PbmsApproveParmt> findApproveParmts(QueryApproveParmtDto queryDto)
			throws AppException {
		try {
			return approveParmtDao.selectApproveParmts(queryDto);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new AppException("E000004");
		}
	}
	
	
	public PbmsApproveParmt findApproveParmt(String apprType)
			throws AppException {
		try {
			log.info("---findApproveParmt by key apprType: "+apprType);
			return approveParmtDao.selectApproveParmt(apprType);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new AppException("E000011",new String[]{apprType});
		}
	}
	
	public void deleteApproveParmt(String idstr)
			throws AppException {
		try {
			approveParmtDao.deleteApproveParmt(idstr);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new AppException("E000005");
		}
	}
	
	public void saveApproveParmt(PbmsApproveParmt approveParmt)
			throws AppException {
		try {
			approveParmtDao.insert(approveParmt);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new AppException("E000007");
		}
	}
	
	public void modifyApproveParmt(PbmsApproveParmt approveParmt)
			throws AppException {
		try {
			approveParmtDao.update(approveParmt);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new AppException("E000006");
		}
	}

	@Override
	public int nextId() {
		try {
			int i = approveParmtDao.getMaxId();
			return  ++i;
		} catch (Exception ex) {
			ex.printStackTrace();
			return 0;
		}
	}

}
