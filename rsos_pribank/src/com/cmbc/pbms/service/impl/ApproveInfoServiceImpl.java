package com.cmbc.pbms.service.impl;

import org.apache.log4j.Logger;

import rsos.framework.constant.GlobalConstants;
import rsos.framework.easyui.EasyGridList;
import rsos.framework.exception.AppException;

import com.cmbc.pbms.bean.PbmsApproveInfo;
import com.cmbc.pbms.dao.ApproveInfoDao;
import com.cmbc.pbms.dto.QueryApproveInfoDto;
import com.cmbc.pbms.service.ApproveInfoService;

public class ApproveInfoServiceImpl implements ApproveInfoService {
	private Logger log = Logger.getLogger(GlobalConstants.USER_ACCESS_LOGGER);
	private ApproveInfoDao approveInfoDao;
	
	public void setApproveInfoDao(ApproveInfoDao approveInfoDao) {
		this.approveInfoDao = approveInfoDao;
	}
	
	public EasyGridList<PbmsApproveInfo> findApproveInfos(QueryApproveInfoDto queryDto)
			throws AppException {
		try {
			return approveInfoDao.selectApproveInfos(queryDto);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new AppException("E000004");
		}
	}
	
	
	public PbmsApproveInfo findApproveInfo(String apprId)
			throws AppException {
		try {
			log.info("---findApproveInfo by key apprId: "+apprId);
			return approveInfoDao.selectApproveInfo(apprId);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new AppException("E000011",new String[]{apprId});
		}
	}
	
	public void deleteApproveInfo(String idstr)
			throws AppException {
		try {
			approveInfoDao.deleteApproveInfo(idstr);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new AppException("E000005");
		}
	}
	
	public void saveApproveInfo(PbmsApproveInfo approveInfo)
			throws AppException {
		try {
			approveInfoDao.insert(approveInfo);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new AppException("E000007");
		}
	}
	
	public void modifyApproveInfo(PbmsApproveInfo approveInfo)
			throws AppException {
		try {
			approveInfoDao.update(approveInfo);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new AppException("E000006");
		}
	}

	@Override
	public int nextId() {
		try {
			int i = approveInfoDao.getMaxId();
			return  ++i;
		} catch (Exception ex) {
			ex.printStackTrace();
			return 0;
		}
	}

}
